package org.example;

import static java.lang.String.format;
import static java.util.Collections.emptyMap;
import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;

/**
 * @author cuiyulong <cuiyulong@kuaishou.com>
 * Created on 2025-06-25
 */
public class JsonFlattenUtils {

    private static final String INIT_PATH = "";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Map<String, String> flattenIterate(String json) throws IOException {
        if (isBlank(json)) {
            return emptyMap();
        }
        final JsonNode rootNode = OBJECT_MAPPER.readTree(json);
        requireNonNull(rootNode);
        Stack<JsonNodeHolder> stack = new Stack<>();
        final Map<String, String> resultMap = new LinkedHashMap<>();
        stack.add(new JsonNodeHolder(rootNode, INIT_PATH));
        // 深度优先遍历
        while (!stack.isEmpty()) {
            final JsonNodeHolder holder = stack.pop();
            final JsonNode node = holder.getNode();
            final String path = holder.getPath();
            if (node.isValueNode()) {
                resultMap.put(path, node.asText());
            } else if (node.isArray()) {
                final List<JsonNode> elements = Lists.newArrayList(node.elements());
                for (int i = elements.size() - 1; i >= 0; i--) {
                    final JsonNode currentNode = elements.get(i);
                    stack.push(JsonNodeHolder.of(currentNode,
                            isBlank(path) ? String.valueOf(i) : format("%s.%s", path, i)));
                }
            } else if (node.isObject()) {
                final List<Entry<String, JsonNode>> entries = Lists.newArrayList(node.fields());
                for (int i = entries.size() - 1; i >= 0; i--) {
                    final Entry<String, JsonNode> entry = entries.get(i);
                    final String fieldName = entry.getKey();
                    final JsonNode currentNode = entry.getValue();
                    stack.push(JsonNodeHolder.of(currentNode,
                            isBlank(path) ? fieldName : format("%s.%s", path, fieldName)));
                }
            }
        }
        return resultMap;
    }

    public static Map<String, String> flattenRecursive(String json) throws IOException {
        if (isBlank(json)) {
            return emptyMap();
        }
        final JsonNode rootNode = OBJECT_MAPPER.readTree(json);
        requireNonNull(rootNode);
        return flatten(rootNode, INIT_PATH);
    }

    private static Map<String, String> flatten(JsonNode node, String prefixPath) {
        if (node.isValueNode()) {
            return ImmutableMap.of(prefixPath, node.asText());
        } else {
            if (node.isArray()) {
                return flattenArrayNode(node, prefixPath);
            }
            if (node.isObject()) {
                return flattenObjectNode(node, prefixPath);
            }
            return emptyMap();
        }
    }

    private static Map<String, String> flattenArrayNode(JsonNode node, String prefixPath) {
        final Map<String, String> resultMap = Maps.newHashMap();
        final Iterator<JsonNode> iterator = node.elements();
        int index = 0;
        while (iterator.hasNext()) {
            final JsonNode currentNode = iterator.next();
            String subPrefixPath = isBlank(prefixPath) ? String.valueOf(index++) : format("%s.%s", prefixPath, index++);
            resultMap.putAll(flatten(currentNode, subPrefixPath));
        }
        return resultMap;
    }

    private static Map<String, String> flattenObjectNode(JsonNode node, String prefixPath) {
        final Map<String, String> resultMap = Maps.newHashMap();
        final Iterator<Entry<String, JsonNode>> iterator = node.fields();
        while (iterator.hasNext()) {
            final Entry<String, JsonNode> entry = iterator.next();
            String subPrefixPath = isBlank(prefixPath) ? entry.getKey() : format("%s.%s", prefixPath, entry.getKey());
            resultMap.putAll(flatten(entry.getValue(), subPrefixPath));
        }
        return resultMap;
    }

    @Getter
    private static class JsonNodeHolder {
        private final JsonNode node;
        private final String path;

        private JsonNodeHolder(JsonNode node, String path) {
            this.node = node;
            this.path = path;
        }

        public static JsonNodeHolder of(JsonNode node, String path) {
            return new JsonNodeHolder(node, path);
        }
    }
}
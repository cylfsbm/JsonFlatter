package org.example;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cuiyulong <cuiyulong@kuaishou.com>
 * Created on 2025-06-25
 */
public class JsonFlattenUtilsTest {

    private ObjectMapper OBJECT_MAPPER;

    public JsonFlattenUtilsTest() {
        OBJECT_MAPPER = new ObjectMapper();
    }

    @Before
    public void before() {
    }

    @After
    public void after() {
    }

    @Test
    public void test() throws IOException {
        final String json = "{\n"
                + "    \"id\": 123,\n"
                + "    \"name\": \"John Doe\",\n"
                + "    \"address\": {\n"
                + "        \"street\": \"123 Main St\",\n"
                + "        \"city\": \"Anytown\",\n"
                + "        \"zip\": \"12345\"\n"
                + "    },\n"
                + "    \"hobbies\": [\"reading\", \"hiking\", \"coding\"],\n"
                + "    \"contact\": {\n"
                + "        \"phone\": {\n"
                + "            \"home\": \"555-1234\",\n"
                + "            \"work\": \"555-5678\"\n"
                + "        },\n"
                + "        \"email\": \"john.doe@example.com\"\n"
                + "    }\n"
                + "}";
        // 迭代实现
        System.out.println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsString(JsonFlattenUtils.flattenIterate(json)));
        // 递归实现
        System.out.println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsString(JsonFlattenUtils.flattenRecursive(json)));
    }
}

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
                + "    \"hobbies\": [\n"
                + "        \"reading\",\n"
                + "        \"hiking\",\n"
                + "        \"coding\"\n"
                + "    ],\n"
                + "    \"contact\": {\n"
                + "        \"phone\": {\n"
                + "            \"home\": \"555-1234\",\n"
                + "            \"work\": \"555-5678\"\n"
                + "        },\n"
                + "        \"email\": \"john.doe@example.com\"\n"
                + "    },\n"
                + "    \"test1\": [\n"
                + "        {\n"
                + "            \"s1\": \"s11\",\n"
                + "            \"i1\": 10,\n"
                + "            \"a1\": [\n"
                + "                \"23\",\n"
                + "                \"233\"\n"
                + "            ]\n"
                + "        },\n"
                + "        {\n"
                + "            \"s1\": \"s12\",\n"
                + "            \"i1\": 100,\n"
                + "            \"a1\": [\n"
                + "                \"233\",\n"
                + "                \"2333\"\n"
                + "            ]\n"
                + "        }\n"
                + "    ]\n"
                + "}";
        System.out.println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsString(JsonFlattenUtils.flattenIterate(json)));
        System.out.println(OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                .writeValueAsString(JsonFlattenUtils.flattenRecursive(json)));
    }
}

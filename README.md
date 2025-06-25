## A json flatten utils written by java based on jackson

### how to use, read the following code or the **JsonFlattenUtilsTest.java**
#### origin json
```json
{
  "id": 123,
  "name": "John Doe",
  "address": {
    "street": "123 Main St",
    "city": "Anytown",
    "zip": "12345"
  },
  "hobbies": ["reading", "hiking", "coding"],
  "contact": {
    "phone": {
      "home": "555-1234",
      "work": "555-5678"
    },
    "email": "john.doe@example.com"
  }
}
```
#### demo code
```java
public class JsonFlattenUtilsTest {
    
    public static void main(String[] args) throws IOException {
        
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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
```
#### flatten result
```json
{
  "id" : "123",
  "name" : "John Doe",
  "address.street" : "123 Main St",
  "address.city" : "Anytown",
  "address.zip" : "12345",
  "hobbies.0" : "reading",
  "hobbies.1" : "hiking",
  "hobbies.2" : "coding",
  "contact.phone.home" : "555-1234",
  "contact.phone.work" : "555-5678",
  "contact.email" : "john.doe@example.com"
}
```
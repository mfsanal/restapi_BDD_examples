package steps;

import com.google.gson.*;
import groovy.util.logging.Slf4j;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public class MainTests {
    RequestSpecification request;
    Response response;
    JsonObject jo;

    @Test
    public void jsonEdit(String path) throws Exception {
        JsonObject jsonObject = (new JsonParser()).parse(readFileAsString("src/test/java/objects/" + path + ".json")).getAsJsonObject();
        jo = jsonObject.getAsJsonObject();
    }

    public String getTypeOfValue(String field) throws Exception {
        if (jo.get(field).isJsonPrimitive()) {
            if (jo.get(field).getAsJsonPrimitive().isBoolean())
                return "Boolean";
            if (jo.get(field).getAsJsonPrimitive().isString())
                return "String";
            if (jo.get(field).getAsJsonPrimitive().isNumber()) {
                return "Number";
            }
        }
        return "Wrong";
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public String dataConversionString(String value) {
        if (value.equals("null")) {
            return null;
        } else if (value.equals("whiteSpace")) {
            return "      ";
        } else if (value.equals("noString")) {
            return "";
        } else
            return value;
    }

    public Integer dataConversionInteger(String value) {
        if (value.equals("null")) {
            return null;
        } else
            return Integer.valueOf(value);
    }

    public Boolean dataConversionBoolean(String value) {
        if (value.equals("null")) {
            return null;
        } else
            return Boolean.valueOf(value);
    }

    @Given("Prepare Url {string}")
    public void prepareUrl(String url) {
        RestAssured.baseURI = url;
        request = RestAssured.given();
    }

    @When("Send Request {string}")
    public void sendRequest(String method) {

        switch (method) {
            case "GET":
                response = request.get();
                break;
            case "POST":
                response = request.post();
                break;
            case "PUT":
                response = request.put();
                break;
            case "DELETE":
                response = request.delete();
                break;
        }
    }

    @Given("Prepare Headers")
    public void prepareHeaders(DataTable dt) {
        List<Map<String, String>> map = dt.asMaps(String.class, String.class);
        for (int i = 0; i < map.size(); i++) {
            request.headers(map.get(i).get("key"), map.get(i).get("value"));
        }
    }

    @Given("Prepare Request Body {string}")
    public void prepareRequestBody(String path, DataTable dt) throws Exception {
        List<Map<String, String>> map = dt.asMaps(String.class, String.class);
        jsonEdit(path);
        for (Map<String, String> stringStringMap : map) {
            if (getTypeOfValue(stringStringMap.get("key")).equals("String")) {
                jo.addProperty(stringStringMap.get("key"), dataConversionString(stringStringMap.get("value")));
            } else if (getTypeOfValue(stringStringMap.get("key")).equals("Number")) {
                jo.addProperty(stringStringMap.get("key"), dataConversionInteger(stringStringMap.get("value")));
            } else if (getTypeOfValue(stringStringMap.get("key")).equals("Boolean")) {
                jo.addProperty(stringStringMap.get("key"), dataConversionBoolean(stringStringMap.get("value")));
            }
        }
        request.body(jo);

    }


}

package steps;

import com.google.gson.*;
import groovy.util.logging.Slf4j;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public class PrepareRequest {
    public static RequestSpecification request;
    public static Response response;
    public static JsonObject jo;

    public void jsonEdit(String path) throws Exception {
        JsonObject jsonObject = (new JsonParser()).parse(readFileAsString("src/test/java/objects/" + path + ".json")).getAsJsonObject();
        jo = jsonObject.getAsJsonObject();
    }

    public static String getTypeOfValue(String field) {
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

    public static String getTypeOfValueResponse(String field) {
        JsonPath res = new JsonPath(response.getBody().asString());
        if (res.get(field).getClass().getName().equals("java.lang.Boolean"))
            return "Boolean";
        if (res.get(field).getClass().getName().equals("java.lang.String"))
            return "String";
        if (res.get(field).getClass().getName().equals("java.lang.Integer")) {
            return "Number";
        }
        return "Wrong";
    }

    public static String readFileAsString(String file) throws Exception {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static String dataConversionString(String value) {
        switch (value) {
            case "null":
                return null;
            case "whiteSpace":
                return "      ";
            case "noString":
                return "";
            default:
                return value;
        }
    }

    public static Integer dataConversionInteger(String value) {
        if (value.equals("null")) {
            return null;
        } else
            return Integer.valueOf(value);
    }

    public static Boolean dataConversionBoolean(String value) {
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
        request.body(jo);
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
        for (Map<String, String> value : map) {
            request.headers(value.get("key"), value.get("value"));
        }
    }

    @Given("Prepare Request Body {string}")
    public void prepareRequestBody(String path, DataTable dt) throws Exception {
        List<Map<String, String>> map = dt.asMaps(String.class, String.class);
        jsonEdit(path);
        for (Map<String, String> value : map) {
            if (getTypeOfValue(value.get("key")).equals("String")) {
                jo.addProperty(value.get("key"), dataConversionString(value.get("value")));
            } else if (getTypeOfValue(value.get("key")).equals("Number")) {
                jo.addProperty(value.get("key"), dataConversionInteger(value.get("value")));
            } else if (getTypeOfValue(value.get("key")).equals("Boolean")) {
                jo.addProperty(value.get("key"), dataConversionBoolean(value.get("value")));
            }
        }

    }

    @Given("Fields Control {string} at {string}")
    public void fieldsControlAt(String field, String request) throws Exception {
        jsonEdit(request);
        jo.remove(field);
    }
}

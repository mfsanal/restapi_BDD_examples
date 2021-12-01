package steps;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import endpoints.Endpoints;
import groovy.util.logging.Slf4j;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objects.Pair;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class MainTests {
    RequestSpecification request;
    Response response;

    @Test
    public void jsonEdit() throws IOException {
        //Gson gg = new Gson();
        //Object aa = gg.fromJson(new FileReader("src/test/java/objects/Request.json"), Object.class);
        // JsonReader reader = new JsonReader(new FileReader("src/test/java/objects/Request.json"));
        //FileReader fileReader = new FileReader("src/test/java/objects/Request.json");
        //String json = "{ \"name\": \"Baeldung\", \"java\": true }";
        //JsonObject js = new JsonParser().parse(fileReader).getAsJsonObject();
        //Pair[] reviews = new Gson().fromJson( , Pair[].class);
        //List<Pair> asList = Arrays.asList(reviews);

       // System.out.printf(String.valueOf(js.get("key")));

    }

    @Given("Prepare Url {string}")
    public void prepareUrl(String url) {
        RestAssured.baseURI = url;
        request = RestAssured.given();
    }

    @When("Send Request {string}")
    public void sendRequest(String method) {

        switch(method){
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
        request.headers(map.get(i).get("key"),map.get(i).get("value"));
        }

    }
}

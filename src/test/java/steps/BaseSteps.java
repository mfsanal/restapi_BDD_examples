package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static steps.PrepareRequest.response;

public class BaseSteps {

    @Then("Expected to see {int} status code")
    public void expectedToSeeStatusCode(int code) {
        Assert.assertEquals(code, response.statusCode());
    }

    @Then("Response Control")
    public void responseControl(DataTable dt) {
        List<Map<String, String>> map = dt.asMaps(String.class, String.class);
        JsonPath res= new JsonPath(response.getBody().asString());
        for (Map<String, String> value : map) {
            if (PrepareRequest.getTypeOfValueResponse(value.get("path")).equals("String")) {
                Assert.assertEquals(res.get(value.get("path")),PrepareRequest.dataConversionString(value.get("value")));
            } else if (PrepareRequest.getTypeOfValueResponse(value.get("path")).equals("Number")) {
                Assert.assertEquals(res.get(value.get("path")),PrepareRequest.dataConversionInteger(value.get("value")));
            } else if (PrepareRequest.getTypeOfValueResponse(value.get("path")).equals("Boolean")) {
                Assert.assertEquals(res.get(value.get("path")),PrepareRequest.dataConversionBoolean(value.get("value")));
            }
        }
    }

    @Then("Expected to see not null control")
    public void expectedToSeeNotNullControl(DataTable dt) {
        List<String> fields = dt.asList();
        for (String value : fields) {
            Assert.assertNotNull(response.jsonPath().get(value));
        }
    }
}

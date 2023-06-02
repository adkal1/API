import enums.StatusCode;
import responseJsonModel.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;


import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import org.testng.annotations.Test;
import utils.DataUtil;
import enums.ContentType;
import utils.JsonUtil;
import utils.LoggerUtil;

import java.util.UUID;


import static utils.DataUtil.*;
import static utils.Response.toJson;
import static utils.Response.getResponse;
import static utils.Response.setRouteParam;
import static utils.Response.setPostRequest;


public class RunTest {
    private static final LoggerUtil log = LoggerUtil.getInstance();

    @Test
    public void shouldReturnStatus() {
        log.logInfo("Step 1");
        HttpResponse<JsonNode> jsonResponsePosts = toJson(getResponse("posts"));
        JSONArray array = JsonUtil.getJsonArray(jsonResponsePosts);

        log.logInfo("Got all posts");
        Assert.assertTrue(jsonResponsePosts.getHeaders().get("Content-Type").get(0).contains(ContentType.JSON.getValue()), "The list in response body isn't json.");
        Assert.assertEquals(jsonResponsePosts.getStatus(), StatusCode.OK.toInt(), "Status code isn't 200.");
        Assert.assertTrue(JsonUtil.isAscendingJsonArray(array), "Posts aren't sorted ascending (by id).");

        log.logInfo("Step 2");
        HttpResponse<JsonNode> jsonResponsePosts99 = toJson(setRouteParam("posts", (String) getTest("name1"), (String) getTest("value1")));
        JSONObject objectJsonPosts99 = JsonUtil.getJsonObject(jsonResponsePosts99);

        log.logInfo("Got post with id=99");
        Assert.assertEquals(jsonResponsePosts99.getStatus(), StatusCode.OK.toInt(), "Status code isn't 200");
        Assert.assertEquals(String.valueOf(objectJsonPosts99.get("userId")), (String) getTest("userId1"), "UserId isn't correct");
        Assert.assertNotNull(objectJsonPosts99.getString("title"), "Title isn't correct");
        Assert.assertNotNull(objectJsonPosts99.getString("body"), "Body isn't correct");

        log.logInfo("Step 3");
        HttpResponse<JsonNode> jsonResponsePost150 = toJson(setRouteParam("posts", (String) getTest("name2"), (String) getTest("value2")));

        log.logInfo("Got post with id=150");
        Assert.assertEquals(jsonResponsePost150.getStatus(), StatusCode.NOTFOUND.toInt(), "Status code isn't 404");
        Assert.assertEquals(jsonResponsePost150.getBody().toString(), (String) getTest("body"), "Response body isn't empty");

        log.logInfo("Step 4");
        String title = UUID.randomUUID().toString();
        String body = UUID.randomUUID().toString();
        JSONObject post = new JSONObject();
        post.put("userId", (String) getTest("userId3"));
        post.put("title", title);
        post.put("body", body);
        HttpResponse<JsonNode> postJsonRequest = toJson(setPostRequest("users", post));
        JSONObject objectPostJsonRequest = JsonUtil.getJsonObject(postJsonRequest);

        log.logInfo("Created post with userId=1 and random body and title");
        Assert.assertEquals(postJsonRequest.getStatus(), StatusCode.CREATED.toInt(), "Status code isn't 201");
        Assert.assertEquals(objectPostJsonRequest.getString("title"), title, "Title isn't correct");
        Assert.assertEquals(objectPostJsonRequest.getString("body"), body, "Body isn't correct");
        Assert.assertEquals(String.valueOf(objectPostJsonRequest.getInt("userId")), (String) getTest("userId3"), "UserId isn't correct");
        Assert.assertTrue(objectPostJsonRequest.has("id"), "Id isn't present in response");

        log.logInfo("Step 5");
        HttpResponse<JsonNode> jsonGetUsers = toJson(getResponse("users"));
        JSONArray arrayUsers = JsonUtil.getJsonArray(jsonGetUsers);
        JSONObject objectUser = arrayUsers.getJSONObject(Integer.parseInt((String) getTest("value4")) - 1);
        User responseJsonTest = DataUtil.readUser(jsonReaderUser());
        User responseJsonUsers = DataUtil.readUser(objectUser.toString());

        log.logInfo("Got all users");
        Assert.assertTrue(jsonGetUsers.getHeaders().get("Content-Type").get(0).contains(ContentType.JSON.getValue()), "The list in response body isn't json");
        Assert.assertEquals(jsonGetUsers.getStatus(), StatusCode.OK.toInt(), "Status code isn't 200");
        Assert.assertEquals(responseJsonTest, responseJsonUsers, "Post information isn't correct: title, body, userId match data from request, id isn't present in response");

        log.logInfo("Step 6");
        HttpResponse<JsonNode> jsonGetUser = toJson(setRouteParam("users", (String) getTest("name4"), (String) getTest("value4")));
        User responseJsonUser = DataUtil.readUser(jsonGetUser.getBody().toString());

        log.logInfo("Got user with id=5");
        Assert.assertEquals(responseJsonUsers, responseJsonUser, "User data doesn't match with user data in the previous step");
        Assert.assertEquals(jsonGetUser.getStatus(), StatusCode.OK.toInt(), "Status code isn't 200");
    }
}

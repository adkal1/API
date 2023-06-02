package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import enums.ContentType;
import org.json.JSONObject;


public class Response {
    private static final LoggerUtil log = LoggerUtil.getInstance();
    private static final String urlString = (String) DataUtil.getConfig("url");

    public static GetRequest getResponse(String request) {
        StringBuilder url = new StringBuilder(urlString);
        url.append(request);
        return Unirest.get(url.toString());
    }

    public static HttpResponse<JsonNode> toJson(GetRequest response) {
        try {
            return response.asJson();
        } catch (UnirestException e) {
            log.logError("Response doesn't convert to Json");
            throw new RuntimeException(e);
        }
    }

    public static HttpResponse<JsonNode> toJson(RequestBodyEntity response) {
        try {
            return response.asJson();
        } catch (UnirestException e) {
            log.logError("Response doesn't convert to Json");
            throw new RuntimeException(e);
        }
    }

    public static GetRequest setRouteParam(String request, String name, String value) {
        log.logInfo("Setting route parameter");
        StringBuilder url = new StringBuilder(urlString);
        url.append(request);
        url.append("/{");
        url.append(name);
        url.append("}");
        return Unirest.get(url.toString()).routeParam(name, value);
    }

    public static RequestBodyEntity setPostRequest(String request, JSONObject post) {

        return postResponse(request)
                .header("Content-Type", ContentType.JSON.getValue())
                .body(post);
    }

    private static HttpRequestWithBody postResponse(String request) {
        StringBuilder url = new StringBuilder(urlString);
        url.append(request);
        return Unirest.post(url.toString());
    }
}

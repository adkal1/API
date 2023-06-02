package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
    public static JSONArray getJsonArray(HttpResponse<JsonNode> response) {
        return new JSONArray(response.getBody().toString());
    }

    public static JSONObject getJsonObject(HttpResponse<JsonNode> response) {
        return new JSONObject(response.getBody().toString());
    }

    public static boolean isAscendingJsonArray(JSONArray array) {
        int i = 1;
        boolean flag = true;
        while (i < array.length() & flag) {
            JSONObject object = array.getJSONObject(i);
            JSONObject oldObject = array.getJSONObject(i - 1);
            if (object.getInt("id") - oldObject.getInt("id") != 1) {
                flag = false;
            }
            ++i;
        }
        return flag;
    }
}

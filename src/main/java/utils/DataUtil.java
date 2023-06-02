package utils;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import responseJsonModel.User;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class DataUtil {
    private static final File testData = new File("src/test/resources/testData.json");
    private static final File fileConfig = new File("src/main/resources/configData.json");
    private static final File testUser = new File("src/test/resources/testUser.json");
    private static final LoggerUtil log = LoggerUtil.getInstance();


    public static Object getConfig(String key) {
        return parse(jsonReader(fileConfig)).get(key);
    }

    public static Object getTest(String key) {
        return parse(jsonReader(testData)).get(key);
    }

    public static String jsonReaderUser() {
        return jsonReader(testUser);
    }

    public static User readUser(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, User.class);
    }

    private static String jsonReader(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            log.logError("File doesn't read");
            throw new RuntimeException(e);
        }
        String jsonString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return jsonString;
    }

    private static JSONObject parse(String jsonString) {
        try {
            return (JSONObject) new JSONParser().parse(jsonString);
        } catch (ParseException e) {
            log.logError("String of Json doesn't parse");
        }
        return null;
    }
}

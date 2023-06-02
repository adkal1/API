package enums;

public enum ContentType {
    JSON("application/json"),
    JSONP("application/javascript"),
    JS("application/x-javascript"),
    JSONPO("text/javascript"),
    JSO("text/x-javascript"),
    JSONO("text/x-json");


    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

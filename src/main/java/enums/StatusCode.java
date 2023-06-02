package enums;

public enum StatusCode {
    OK(200),
    CREATED(201),
    NOTFOUND(404);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }

}

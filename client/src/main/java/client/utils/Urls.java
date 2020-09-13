package client.utils;

public class Urls {
    public static final String BASE_URI = "http://localhost:8080/talkdesk/api/call/";

    public static final String CREATE_CALLS_URI = BASE_URI + "create-calls";

    public static final String GET_ALL_CALLS = BASE_URI + "get-calls?";

    public static final String DELETE_CALL_BY_ID = BASE_URI + "delete-call/";

    public static final String GET_CALLS_STATISTICS = BASE_URI + "statistics-calls";
}

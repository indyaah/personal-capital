package pro.anuj.challenge.pc.api;

import lombok.Data;

import java.util.Map;

@Data
public class Response {
    private final String body;
    private final Map<String, String> headers;
    private final int statusCode;
}

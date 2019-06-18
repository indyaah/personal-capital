package pro.anuj.challenge.pc.api;

import lombok.Data;

import java.util.Map;

@Data
public class Request {

    private Map<String, String> queryStringParameters;

}

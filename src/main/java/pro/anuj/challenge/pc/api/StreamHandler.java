package pro.anuj.challenge.pc.api;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import pro.anuj.challenge.pc.RequestSigningInterceptor;
import pro.anuj.challenge.pc.config.AppConfig;
import pro.anuj.challenge.pc.elasticsearch.ElasticSearchService;
import pro.anuj.challenge.pc.response.ErrorMessage;
import pro.anuj.challenge.pc.response.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

abstract class StreamHandler implements RequestHandler<Map<String, Object>, Response> {
    private static final int SC_OK = 200;
    private static int SC_BAD_REQUEST = 400;
    private static int SC_INTERNAL_ERROR = 500;

    private final AppConfig config = new AppConfig();
    private final HttpHost httpHost = new HttpHost(config.getHost(), config.getPort(), config.getProtocol());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String indexName = Optional.ofNullable(System.getenv("INDEX_NAME")).orElse("plans");
    private final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));
    private final ElasticSearchService service = new ElasticSearchService(client, objectMapper, indexName);

    private Map<String, String> APPLICATION_JSON = Collections.singletonMap("Content-Type", "application/json");


    Response handleInternal(Map<String, Object> input, String[] terms) {

        try {
            //noinspection unchecked
            Map<String, String> queryParams = (Map<String, String>) input.get("queryStringParameters");
            if (queryParams == null || isNullOrEmpty(queryParams.get("q"))) {
                return handleError("Bad Request", SC_BAD_REQUEST);
            }
            List<Map<String, Object>> results = service.executeQuery(queryParams.get("q"), terms);
            return new Response<>(objectMapper.writeValueAsString(results), APPLICATION_JSON, SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            return handleError("Internal Server Error", SC_INTERNAL_ERROR);
        }
    }

    private Response<ErrorMessage> handleError(String msg, int status) {
        return new Response<>(new ErrorMessage(msg, status), APPLICATION_JSON, status);
    }

    private boolean isNullOrEmpty(final String string) {
        return string == null || string.isEmpty();
    }

}
package pro.anuj.challenge.pc.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ElasticSearchService implements SearchService {

    private final RestHighLevelClient restClient;
    private final ObjectMapper objectMapper;
    private final String indexName;

    @Override
    public List<Map<String, Object>> executeQuery(String value, String[] fields) throws IOException {
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(value, fields)
                .fuzziness(Fuzziness.AUTO)
                .maxExpansions(10);
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(multiMatchQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restClient.search(searchRequest);
        return Arrays.stream(search.getHits().getHits()).map(SearchHit::getSourceAsMap)
                .collect(Collectors.toList());

    }
}

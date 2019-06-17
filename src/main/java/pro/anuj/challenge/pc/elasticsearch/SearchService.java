package pro.anuj.challenge.pc.elasticsearch;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {
    List<Map<String, Object>> executeQuery(String value, String[] fields) throws IOException;
}


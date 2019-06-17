package pro.anuj.challenge.pc.api;

import com.amazonaws.services.lambda.runtime.Context;
import pro.anuj.challenge.pc.response.Response;

import java.util.Map;

public class PlanNameSearchHandler extends StreamHandler implements TermSearcher {

    private static final String[] TERMS = {"PLAN_NAME", "LAST_RPT_PLAN_NAME"};

    @Override
    public String[] getTerms() {
        return TERMS;
    }

    @Override
    public Response handleRequest(Map<String, Object> input, Context context) {
        return handleInternal(input, TERMS);
    }
}

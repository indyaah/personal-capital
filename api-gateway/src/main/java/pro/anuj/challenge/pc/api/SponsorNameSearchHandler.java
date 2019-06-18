package pro.anuj.challenge.pc.api;

import com.amazonaws.services.lambda.runtime.Context;
import pro.anuj.challenge.pc.api.response.Response;

import java.util.Map;

public class SponsorNameSearchHandler extends StreamHandler implements TermSearcher {

    private static final String[] TERMS = {"SPONSOR_DFE_NAME", "LAST_RPT_SPONS_NAME"};


    @Override
    public String[] getTerms() {
        return TERMS;
    }


    @Override
    public Response handleRequest(Map<String, Object> stringObjectMap, Context context) {
        return handleInternal(stringObjectMap, TERMS);
    }
}

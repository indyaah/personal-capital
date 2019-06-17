package pro.anuj.challenge.pc.api;

import com.amazonaws.services.lambda.runtime.Context;
import pro.anuj.challenge.pc.response.Response;

import java.util.Map;

public class SponsorStateSearchHandler extends StreamHandler implements TermSearcher {

    private static final String[] TERMS = {"SPONS_DFE_MAIL_US_STATE", "SPONS_DFE_LOC_US_STATE"};

    @Override
    public String[] getTerms() {
        return TERMS;
    }

    @Override
    public Response handleRequest(Map<String, Object> input, Context context) {
        return handleInternal(input, getTerms());
    }
}

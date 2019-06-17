package pro.anuj.challenge.pc.api;

import com.amazonaws.services.lambda.runtime.Context;
import pro.anuj.challenge.pc.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

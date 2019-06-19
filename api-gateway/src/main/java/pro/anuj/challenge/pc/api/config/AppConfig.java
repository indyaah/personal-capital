package pro.anuj.challenge.pc.api.config;

public class AppConfig {


    public Integer getPort() {
        return Integer.parseInt(System.getenv(Constants.ES_PORT));
    }

    public String getHost() {
        return System.getenv(Constants.ES_HOST);
    }

    public String getProtocol() {
        return System.getenv(Constants.ES_PROTOCOL);
    }


    public boolean isAwsElasticSearch() {
        return getHost().endsWith("es.amazonaws.com");
    }
}

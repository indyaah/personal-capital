package pro.anuj.challenge.pc.config;

import java.util.Properties;

public class AppConfig {

    private final Properties properties = System.getProperties();

    public Integer getPort() {
        return Integer.parseInt(properties.getProperty(Constants.ES_PORT, "9200"));
    }

    public String getHost() {
        return properties.getProperty(Constants.ES_HOST, "localhost");
    }

    public String getProtocol() {
        return properties.getProperty(Constants.ES_PROTOCOL, "http");
    }

    public String getBucketName() {
        return properties.getProperty(Constants.S3_BUCKET_NAME, "f5500-test");
    }

    public String getObjectKey() {
        return properties.getProperty(Constants.S3_OBJECT_KEY, "f_5500_2017_latest.csv");
    }

    public boolean isAwsElasticSearch() {
        return getHost().endsWith("es.amazonaws.com");
    }
}

# Personal Capital Challenge

## Challnege 1: 
##### Ingest a CSV file into ElasticSearch and expose an API Gateway endpoint for allowing users to search based on certain fields.

### Major Infrastructure Components
1. AWS ElasticSearch Service (ElasticSearch v6.7.0) : Managed search engine service
2. AWS Api Gateway
3. AWS Lambda
4. AWS EC2
5. API Gateway

# Building

1. Use `infra/build.sh` to setup AWS environment, you'd need AWS credentials of a user with cloudformation permissions.
2. The script should setup everything except for API Gateway and Lambda for test
3. For lambda:
    1. `mvn clean package` from root
    2. `sam package 
            --template-file template.yaml 
            --output-template-file packaged.yaml
            --s3-bucket BUCKET_NAME`
    3. `aws cloudformation deploy --template-file packaged.yaml --stack-name test-2 --capabilities CAPABILITY_IAM --region us-west-2`

## Challenge 2:
##### Predict returns on investment using monte carlo simulattion
1. `mvn clean package` from root
2. `java -jar simulator/target/simulator-1.0.0.jar`
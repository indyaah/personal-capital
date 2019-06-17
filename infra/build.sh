#!/usr/bin/env bash

function usage() {
    echo "$0 <usage>"
    echo " "
    echo "options:"
    echo -e "--help \t Show options for this script"
    echo -e "--stack-name \t Stack Name to given while deploying, Default: 'personal-capital'."
    echo -e "--bucket-name \t S3 bucket used for storing templates, Default: 'templates.anuj.pro'r (read-only public bucket)"
    echo -e "--push \t Enable or Disable template pushing template to bucket of your choice."
    echo -e "--deploy \t Create new stack"
    echo -e "--key-name \t KeyPair Name from AWS that can be attached with bastion/logstash instances."
    echo -e "--az \t List of two AZs in with comma double escaped. e.g 'us-west-2a\\,us-west-2b'"

}


# extract options and their arguments into variables.
while true; do
    case "$1" in
        -h | --help)
            usage
            exit 1
            ;;
        --bucket-name)
            BUCKET_NAME="$2";
            shift 2
            ;;
        --stack-name)
            STACK_NAME="$2";
            shift 2
            ;;
        --push)
            PUSH_TO_S3="$2";
            shift 2
            ;;
        --deploy)
            DEPLOY_STACK="$2";
            shift 2
            ;;
        --key-name)
            KEY_PAIR_NAME="$2";
            shift 2
            ;;
        --az)
            AZ="$2";
            shift 2
            ;;
        --)
            break
            ;;
        *)
            break
            ;;
    esac
done

COMMIT_HASH=$(git rev-parse --verify HEAD)
TEMPLATE_BUCKET_NAME=${BUCKET_NAME:-templates.anuj.pro}
ESCAPED_TEMPLATE_BUCKET_NAME=${TEMPLATE_BUCKET_NAME//./\\.}
FINAL_STACK_NAME=${STACK_NAME:-personal-capital}

function build() {
    BUILD_DIR="./build/${COMMIT_HASH}"
    mkdir -p ${BUILD_DIR}
    sed "s/TEMPLATE_BUCKET_NAME/$ESCAPED_TEMPLATE_BUCKET_NAME/g;s/TEMPLATE_OBJECT_PREFIX/$COMMIT_HASH/g;" ./fullstack/master.yaml > ${BUILD_DIR}/master.yaml
    cp *.yaml ${BUILD_DIR}/
}

function push() {
    aws s3 sync ${BUILD_DIR}/ s3://${TEMPLATE_BUCKET_NAME}/${COMMIT_HASH}
}

function deploy() {
    aws cloudformation create-stack \
        --region us-west-2 --stack-name ${FINAL_STACK_NAME} \
        --template-url "https://$TEMPLATE_BUCKET_NAME.s3.amazonaws.com/$COMMIT_HASH/bastion.yaml" \
        --parameters "ParameterKey=AvailabilityZones,ParameterValue=${AZ},ParameterKey=KeyPairName,ParameterValue=${KEY_PAIR_NAME}" \
        --capabilities CAPABILITY_IAM
}

if [[ ${PUSH_TO_S3} == "true" ]]; then
   build
   push
fi

if [[ ${DEPLOY_STACK} == "true" ]]; then
   if [[ -z ${KEY_PAIR_NAME} ]];then
        echo "Missing parameter key pair name"
        usage
        exit 1
    fi
   if [[ -z ${AZ} ]];then
        echo "Missing parameter AZ name"
        usage
        exit 1
    fi
   build
   push
   deploy
fi


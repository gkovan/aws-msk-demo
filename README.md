# aws-msk-demo
aws msk demo app

## Kafka setup on local laptop

Intall Kafka 2.6.2

Start Zookeeper

```bash
./bin/zookeeper-server-start.sh config/zookeeper.properties
```

Start Kafka server/broker
```bash
./bin/kafka-server-start.sh config/server.properties
```

## Useful Kafka commands

Describe Kafka topics
```bash
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --describe
```

List Kafka topics
```bash
./bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
```

Delete Kafka topic
```bash
./bin/kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo
```

## Pipe Streaming Kafka example 

The pipe stream app basically takes the value on the input topic and puts it on the output topic.
For the pipe stream app to work, the following topics needs to be created:

```bash
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-pipe-input
```
```bash
./bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic streams-pipe-output
```

Start a kafka producer and consumer as follows:

```bash
./bin/kafka-console-producer.sh --broker-list localhost:9092 --topic streams-pipe-input
```
```bash
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic streams-pipe-output --from-beginning
```

## Java code to get a secret

```
// Use this code snippet in your app.
// If you need more information about configurations or implementing the sample code, visit the AWS docs:
// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-samples.html#prerequisites

public static void getSecret() {

    String secretName = "AmazonMSK_gk_cluster_secret_2";
    String region = "us-east-1";

    // Create a Secrets Manager client
    AWSSecretsManager client  = AWSSecretsManagerClientBuilder.standard()
                                    .withRegion(region)
                                    .build();
    
    // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
    // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
    // We rethrow the exception by default.
    
    String secret, decodedBinarySecret;
    GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                    .withSecretId(secretName);
    GetSecretValueResult getSecretValueResult = null;

    try {
        getSecretValueResult = client.getSecretValue(getSecretValueRequest);
    } catch (DecryptionFailureException e) {
        // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
        // Deal with the exception here, and/or rethrow at your discretion.
        throw e;
    } catch (InternalServiceErrorException e) {
        // An error occurred on the server side.
        // Deal with the exception here, and/or rethrow at your discretion.
        throw e;
    } catch (InvalidParameterException e) {
        // You provided an invalid value for a parameter.
        // Deal with the exception here, and/or rethrow at your discretion.
        throw e;
    } catch (InvalidRequestException e) {
        // You provided a parameter value that is not valid for the current state of the resource.
        // Deal with the exception here, and/or rethrow at your discretion.
        throw e;
    } catch (ResourceNotFoundException e) {
        // We can't find the resource that you asked for.
        // Deal with the exception here, and/or rethrow at your discretion.
        throw e;
    }

    // Decrypts secret using the associated KMS key.
    // Depending on whether the secret is a string or binary, one of these fields will be populated.
    if (getSecretValueResult.getSecretString() != null) {
        secret = getSecretValueResult.getSecretString();
    }
    else {
        decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
    }

    // Your code goes here.
}
```

Requires AWS Java SDK.  See: https://github.com/aws/aws-sdk-java-v2/#using-the-sdk


## VS code setup

### define a .env file

```
AWS_SECRETS_PATH=secrets
```

### Create secrets file

Create file called ./secrets/amazonmsk-fltops-msapp-user

Contents of file should be in form:

```
{
    "username": "gerry",
    "password": "dummypw"
}
```

### .vscode/launch.json file

`Note` that the .env file is configured here.

```
{
    // Use IntelliSense to learn about possible attributes.
    // Hover to view descriptions of existing attributes.
    // For more information, visit: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Launch Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
        {
            "type": "java",
            "name": "Launch DemoApplication",
            "request": "launch",
            "mainClass": "com.gk.aws.msk.demo.DemoApplication",
            "projectName": "demo",
            "envFile": "${workspaceFolder}/.env"
        }
    ]
}
```
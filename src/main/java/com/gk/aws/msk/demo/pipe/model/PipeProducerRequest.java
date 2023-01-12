package com.gk.aws.msk.demo.pipe.model;

public class PipeProducerRequest {

    private String kafkaKey;
    private String kafkaHeader;
    private KafkaBody kafkaBody;
    private boolean synchronousProducer;

    public boolean isSynchronousProducer() {
        return synchronousProducer;
    }
    public void setSynchronousProducer(boolean synchronousProducer) {
        this.synchronousProducer = synchronousProducer;
    }
    public String getKafkaKey() {
        return kafkaKey;
    }
    public void setKafkaKey(String kafkaKey) {
        this.kafkaKey = kafkaKey;
    }
    public String getKafkaHeader() {
        return kafkaHeader;
    }
    public void setKafkaHeader(String kafkaHeader) {
        this.kafkaHeader = kafkaHeader;
    }
    public KafkaBody getKafkaBody() {
        return kafkaBody;
    }
    public void setKafkaBody(KafkaBody kafkaBody) {
        this.kafkaBody = kafkaBody;
    }
    @Override
    public String toString() {
        return "PipeProducerRequest [kafkaKey=" + kafkaKey + ", kafkaHeader=" + kafkaHeader + ", kafkaBody=" + kafkaBody
                + ", synchronousProducer=" + synchronousProducer + "]";
    }

    
}

package com.gk.aws.msk.demo.pipe.model;

public class PipeProducerResponse {
	private String status;
	private String topic;
	private String partition;
	private String offset;
	private String kafkaBody;
	private String kafkaKey;
	private String kafkaHeader;
	private boolean synchronousProducer;

	public PipeProducerResponse() {
		super();
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

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getStatus() {
		return status;
	}

	public String getKafkaBody() {
		return kafkaBody;
	}

	public void setKafkaBody(String kafkaBody) {
		this.kafkaBody = kafkaBody;
	}

	public boolean isSynchronousProducer() {
        return synchronousProducer;
    }
    public void setSynchronousProducer(boolean synchronousProducer) {
        this.synchronousProducer = synchronousProducer;
    }

	@Override
	public String toString() {
		return "PipeProducerResponse [status=" + status + ", topic=" + topic + ", partition=" + partition + ", offset="
				+ offset + ", kafkaBody=" + kafkaBody + ", kafkaKey=" + kafkaKey + ", kafkaHeader=" + kafkaHeader
				+ ", synchronousProducer=" + synchronousProducer + "]";
	}
	
	
}

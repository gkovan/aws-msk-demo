package com.gk.aws.msk.demo.pipe.model;

public class PipeProducerResponse {
	private String status;
	
	public PipeProducerResponse(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}

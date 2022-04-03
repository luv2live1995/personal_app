package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseMessage {
	
	private String message;

	public ResponseMessage(String message) {
		super();
		this.message = message;
	}
	
}

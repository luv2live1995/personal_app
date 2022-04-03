package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransmittedFile {
	
	private String name;
	private String url;
	private String type;
	private long size;
	
	public TransmittedFile(String name, String url, String type, long size) {
		super();
		this.name = name;
		this.url = url;
		this.type = type;
		this.size = size;
	}
	
}

package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="dictionaries")
public class Dictionary implements Serializable {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	// Constructor
	public Dictionary() {
		
	}
	
	// Constructor with params
	public Dictionary(String eng_word, String jap_word, String vie_word) {
		this.eng_word = eng_word;
		this.jap_word = jap_word;
		this.vie_word = vie_word;
	}
	
	// Auto-incremented ID
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	// 英語
	@Column(name="eng_word")
	private String eng_word;
	
	// 日本語
	@Column(name="jap_word")
	private String jap_word;
	
	// ベトナム語
	@Column(name="vie_word")
	private String vie_word;	

}

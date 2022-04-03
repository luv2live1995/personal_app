package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Dictionary;
import com.example.demo.repository.DictionaryRepository;

@Service
public class DictionaryService {
	
	@Autowired
	DictionaryRepository dictionaryRepository;
	
	@Transactional
	public List<Dictionary> getWordOnSearchString(String searchStr) {
		
		List<Dictionary> words = (List<Dictionary>) dictionaryRepository.findAll();
		List<Dictionary> hitWords = new ArrayList<Dictionary>();
		
		for(int i = 0; i < words.size(); i++) {
			Dictionary word = words.get(i);
			
			if(checkSearchStringContainedInWord(word, searchStr)) {
				hitWords.add(word);
			}
		}
		
		return hitWords;
	}
	
	public boolean checkSearchStringContainedInWord(Dictionary word, String searchStr) {
		
		return (word.getEng_word().contains(searchStr) 
				|| word.getJap_word().contains(searchStr) 
				|| word.getVie_word().contains(searchStr));
		
	} 
	
	
}

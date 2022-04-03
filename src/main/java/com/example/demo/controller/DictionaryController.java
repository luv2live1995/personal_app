package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Dictionary;
import com.example.demo.repository.DictionaryRepository;
import com.example.demo.service.DictionaryService;

@RestController
@RequestMapping(path="/dictionary")
public class DictionaryController {
	
	@Autowired
 	private DictionaryRepository dictionaryRepository;
	
	@Autowired
	private DictionaryService dictionaryService;
    
    @RequestMapping(value="/getAllWords", method = RequestMethod.GET)
    @ResponseBody
    public List<Dictionary> getAllWords(){
        
        List<Dictionary> words = (List<Dictionary>) dictionaryRepository.findAll();
        
        return words;
    }
    
    @RequestMapping(value="/getWordOnSearchText", method = RequestMethod.GET)
    @ResponseBody
    public List<Dictionary> getWordOnSearchText(@RequestParam String searchText){
        
        List<Dictionary> words = dictionaryService.getWordOnSearchString(searchText);
        return words;
        
    }
    
}

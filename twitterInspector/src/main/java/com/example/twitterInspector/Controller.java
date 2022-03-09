package com.example.twitterInspector;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import dao.TwitterDao;


@RestController
public class Controller {

	@PostMapping(value="/saveTwitterData", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> extractTwitterData(String query)
	{
		ArrayList<TwitterDao> ar = new ArrayList<>();
		TwitterClass tc = new TwitterClass();
		ar = tc.searchtweets(query);
		
		Database db = new Database();
		db.saveDB1(ar);
		
		return new ResponseEntity<String>(ar.toString(),HttpStatus.ACCEPTED);		
	} 
	
	@PostMapping(value="/analyseTwitterData", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> analyseTwitterData(String query) throws Exception
	{
		ArrayList<TwitterDao> ar = new ArrayList<>();
		
		Database db = new Database();
		ar = db.getTweetsfromDB1();
		int i = 0 ;
		while(i < 15)
		{
			ParallelDots pd = new ParallelDots();
			ar.get(i).setSentiment(pd.evalSentiment(ar.get(i).getTweet()));
			//ar.get(i).setEmotion(pd.emotionDetect((ar.get(i).getTweet())));
			//ar.get(i).setIntent(pd.intentAnalysisOld((ar.get(i).getTweet())));
			i++;
		}
		db.saveTwitterResults(ar);
		return new ResponseEntity<String>(ar.toString(),HttpStatus.ACCEPTED);		
	} 
	
	
}

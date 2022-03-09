package com.example.twitterInspector;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import org.json.JSONObject;

import com.paralleldots.paralleldots.App;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ParallelDots {
	
	public String emotionDetect(String content) throws Exception
	{
		App pd = new App("4yAEifMKBf51AWMPBbMawbeUurqOnK12cioAmtfNYLY");

		// for single sentences

		    String emotion = pd.emotion(content);
		    return emotion;

		
	}
	
	public String abusiveSpeech(String content)throws Exception 
	{
		App pd = new App("ZE8nKaZ99Pmte1bF0mXL5vRHnY8tbqSh9EmJGDgxT0E");
		// for single sentences
		   String abuse = pd.abuse(content);
		   JSONObject object = new JSONObject(abuse);
		   Double abusive = object.getDouble("abusive");
		   Double hate_speech = object.getDouble("hate_speech");
		   Double neither = object.getDouble("neither");
		   if(abusive> hate_speech && abusive > neither)
		   {
			   return "Abusive Speech";
		   }
		   else if (hate_speech > abusive && hate_speech > neither)
		   {
			   return "Hate Speech";
		   }
		   else
		   {
			   return "No Hate or Abusive speeech found !";
		   }
		   
	}
	
	public String intentAnalysisOld(String content) throws Exception
	{
		App pd = new App("ZE8nKaZ99Pmte1bF0mXL5vRHnY8tbqSh9EmJGDgxT0E");
		// for single sentences
			int flag = 0 ;
		double feedback =0 ;
		   String intent = pd.intent(content);
		   JSONObject intent_response = new JSONObject(intent);
		   System.out.println(intent_response);
		   JSONObject response= intent_response.getJSONObject("intent");
		   double news = response.getDouble("news");
		   double query = response.getDouble("query");
		   double spam = response.getDouble("spam");
		   double marketing = response.getDouble("marketing");
		   if(response.get("feedback").getClass().toString().equalsIgnoreCase("class org.json.JSONObject"))
				   {
			   JSONObject feed = response.getJSONObject("feedback");
			   JSONObject tag = feed.getJSONObject("tag");
			   Double complain = tag.getDouble("complaint");
			   Double suggestion = tag.getDouble("suggestion");
			   Double appreciation = tag.getDouble("appreciation");
			   			if(complain > suggestion && complain > appreciation)
			   			{
			   				return "Complaint";
			   			}
			   			if(suggestion > complain && suggestion > appreciation)
			   			{
			   				return "Suggestion";
			   			}
			   			else {
			   				return "Appreciation";
			   			}
				   }
		   else if(response.get("feedback").getClass().toString().equalsIgnoreCase("java.lang.Double"))
		   {
			  feedback = response.getDouble("feedback");
		   }
		   
		
		   
		   if(news > query && news > spam && news >marketing && news >feedback)
		   {
			   return "News";
		   }
		   else if(query > news && query > spam && query >marketing && query >feedback)
		   {
			   return "Query";
		   }
		   else if(spam > news && spam > query && spam > marketing && spam > feedback)
		   {
			   return "Spam";
		   }
		   else if(marketing > news && marketing > query && marketing > spam && marketing > feedback)
		   {
			   return "Marketing";
		   }
		   else
		   {
			   return "Feedback";
		   }
		   
	}

	//new/intent
	public ArrayList<String> intentAnalysis(String content) throws IOException
	{
		System.out.println(content);
		ArrayList<String> intent = new ArrayList<>();
		try {
		String api_key = "ZE8nKaZ99Pmte1bF0mXL5vRHnY8tbqSh9EmJGDgxT0E";
		String text = content;
		String query = "feedback";
		String url = "https://apis.paralleldots.com/v4/new/intent";
		    OkHttpClient client = new OkHttpClient();
		            RequestBody requestBody = new MultipartBody.Builder()
		            .setType(MultipartBody.FORM)
		            .addFormDataPart("api_key", api_key)
		            .addFormDataPart("text", text)
		            .addFormDataPart("query", query)
		            .build();
		            Request request = new Request.Builder()
		              .url(url)
		              .post(requestBody)
		              .addHeader("cache-control", "no-cache")
		              .build();
		Response response = client.newCall(request).execute();
		String s = response.body().string();
		 JSONObject object = new JSONObject(s);
		    JSONObject sentiments = object.getJSONObject("feedback");
		    System.out.println(sentiments);
		    Double comp = sentiments.getDouble("complaint");
		    Double sugg = sentiments.getDouble("suggestion");
		    Double app = sentiments.getDouble("appreciation");
		    comp = (double) Math.round(comp * 100);
		    sugg = (double) Math.round(sugg * 100);
		    app = (double) Math.round(app * 100);
		    System.out.println("Complaint= "+comp);
		    System.out.println("Suggestion= "+sugg);
		    System.out.println("Appreciation= "+app);
		    if (comp > sugg && comp > app)
		    {
		    	intent.add("Complaint");
		    	String n = Double.toString(comp);
		    	intent.add(n);
		    	return intent;
		    }
		    else
		    {
		    	intent.add("nil");
		    	String n = Double.toString(app);
		    	intent.add(n);
		    	return intent;
		    }
		}
		    
		    catch(Exception e)
			{
				intent.clear();
				intent.add("error");
				return intent;
			}
		
	}
	
	
	//sentiment
	public String evalSentiment(String msg) 
	{
		
		ArrayList<String> senti = new ArrayList<>();
		try {
		
		App pd = new App("ZE8nKaZ99Pmte1bF0mXL5vRHnY8tbqSh9EmJGDgxT0E");
		// for single sentences
		    String sentiment = pd.sentiment(msg);
		    JSONObject object = new JSONObject(sentiment);
		    JSONObject sentiments = object.getJSONObject("sentiment");
		    Double negative = sentiments.getDouble("negative");
		    Double neutral = sentiments.getDouble("neutral");
		    Double positive = sentiments.getDouble("positive");
		    negative = (double) Math.round(negative * 100);
		    neutral = (double) Math.round(neutral * 100);
		    positive = (double) Math.round(positive * 100);
		    if (negative > neutral && negative >positive)
		    {
		    	return "Negative";
		    }
		    else if (neutral > positive)
		    {
		    return "Neutral";
		    }
		    else
		    {
		    return "Positive";
		    }
		
		}
		catch(Exception e)
		{
			return "error";
		}
		
		 }
	
	public static void main(String args[]) throws Exception
	{
		ParallelDots pd = new ParallelDots();
		System.out.println(pd.emotionDetect("He is not a bad person"));
	}

}

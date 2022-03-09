package com.example.twitterInspector;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PerspectiveAPI {

	public double calToxicity(String msg)
	{
		
		 
		        HttpClient httpclient = HttpClients.createDefault();
		       
		        try
		        {
		            URIBuilder builder = new URIBuilder("https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=AIzaSyDYNxgWI88Km4Q6lhGi16WPOMEffdiwFes");

		            URI uri = builder.build();
		            HttpPost request = new HttpPost(uri);
		            request.setHeader("Content-Type", "application/json");	


		            // Request body
		            StringEntity reqEntity = new StringEntity("{comment: {text: \"  "+msg+"    \"},\r\n" + 
		            		"       languages: [\"en\"],\r\n" + 
		            		"       requestedAttributes: {TOXICITY:{}} }");
		            request.setEntity(reqEntity);

		            HttpResponse response = httpclient.execute(request);
		            HttpEntity entity = response.getEntity();
		            
		            if (entity != null) 
		            {
		            	String s = EntityUtils.toString(entity);
		   			 	JSONObject object = new JSONObject(s);
		   			 	System.out.println(object);
		   			 	if(object.has("attributeScores"))
		   			 	{
		   			 	JSONObject attributeScores = object.getJSONObject("attributeScores");
		   			 	JSONObject toxicity = attributeScores.getJSONObject("TOXICITY");
		   			 JSONObject summaryScore = toxicity.getJSONObject("summaryScore");
		                Double value = summaryScore.getDouble("value");
		                value = (double) Math.round(value * 100);
		                return value;
		               
		   			 	}
		            }
		        }
		        catch (Exception e)
		        {
		            e.printStackTrace();
		        }
		return -1;
		
		 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PerspectiveAPI ap= new PerspectiveAPI();
		System.out.println(ap.calToxicity("What a crap product"));
	}

}

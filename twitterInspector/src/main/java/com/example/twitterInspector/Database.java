package com.example.twitterInspector;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import dao.TwitterDao;

public class Database {

	public int checkLogin(String user, String password) {
		int response = 0;
		try {
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Users");

		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			if (document.getString("user").equals(user) && document.getString("password").equals(password)) {
				response =1;
			}

		}
		mongo.close();
		}
		catch(Exception e)
		{
			response = -1;
		}
		return response;
	}
	
	public JSONArray readcontentfromJSON() {
		JSONArray response = null;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("C:\\json/dummy-data.json"));
			org.json.simple.JSONArray ar = (org.json.simple.JSONArray) obj;
			String s = ar.toString();
			response = new JSONArray(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public JSONArray readinstafromJSON() {
		JSONArray response = null;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("C:\\json/instagram.json"));
			org.json.simple.JSONArray ar = (org.json.simple.JSONArray) obj;
			String s = ar.toString();
			response = new JSONArray(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public int saveTwitterResults(ArrayList<String> original, ArrayList<ArrayList<String>> moderateText,
			ArrayList<String> intent, ArrayList<String> sentiment, ArrayList<String> emotion,
			ArrayList<String> image_res, ArrayList<String> toxicity, String company) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("ContentModeration");
			MongoCollection<Document> collection = db.getCollection("TwitterResults");

			int i = 0;
			System.out.println(original.size());
			while (i < original.size()) {
				Document doc = new Document();
				doc.append("tweet", original.get(i).toString());
				doc.append("bad words", moderateText.get(i).toString());
				doc.append("intent", intent.get(i).toString());
				doc.append("sentiment", sentiment.get(i).toString());
				doc.append("emotion", emotion.get(i).toString());
				doc.append("image", image_res.get(i).toString());
				doc.append("review", toxicity.get(i).toString());
				doc.append("company", company);
				collection.insertOne(doc);
				i++;
			}
			mongo.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public int saveTwitterResults(ArrayList<TwitterDao> ar) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("ContentModeration");
			MongoCollection<Document> collection = db.getCollection("Tweets");
			
			int i = 0;
			while (i < 15) {
				Bson query = Filters.eq("Id", ar.get(i).getId());
				 Bson updates = Updates.combine(Updates.set("sentiment", ar.get(i).getSentiment()), Updates.set("emotion", ar.get(i).getEmotion()));
				collection.findOneAndUpdate(query, updates);
				 
				i++;
			}
			mongo.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	
	public int saveInstagramResults(ArrayList<String> original, ArrayList<ArrayList<String>> moderateText,
			ArrayList<String> intent, ArrayList<String> sentiment, ArrayList<String> emotion,
			ArrayList<String> image_res, ArrayList<String> toxicity, String company) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("ContentModeration");
			MongoCollection<Document> collection = db.getCollection("InstagramResults");

			int i = 0;
			System.out.println(original.size());
			while (i < original.size()) {
				Document doc = new Document();
				doc.append("post", original.get(i).toString());
				doc.append("bad words", moderateText.get(i).toString());
				doc.append("intent", intent.get(i).toString());
				doc.append("sentiment", sentiment.get(i).toString());
				doc.append("emotion", emotion.get(i).toString());
				doc.append("image", image_res.get(i).toString());
				doc.append("review", toxicity.get(i).toString());
				doc.append("company", company);
				collection.insertOne(doc);
				i++;
			}
			mongo.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public int saveFacebookResults(ArrayList<String> original, ArrayList<ArrayList<String>> moderateText,
			ArrayList<String> intent, ArrayList<String> sentiment, ArrayList<String> emotion,
			ArrayList<String> toxicity, ArrayList<ArrayList<String>> type, String company) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("ContentModeration");
			MongoCollection<Document> collection = db.getCollection("FacebookResults");
			int i = 0;
			System.out.println(original.size());
			while (i < original.size()) {
				Document doc = new Document();
				doc.append("post", original.get(i).toString());
				doc.append("bad words", moderateText.get(i).toString());
				doc.append("intent", intent.get(i).toString());
				doc.append("sentiment", sentiment.get(i).toString());
				doc.append("emotion", emotion.get(i).toString());
				doc.append("review", toxicity.get(i).toString());
				doc.append("type", type.get(i).toString());
				doc.append("company", company);
				collection.insertOne(doc);
				i++;
			}
			mongo.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public int saveYoutubeResults(ArrayList<String> sender, ArrayList<String> original,
			ArrayList<ArrayList<String>> moderateText, ArrayList<String> intent, ArrayList<String> sentiment,
			ArrayList<String> emotion, ArrayList<String> toxicity, ArrayList<ArrayList<String>> type, String company) {
		try {
			MongoClient mongo = new MongoClient("localhost", 27017);
			MongoDatabase db = mongo.getDatabase("ContentModeration");
			MongoCollection<Document> collection = db.getCollection("YoutubeResults");
			int i = 0;
			System.out.println(original.size());
			while (i < original.size()) {
				Document doc = new Document();
				doc.append("Name", sender.get(i).toString());
				doc.append("Comment", original.get(i).toString());
				doc.append("bad words", moderateText.get(i).toString());
				doc.append("intent", intent.get(i).toString());
				doc.append("sentiment", sentiment.get(i).toString());
				doc.append("emotion", emotion.get(i).toString());
				doc.append("review", toxicity.get(i).toString());
				doc.append("type", type.get(i).toString());
				doc.append("company", company);
				collection.insertOne(doc);
				i++;
			}
			mongo.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public JSONArray readImagesfromJSON() {
		JSONArray response = null;
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("C:\\json/dummy-images-data.json"));
			org.json.simple.JSONArray ar = (org.json.simple.JSONArray) obj;
			String s = ar.toString();
			response = new JSONArray(s);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public int saveImageToDB(String result, String url) {

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Images");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		int i = 0;

		Document doc = new Document();
		doc.append("url", url);
		doc.append("category", result);
		if (!(result.toString().equalsIgnoreCase("Safe"))) {
			doc.append("review", "yes");
		} else {
			doc.append("review", "no");
		}
		doc.append("date", sdf.format(d));
		collection.insertOne(doc);
		i++;

		mongo.close();
		return 1;

	}
	public int saveAPICalls(int countParallel, int countModerateContent) {

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Resources");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Document doc = new Document();
		doc.append("service", "ParallelDots");
		doc.append("count", countParallel);
		doc.append("date", sdf.format(d));
		collection.insertOne(doc);
		
		
		Document doc2 = new Document();
		doc2.append("service", "ModerateContent");
		doc2.append("count", countModerateContent);
		doc2.append("date", sdf.format(d));
		collection.insertOne(doc2);

		mongo.close();
		return 1;

	}

	public int saveDB(ArrayList<String> name, ArrayList<String> content, ArrayList<Double> toxicity) {

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Tweets");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		int i = 0;
		while (i < name.size()) {
			Document doc = new Document();
			doc.append("name", name.get(i).toString());
			doc.append("content", content.get(i).toString());
			doc.append("toxicity", toxicity.get(i).toString());
			doc.append("date", sdf.format(d));
			collection.insertOne(doc);
			i++;
		}
		mongo.close();
		return 1;

	}
	
	public int saveDB1(ArrayList<TwitterDao> ar) {

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Tweets");
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		int i = 0;
		while (i < ar.size()) {
			
			Document doc = new Document();
			doc.append("Name", ar.get(i).getName());
			doc.append("Tweet", ar.get(i).getTweet());
			doc.append("Id", ar.get(i).getId());
			doc.append("Date", sdf.format(d));
			collection.insertOne(doc);
			
			i++;
		}
		mongo.close();
		return 1;

	}

	public JSONArray getFbPostsfromDB() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("FacebookResults");

		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			JSONObject data = new JSONObject();
			data.put("Content", document.getString("post"));
			data.put("Words", document.getString("bad words"));
			data.put("Intent", document.getString("intent"));
			data.put("Emotion", document.getString("emotion"));
			data.put("Sentiment", document.getString("sentiment"));
			data.put("Review", document.getString("review"));
			array.put(data);
		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray getYoutubeCommentsfromDB() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("YoutubeResults");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			JSONObject data = new JSONObject();
			// data.put("Content", document.getString("content"));
			// data.put("Toxicity", document.getString("toxicity"));
			data.put("Intent", document.getString("intent"));
			data.put("Emotion", document.getString("emotion"));
			data.put("Sentiment", document.getString("sentiment"));
			data.put("Review", document.getString("review"));
			// data.put("Date", document.getString("date"));
			array.put(data);
		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray getInstaPostsfromDB() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("InstagramResults");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
		System.out.println("doc="+documents);
		for (Document document : documents) {

			JSONObject data = new JSONObject();
			// data.put("Content", document.getString("content"));
			// data.put("Toxicity", document.getString("toxicity"));
			data.put("Intent", document.getString("intent"));
			data.put("Emotion", document.getString("emotion"));
			data.put("Sentiment", document.getString("sentiment"));
			data.put("Review", document.getString("review"));
			// data.put("Date", document.getString("date"));
			array.put(data);
		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray getTweetsfromDB() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("TwitterResults");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			JSONObject data = new JSONObject();
			data.put("Tweet", document.getString("tweet"));
			data.put("Words", document.getString("bad words"));
			data.put("Intent", document.getString("intent"));
			data.put("Emotion", document.getString("emotion"));
			data.put("Sentiment", document.getString("sentiment"));
			data.put("Image", document.getString("image"));
			data.put("Review", document.getString("review"));

			array.put(data);
		}

		mongo.close();
		System.out.println(array);
		return array;

	}
	
	public ArrayList<TwitterDao> getTweetsfromDB1() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Tweets");
		ArrayList<TwitterDao> ar = new ArrayList<>();
		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			TwitterDao td = new TwitterDao();
			td.setTweet(document.getString("Tweet"));
			td.setId(document.getString("Id"));
			

			ar.add(td);
		}

		mongo.close();
		return ar;

	}

	public JSONArray getTweetsBasedOnCompany(String company) {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("TwitterResults");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			if (document.getString("company").equalsIgnoreCase(company)) {
				JSONObject data = new JSONObject();
				data.put("Tweet", document.getString("tweet"));
				data.put("Words", document.getString("bad words"));
				data.put("Intent", document.getString("intent"));
				data.put("Emotion", document.getString("emotion"));
				data.put("Sentiment", document.getString("sentiment"));
				data.put("Image", document.getString("image"));
				data.put("Review", document.getString("review"));
				array.put(data);
			}

		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray getFacebookPostsBasedOnCompany(String company) {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("FacebookResults");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			if (document.getString("company").equalsIgnoreCase(company)) {
				JSONObject data = new JSONObject();
				data.put("Post", document.getString("post"));
				data.put("Words", document.getString("bad words"));
				data.put("Intent", document.getString("intent"));
				data.put("Emotion", document.getString("emotion"));
				data.put("Sentiment", document.getString("sentiment"));
				data.put("Review", document.getString("review"));
				data.put("Type", document.getString("type"));
				array.put(data);
			}

		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray getInstagramPostsBasedOnCompany(String company) {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("InstagramResults");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			if (document.getString("company").equalsIgnoreCase(company)) {
				JSONObject data = new JSONObject();
				data.put("Post", document.getString("post"));
				data.put("Words", document.getString("bad words"));
				data.put("Intent", document.getString("intent"));
				data.put("Emotion", document.getString("emotion"));
				data.put("Sentiment", document.getString("sentiment"));
				data.put("Review", document.getString("review"));
				data.put("Image", document.getString("image"));
				array.put(data);
			}

		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray getImagefromDB() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Images");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			JSONObject data = new JSONObject();
			data.put("url", document.getString("url"));
			data.put("review", document.getString("review"));
			data.put("category", document.getString("category"));
			data.put("date", document.getString("date"));

			array.put(data);
		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	
	public JSONArray checkResourceUsed(String service) {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Resources");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {
			
			if(document.getString("service").equalsIgnoreCase(service))
			{
			JSONObject data = new JSONObject();
			data.put("service", document.getString("service"));
			data.put("count", document.getInteger("count"));
			data.put("Date", document.getString("date"));
			array.put(data);
			}
		}

		mongo.close();
		return array;

	}
	
	public JSONArray checkModerateContentUsed() {

		JSONArray array = new JSONArray();

		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Resources");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			JSONObject data = new JSONObject();
			data.put("provider", document.getString("provider"));
			data.put("count", document.getString("count"));
			data.put("date", document.getString("date"));
			array.put(data);
		}

		mongo.close();
		System.out.println(array);
		return array;

	}

	public JSONArray fetchDataUsingFilterPosts(String sentiment, String intent, String platform, String company)
			throws ParseException, FileNotFoundException, IOException, org.json.simple.parser.ParseException {

		System.out.println("Sentiment"+sentiment);
		System.out.println("intent"+intent);
		
		JSONArray array = new JSONArray();
		int casing = 0;
		if (!sentiment.equalsIgnoreCase("none") && !intent.equalsIgnoreCase("none")) {
			casing = 1;
		} else if (sentiment.equalsIgnoreCase("none") && intent.equalsIgnoreCase("none")) {
			casing = 2;
		} else if ((!intent.equalsIgnoreCase("none")) && sentiment.equalsIgnoreCase("none")) {
			casing = 3;
		} else {
			casing = 4;
		}
		System.out.println("casing"+casing);
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection(platform);

		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		if (casing == 1) {
			// case-1 all 3 given
			for (Document document : documents) {
				JSONObject obj = new JSONObject();
				if (sentiment.toString().equalsIgnoreCase(document.getString("sentiment"))
						&& intent.toString().equalsIgnoreCase(document.getString("intent"))) {
					System.out.println("matching cases");
					if (company.equalsIgnoreCase(document.getString("company"))) {
						obj.put("Intent", document.getString("intent"));
						obj.put("Emotion", document.getString("emotion"));
						obj.put("Sentiment", document.getString("sentiment"));
						obj.put("Review", document.getString("review"));
						obj.put("Date", document.getString("Date"));
						array.put(obj);
					}
				}

			}
		} else if (casing == 2) {
			// case- 2 only company given
			for (Document document : documents) {
				JSONObject obj = new JSONObject();
				if (company.toString().equalsIgnoreCase(document.getString("company"))) {
					System.out.println("matching cases");
					obj.put("Intent", document.getString("intent"));
					obj.put("Emotion", document.getString("emotion"));
					obj.put("Sentiment", document.getString("sentiment"));
					obj.put("Review", document.getString("review"));
					obj.put("Date", document.getString("Date"));
					array.put(obj);
				}
			}

		} else if (casing == 3) {
			// case- 3 company and intent given

			for (Document document : documents) {
				JSONObject obj = new JSONObject();
				if (company.toString().equalsIgnoreCase(document.getString("company"))
						&& intent.toString().equalsIgnoreCase(document.getString("intent"))) {
					System.out.println("matching cases");
					obj.put("Intent", document.getString("intent"));
					obj.put("Emotion", document.getString("emotion"));
					obj.put("Sentiment", document.getString("sentiment"));
					obj.put("Review", document.getString("review"));
					obj.put("Date", document.getString("Date"));
					array.put(obj);
				}

			}
		} else // only company and sentiment given
		{
			for (Document document : documents) {
				JSONObject obj = new JSONObject();
				if (company.toString().equalsIgnoreCase(document.getString("company"))
						&& sentiment.toString().equalsIgnoreCase(document.getString("sentiment"))) {
					System.out.println("matching cases");
					obj.put("Intent", document.getString("intent"));
					obj.put("Emotion", document.getString("emotion"));
					obj.put("Sentiment", document.getString("sentiment"));
					obj.put("Review", document.getString("review"));
					obj.put("Date", document.getString("Date"));
					array.put(obj);
				}

			}
		}
		if(array.length()==0)
		{
			JSONObject obj =new JSONObject();
			obj.put("responses", "empty");
			
			array.put(obj);
		}

		mongo.close();
		return array;
	}

	public JSONArray fetchDataUsingFilterPosts1(String sentiment, String intent, String platform)
			throws ParseException, FileNotFoundException, IOException, org.json.simple.parser.ParseException {

		
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection(platform);

		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		
			JSONArray array = new JSONArray();
			for (Document document : documents) {
				JSONObject obj = new JSONObject();
				if (sentiment.toString().equalsIgnoreCase(document.getString("sentiment"))
						|| intent.toString().equalsIgnoreCase(document.getString("intent"))) {
					System.out.println("matching cases");
					
						obj.put("Intent", document.getString("intent"));
						obj.put("Emotion", document.getString("emotion"));
						obj.put("Sentiment", document.getString("sentiment"));
						obj.put("Review", document.getString("review"));
						array .put(obj);
					
				}

			}
			if(array.length()==0)
			{
				JSONObject obj =new JSONObject();
				obj.put("responses", "empty");
				
				array.put(obj);
			}
	
		mongo.close();
		return array;
	}
	public JSONArray getRangeTwitterImage(String start, String end) throws ParseException {
		SimpleDateFormat s = new SimpleDateFormat("yyyy/MM/dd");
		Date starting = s.parse(start);
		System.out.println(starting);
		Date ending = s.parse(end);
		System.out.println(ending);
		JSONArray array = new JSONArray();
		int range = 0;
		MongoClient mongo = new MongoClient("localhost", 27017);
		MongoDatabase db = mongo.getDatabase("ContentModeration");
		MongoCollection<Document> collection = db.getCollection("Images");

		FindIterable<Document> iterDoc = collection.find();
		List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());

		for (Document document : documents) {

			String date = document.getString("date");
			Date post_date = s.parse(date);
			if (post_date.getTime() >= starting.getTime() && post_date.getTime() <= ending.getTime()) {
				range++;
				JSONObject data = new JSONObject();
				data.put("url", document.getString("url"));
				data.put("review", document.getString("review"));
				data.put("category", document.getString("category"));
				data.put("date", document.getString("date"));
				array.put(data);
			}

		}
		mongo.close();
		return array;
	}

	public JSONArray getRange(String start, String end, JSONArray ar) throws ParseException {
		try {
		JSONArray array = new JSONArray();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = s.parse(start);
		System.out.println("starttt="+startDate);
		Date endDate = s.parse(end);
		System.out.println(endDate);
		
		int i=0 ;
		while(i<ar.length())
		{
			JSONObject ob = ar.getJSONObject(i);
			String postDate = ob.getString("Date");
			Date post_date = s.parse(postDate);
			if(post_date.getTime() >= startDate.getTime() && post_date.getTime() <= endDate.getTime())
			{
				array.put(ob);
			}
			i++;
		}
		if(array.length()==0)
		{
			JSONObject obj =new JSONObject();
			obj.put("responses", "empty");
			
			array.put(obj);
		}
		
		return array;
		}
		catch(Exception e)
		{
			JSONArray array = new JSONArray();
			return array;
		}
		}

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Database d = new Database();
		JSONArray a = new JSONArray();

	}

}

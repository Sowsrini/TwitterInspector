package com.example.twitterInspector;

import java.util.ArrayList;
import java.util.List;

import dao.TwitterDao;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClass {

	public static Twitter getTwitterinstance() {

		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		//cb.setTweetModeExtended(true);
		cb.setDebugEnabled(true).setOAuthConsumerKey("vtrMr8ICSpc37M5Ghk8brecVk")
				.setOAuthConsumerSecret("F0hhDnegDLEKSF2Z4fEsqPZSCezfXxxM4CbDDPD46zNXzt1O0A")
				.setOAuthAccessToken("1272797533035610112-SNYe5XAH4AVGNR1vClhY50pLBS3axp")
				.setOAuthAccessTokenSecret("WE8hxjkfRJoDi3weAc0cBRkW5YCVoRFgCk875yF4KIkqo");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;

	}

	public static String createTweet(String tweet) throws TwitterException {
		Twitter twitter = getTwitterinstance();
		Status status = twitter.updateStatus(tweet);
		return status.getText();
	}

	public static ArrayList<String> getTimeLine(){
		ArrayList<String> ar = new ArrayList(15);
		try {
		Twitter twitter = getTwitterinstance();
		Paging p = new Paging(1,5);
		List<Status> statuses = twitter.getUserTimeline(p);
		System.out.println(statuses);
		int i = 0;
		while (i < statuses.size()) {
			ar.add(statuses.get(i).getUser().getName());
			ar.add(statuses.get(i).getText());
			if (statuses.get(i).getMediaEntities().length != 0) {
				MediaEntity[] media = statuses.get(i).getMediaEntities();
				ar.add(media[0].getMediaURL());
			} else {
				ar.add("0");
			}
			i++;
		}
		//System.out.println(ar);
		return ar;
		}
		catch(Exception e)
		{
			ar.add("error");
			return ar;
		}
		}
	
	public static ArrayList<String> getTimeLine(int page){
		ArrayList<String> ar = new ArrayList(15);
		try {
		Twitter twitter = getTwitterinstance();
		Paging p = new Paging(page,5);
		List<Status> statuses = twitter.getUserTimeline(p);
		System.out.println(statuses);
		int i = 0;
		while (i < statuses.size()) {
			ar.add(statuses.get(i).getUser().getName());
			ar.add(statuses.get(i).getText());
			if (statuses.get(i).getMediaEntities().length != 0) {
				MediaEntity[] media = statuses.get(i).getMediaEntities();
				ar.add(media[0].getMediaURL());
			} else {
				ar.add("0");
			}
			i++;
		}
		//System.out.println(ar);
		return ar;
		}
		catch(Exception e)
		{
			ar.add("error");
			return ar;
		}
		}

	public static void getTimeLinepic() throws TwitterException {
		Twitter twitter = getTwitterinstance();
		System.out.println("hello1");
		List<Status> status = twitter.getHomeTimeline();
		// MediaEntity[] m = status.get(8).getMediaEntities();
		// System.out.println(m[0].getMediaURL());
		// System.out.println("hello2");
		// MediaEntity[] media = statuses(0).getMediaEntities(); //get the media
		// entities from the status

	}

	public static ArrayList<TwitterDao> searchtweets(String query1){
		
		ArrayList<TwitterDao> ar = new ArrayList(300);
		try {
		Twitter twitter = getTwitterinstance();
		Query query = new Query(query1);
		query.setCount(100);
		QueryResult result = twitter.search(query);
		List<Status> statuses = result.getTweets();

		
		int i = 0;
		while (i < 50) {
			TwitterDao td = new TwitterDao();
			td.setId(String.valueOf(statuses.get(i).getId()));
			td.setName(statuses.get(i).getUser().getName());
			td.setTweet(statuses.get(i).getText());
			i++;
			ar.add(td);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return ar;
		
	}

	public static String deleteTweet(long tweet) {
		Twitter twitter = getTwitterinstance();
		Status status;
		try {
			status = twitter.destroyStatus(tweet);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			return "Cannot delete Tweet. Please enter a valid Tweet ID";
		}
		return "done";
	}

	public static void main(String args[]) throws Exception {
		TwitterClass f = new TwitterClass();
		Database db = new Database();
		//PerspectiveAPI pp = new PerspectiveAPI();
		//db.saveDB1(f.searchtweets("#ukraine"));
		db.getTweetsfromDB1();
		
	}
}

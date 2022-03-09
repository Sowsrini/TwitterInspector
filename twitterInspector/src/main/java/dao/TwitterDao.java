package dao;

public class TwitterDao {

	String tweet;
	String Id;
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRetweetStatus() {
		return retweetStatus;
	}
	public void setRetweetStatus(int retweetStatus) {
		this.retweetStatus = retweetStatus;
	}
	public String getEmotion() {
		return emotion;
	}
	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}
	public String getIntent() {
		return intent;
	}
	public void setIntent(String intent) {
		this.intent = intent;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	String name;
	int retweetStatus;
	String emotion;
	String intent;
	String sentiment;
	
}

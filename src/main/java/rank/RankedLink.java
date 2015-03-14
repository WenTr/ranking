package rank;

import java.util.HashMap;

public class RankedLink {
	private double googleRank;
	private HashMap<String, Float> wordValues;
	private double ourRank;
	
	public RankedLink(double rank, HashMap<String,Float> wordValues, double ourRank){
		this.googleRank = rank;
		this.wordValues = wordValues;
		this.ourRank = ourRank;
	}
	
	public double getGoogleRank() {
		return googleRank;
	}
	public void setGoogleRank(double googleRank) {
		this.googleRank = googleRank;
	}
	public HashMap<String, Float> getWordValues() {
		return wordValues;
	}
	public void setWordValues(HashMap<String, Float> wordValues) {
		this.wordValues = wordValues;
	}
	public double getOurRank() {
		return ourRank;
	}
	public void setOurRank(double ourRank) {
		this.ourRank = ourRank;
	}
	
}

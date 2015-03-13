package rank;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.xml.sax.ContentHandler;

public class CrawledLink{
	
	private String linkURL;
	private String localPath;
	private String lastPullDate;
	private String mimeType;
	private Map<String, String> metadata;
	private Set<Link> listOfLinks;
	private Map<String, Integer> wordMap; //set of (words, wordCount)
	private Set<String> wordSet; //set of words per doc
	
	public CrawledLink(){
		metadata = new HashMap<String,String>();
		listOfLinks = new HashSet<Link>();
	}

	public CrawledLink(String url, String local, String date, String mimeType, Set<Link> links){
		this.linkURL = url;
		this.localPath = local;
		this.lastPullDate = date;
		this.mimeType = mimeType;
		this.listOfLinks = links;
	}
	
	public CrawledLink(String url, String local, Map<String,String> metadata, Set<Link> urlList, String date, String mimeType){
		this.linkURL = url;
		this.localPath = local;
		this.metadata = metadata;
		this.listOfLinks = urlList;
		this.lastPullDate = date;
		this.mimeType = mimeType;
	}
	

	public Map<String, String> getMetadata() {
		return metadata;
	}

	public void setMetadata(Map<String, String> metadata) {
		this.metadata = metadata;
	}

	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public String getLinkURL() {
		return linkURL;
	}

	public void setLinkURL(String linkURL) {
		this.linkURL = linkURL;
	}
	
	


	public Set<Link> getListOfLinks() {
		return listOfLinks;
	}

	public void setListOfLinks(Set<Link> listOfLinks) {
		this.listOfLinks = listOfLinks;
	}


	public String getLastPullDate() {
		return lastPullDate;
	}


	public void setLastPullDate(String lastPullDate) {
		this.lastPullDate = lastPullDate;
	}


	public String getMimeType() {
		return mimeType;
	}


	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String toString(){
		return "Link: " + linkURL + "-- Local Path: " + localPath + "-- Date: " + lastPullDate + "  -- mimeType: " + mimeType;
		
	}

	public Map<String, Integer> getWordMap() {
		return wordMap;
	}

	public void setWordMap(Map<String, Integer> wordMap) {
		this.wordMap = wordMap;
	}

	public Set<String> getWordSet() {
		return wordSet;
	}

	public void setWordSet(Set<String> wordSet) {
		this.wordSet = wordSet;
	}

}

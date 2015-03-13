package rank;


public class Link {
	
	private String url;
	private String text;
	
	public Link(){}
	
	public Link(String url, String text){
		this.setUrl(url);
		this.setText(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}

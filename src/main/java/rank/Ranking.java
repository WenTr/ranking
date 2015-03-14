package rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Ranking {
	public void pageRanking(Set<CrawledLink> allLinks) {
		List<CrawledLink> linkers = new ArrayList<CrawledLink>(allLinks);
		HashMap<String, Float> ranks = new HashMap<String, Float>();
		
		System.out.println(linkers.size());
		
		//filters out links that are not html
		//puts links with starting rank into map (link, rank)
		for (int i = 0; i < linkers.size(); i++) {
			if (!linkers.get(i).getMimeType().contains("text/html")) {
				linkers.remove(i);
				i--;
			}
		}
		
		//starting rank
		float startRank = (float) (1.0 / linkers.size());
		System.out.println(startRank);
		
		for (int i = 0; i < linkers.size(); i++) {
				ranks.put(linkers.get(i).getLinkURL(), startRank);
		}

		System.out.println("ranks: " + ranks.size());
		System.out.println("starting");
		
		for (int u = 0; u < 10; u++) {
		HashMap<String, Float> oldranks = new HashMap<String, Float>();
		oldranks.putAll(ranks);
		List<String> aa = new ArrayList<String>();
		
			System.out.println("u: " + u);
			for (CrawledLink eachLink : linkers) {
				for(CrawledLink everyLink : linkers){
					for(Link perLink : everyLink.getListOfLinks()){
						String a  = eachLink.getLinkURL().toString().replaceAll("[^a-zA-Z0-9]", "");
						String b   = perLink.getUrl().replaceAll("[^a-zA-Z0-9]", "");
						if(a.equals(b)){
							aa.add(perLink.getUrl());
						}
					}
				}
				float h = (float) 0;
				for (int w = 0; w < aa.size(); w++) {
					//System.out.println(w);
					if(oldranks.containsKey(aa.get(w))){
						//System.out.println(eachLink.getListOfLinks().size());
						//float gg = oldranks.get(aa.get(w));
						h += oldranks.get(aa.get(w)) / linkers.get(linkers.indexOf(eachLink)).getListOfLinks().size();
					}
				}
				ranks.put(eachLink.getLinkURL(), h); 
			}
		}
		
		System.out.println(ranks.keySet());
		System.out.println(ranks.values());
		System.out.println(ranks.size());

	}

	public void wordCalculation() {

	}

	public void addedRanking() {

	}
}

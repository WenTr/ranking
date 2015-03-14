package rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Ranking {
	public void pageRanking(Set<CrawledLink> allLinks) {
		List<CrawledLink> linkers = new ArrayList<CrawledLink>(allLinks);
		HashMap<String, Float> ranks = new HashMap<String, Float>();

		float startRank = (float) (1.0 / linkers.size());
		System.out.println(startRank);
		System.out.println(linkers.size());
		for (int i = 0; i < linkers.size(); i++) {
			if(!linkers.get(i).getMimeType().contains("text/html"))
				ranks.put(linkers.get(i).getLinkURL(), startRank);
		}
		
		for (int i = 0; i < linkers.size(); i++) {
			if(linkers.get(i).getMimeType().contains("text/html"))
				ranks.put(linkers.get(i).getLinkURL(), startRank);
		}
		System.out.println("ranks: " + ranks.size());
		for (int u = 0; u <= 10; u++) {
			System.out.println("u: " + u);
			HashMap<String, Float> oldranks = new HashMap<String, Float>();
			oldranks.putAll(ranks);

			for (int i = 0; i < linkers.size(); i++) {
				List<String> aa = new ArrayList<String>();

				if (linkers.get(i).getMimeType().equals("text/html")) {
					for (Link l : linkers.get(i).getListOfLinks()) {
						aa.add(l.getUrl());
					}
					
					float h = (float) 0;
					for (int w = 0; w < aa.size(); w++) {
						for (int g = 0; g < linkers.size(); g++) {
							if(linkers.get(g).getMimeType().contains("text/html")){
								System.out.println("g: " + g + " " + linkers.get(g).getLinkURL());
							}
							if (linkers.get(g).getLinkURL().equals(aa.get(w))) {
								System.out.println("true");
								if(linkers.get(g).getMimeType().contains("text/html")){
									System.out.println(aa.get(w) + " " + h);
									h += oldranks.get(aa.get(w)) / linkers.get(g).getListOfLinks().size();
									//System.out.println(h);
								}
							}
						}
					}
					ranks.put(linkers.get(i).getLinkURL(), h);
				}

				
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

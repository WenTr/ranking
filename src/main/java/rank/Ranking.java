package rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Ranking {

	public List<CrawledLink> keepURLS(List<CrawledLink> list) {
		for (int i = 0; i < list.size(); i++) {
			if (!list.get(i).getMimeType().contains("text/html")) {
				list.remove(i);
				i--;
			}
		}

		return list;
	}

	public void pageRanking(Set<CrawledLink> allLinks) {
		List<CrawledLink> linkers = new ArrayList<CrawledLink>(allLinks);
		HashMap<String, Float> ranks = new HashMap<String, Float>();

		System.out.println(linkers.size());

		// filters out links that are not html
		// puts links with starting rank into map (link, rank)

		linkers = keepURLS(linkers);

		float[] rankArray = new float[linkers.size()];
		float[] temprankArray = new float[linkers.size()];

		for (int i = 0; i < linkers.size(); i++) {
			rankArray[i] = (float) (1.0 / linkers.size());
		}

		System.out.println("ranks: " + ranks.size());

		System.out.println("starting");

		for (int u = 0; u < 10; u++) {
			System.out.println("u: " + u);
			for (int i = 0; i < linkers.size(); i++) {
				CrawledLink eachLink = linkers.get(i);
				List<String> links = new ArrayList<String>();
				for (CrawledLink everyLink : linkers) {
					for (Link perLink : everyLink.getListOfLinks()) {
						String linkA = eachLink.getLinkURL().toString().replaceAll("[^a-zA-Z0-9]", "");
						String linkB = perLink.getUrl().replaceAll("[^a-zA-Z0-9]", "");
						if (linkA.equals(linkB)) {
							links.add(perLink.getUrl());
						}
					}
				}
				
//				System.out.println("LINKS SIZE" + links.size());
				
				float h = (float) 0;
				for (int w = 0; w < linkers.size(); w++) {
						h += rankArray[w]/ links.size();
					}
				temprankArray[i] = h;
				}
						
			rankArray = temprankArray;
			}
		
		for(int i = 0; i < rankArray.length; i++){
			System.out.println(rankArray[i] + " URL: " + linkers.get(i).getLinkURL());
		}

		System.out.println(ranks.keySet());
		System.out.println(ranks.values());
		System.out.println(ranks.size());

	}
}

package rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Ranking {

	public HashMap<String, Float> pageRanking(Set<CrawledLink> allLinks) {

		ArrayList<CrawledLink> linkers = new ArrayList<CrawledLink>(allLinks);
		
		for (int i = 0; i< linkers.size(); i++) {
						String a = linkers.get(i).getLinkURL().toString();
						if (a.lastIndexOf("/") == a.length() - 1
								|| a.lastIndexOf("#") == a.length() - 1) {
							a = StringUtils.stripEnd(a, "/");
							a = StringUtils.stripEnd(a, "#");
							linkers.get(i).setLinkURL(a);
						}
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<CrawledLink> allLinkTypes = (ArrayList<CrawledLink>) linkers
				.clone();
		HashMap<String, Float> ranks = new HashMap<String, Float>();
		Set<String> linkStrings = new HashSet<String>();

		System.out.println("LINKERS" + linkers.size());


		// filters out links that are not html
		// puts links with starting rank into map (link, rank)

		for (int i = 0; i < linkers.size(); i++) {
			CrawledLink link = linkers.get(i);

			if (link.getMetadata() != null
					&& !link.getMetadata().get("Content-Type").contains("html")) {
				linkers.remove(i);
				i--;
			}
			Set<Link> modLinks = new HashSet<Link>();
			for(Link l: link.getListOfLinks()){
				String b = l.getUrl();
				if (b.lastIndexOf("/") == b.length() - 1
						|| b.lastIndexOf("#") == b.length() - 1) {
					b = StringUtils.stripEnd(b, "/");
					b = StringUtils.stripEnd(b, "#");
					modLinks.add(new Link(b, l.getText()));
				}
			}
			
			System.out.println("I: " + i);
			System.out.println("MOD LINKS:" + modLinks.size());
			linkers.get(i).setListOfLinks(modLinks);
		}

		for (int i = 0; i < allLinkTypes.size(); i++) {
			linkStrings.add(allLinkTypes.get(i).getLinkURL().toString());
		}
		
		
		
		

		System.out.println("LINKERS" + linkers.size());
		// starting rank
		float startRank = (1.0F / (float) linkStrings.size());
		System.out.println("START RANK" + startRank);

		for (String url: linkStrings) {
			ranks.put(url, startRank);
		}

		System.out.println("set of links: " + linkStrings.size());
		System.out.println("ranks: " + ranks.size());

		System.out.println("starting");
		
		Map<String, Integer> linksToMap = new HashMap<String,Integer>();
		
		
		for(CrawledLink eachLink : allLinkTypes){
			linksToMap.put(eachLink.getLinkURL(), 0);
		}

		for (int u = 0; u < 3; u++) {
			System.out.println("ITERATION");
			HashMap<String, Float> oldranks = new HashMap<String, Float>();
			oldranks.putAll(ranks);
			System.out.print("BEFORE PROCESS" + ranks.size());
			List<String> aa = new ArrayList<String>();

			// System.out.println("u: " + u);
			for (CrawledLink eachLink : allLinkTypes) {
				for (CrawledLink everyLink : linkers) {
					if (eachLink != everyLink) {
						for (Link perLink : everyLink.getListOfLinks()) {

							String a = eachLink.getLinkURL().toString();

							String b = perLink.getUrl().toString();

							String c = everyLink.getLinkURL().toString();

							if (a.equals(b) && !aa.contains(c)) {
								// System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7: "
								// + c);
								aa.add(c);
							}
						}
					}
				}

				// System.out.println("LINKS SIZE" + links.size());

				float h = (float) 0;

				for (int w = 0; w < aa.size(); w++) {

					if (oldranks.containsKey(aa.get(w))) {
						// System.out.println(aa.get(w) + " : " + w);
						//
						int uy = 0;
						for (int p = 0; p < linkers.size(); p++) {
							if (linkers.get(p).getLinkURL().equals(aa.get(w))) {
								uy = p;
								break;
							}
						}

						// System.out.println(aa.get(w) + "::" +
						// linkers.get(uy).getLinkURL());
						h += oldranks.get(aa.get(w))
								/ linkers.get(uy).getListOfLinks().size();
					}
				}
				ranks.put(eachLink.getLinkURL(), h);
			}
		}

		 for (Entry<String, Float> entry : ranks.entrySet()) {
		 System.out.println(entry.getKey() + " : " + entry.getValue());
		 }

		System.out.println("BRYAN RANKING: " + ranks.size());

		return ranks;

	}

	// rank by the number of words a document has
	public Map<String, Float> addedRanking(Set<CrawledLink> allLinks) {

		Map<String,Float> tempMap = new HashMap<String, Float>();
		Map<String, Float> urlNnumWords = new HashMap<String, Float>();
		
		int count = 0; //# of links with no wordset
		// Queue<Map.Entry<String, Integer>> arrangedWL = new
		// LinkedList<Map.Entry<String, Integer>>();

		for (CrawledLink link : allLinks) {
			if (link.getWordSet() != null) {
				tempMap.put(link.getLinkURL(), (float) link.getWordSet().size());
				System.out.println("NUMBER OF WORDS: " + link.getWordSet().size());
			}
			else{
				count++;
			}
		}
		
		System.out.println("COUNT:" + count);

		// for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
		// System.out.println(entry.getKey() + ": " + entry.getValue());
		// }
		// System.out.println(Collections.max(tempMap.values()));
		
		Float maxWords = Collections.max(tempMap
				.values());
		System.out.println("MAX WORDS: " + maxWords);

		for (Map.Entry<String, Float> entry : tempMap.entrySet()) {
			float number = (float)entry.getValue() / (float) maxWords;
			
			urlNnumWords.put(entry.getKey(), number);
			
			System.out.println("RANK" + number);
		}

		return urlNnumWords;
	}

}

package rank;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class PageRanker {

	public Map<String,Float> rankPages(Set<CrawledLink> allLinks) {

		ArrayList<CrawledLink> linkers = new ArrayList<CrawledLink>(allLinks);
		System.out.println("PAGE RANKS");

		for (int i = 0; i < linkers.size(); i++) {
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
		HashMap<String, Float> ranksMap = new HashMap<String, Float>();
		
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
			for (Link l : link.getListOfLinks()) {
				String b = l.getUrl();
				if (b.lastIndexOf("/") == b.length() - 1
						|| b.lastIndexOf("#") == b.length() - 1) {
					b = StringUtils.stripEnd(b, "/");
					b = StringUtils.stripEnd(b, "#");
					modLinks.add(new Link(b, l.getText()));
				}
			}

//			System.out.println("I: " + i);
//			System.out.println("MOD LINKS:" + modLinks.size());
			linkers.get(i).setListOfLinks(modLinks);
		}

		for (int i = 0; i < allLinkTypes.size(); i++) {
			if(!linkStrings.contains(allLinkTypes.get(i).getLinkURL().toString())){
				linkStrings.add(allLinkTypes.get(i).getLinkURL().toString());
			}
			else{
				allLinkTypes.remove(i);
			}
			
		}
		
		System.out.println("LINKS SIZE: " + allLinkTypes.size() + "  LINK STRING SIZE: " + linkStrings.size());
		
//		Float [] ranks = new Float[allLinkTypes.size()];

		float startRank = (1.0F / (float) allLinkTypes.size());
		System.out.println("START RANK" + startRank);

		for (int i = 0; i < allLinkTypes.size(); i++) {
			ranksMap.put(allLinkTypes.get(i).getLinkURL(), startRank);
		}
		
		

		HashMap<String, Float> tempranksMap = new HashMap<String, Float>();

		for (int iterations = 0; iterations < 3; iterations++) {
			System.out.println("ITERATION: " + iterations);
			
			tempranksMap = new HashMap<String, Float>();
			
			
			for (int i = 0; i < allLinkTypes.size(); i++) {
				Float rank = 0.0F;
				String currentLink = allLinkTypes.get(i).getLinkURL();
				
				for (int j = 0; j < linkers.size(); j++) {
					if (!currentLink.equals(linkers.get(j).getLinkURL())){
						Set<String> links = new HashSet<String>();
						for (Link l : linkers.get(j).getListOfLinks()) {
							links.add(l.getUrl());
						}

						if (links.contains(currentLink)) {
							float jRank = ranksMap.get(linkers.get(j).getLinkURL());
							rank += jRank/ (float)links.size();
						}
					}
				}
				tempranksMap.put(currentLink, rank);
			}
			
			for (Entry<String, Float> entry : tempranksMap.entrySet()) {
				ranksMap.put(entry.getKey(), entry.getValue());
			}
			
		}
		

		
		return ranksMap;

	}

}

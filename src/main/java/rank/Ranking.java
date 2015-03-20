package rank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Ranking {
	public HashMap<String,Float> pageRanking(Set<CrawledLink> allLinks) {
		List<CrawledLink> linkers = new ArrayList<CrawledLink>(allLinks);
		HashMap<String, Float> ranks = new HashMap<String, Float>();

		System.out.println(linkers.size());

		// filters out links that are not html
		// puts links with starting rank into map (link, rank)

		for (int i = 0; i < linkers.size(); i++) {
			if (!linkers.get(i).getMimeType().contains("text/html")) {
				linkers.remove(i);
				i--;
			} else {
				String a = linkers.get(i).getLinkURL().toString();
				if (a.lastIndexOf("/") == a.length() - 1
						|| a.lastIndexOf("#") == a.length() - 1) {
					a = StringUtils.stripEnd(a, "/");
					a = StringUtils.stripEnd(a, "#");
				}
				linkers.get(i).setLinkURL(a);
			}
		}

		// starting rank
		float startRank = (float) (1.0 / linkers.size());
		System.out.println(startRank);

		for (int i = 0; i < linkers.size(); i++) {
			ranks.put(linkers.get(i).getLinkURL(), startRank);
		}

		System.out.println("ranks: " + ranks.size());

		System.out.println("starting");


		for (int u = 0; u < 3; u++) {
			HashMap<String, Float> oldranks = new HashMap<String, Float>();
			oldranks.putAll(ranks);
			List<String> aa = new ArrayList<String>();

			//System.out.println("u: " + u);
			for (CrawledLink eachLink : linkers) {
				for (CrawledLink everyLink : linkers) {
					if(eachLink != everyLink){
						for (Link perLink : everyLink.getListOfLinks()) {

							String a = eachLink.getLinkURL().toString();
							if (a.lastIndexOf("/") == a.length() - 1
									|| a.lastIndexOf("#") == a.length() - 1) {
								a = StringUtils.stripEnd(a, "/");
								a = StringUtils.stripEnd(a, "#");
							}

							String b = perLink.getUrl().toString();
							if (b.lastIndexOf("/") == b.length() - 1
									|| b.lastIndexOf("#") == b.length() - 1) {
								b = StringUtils.stripEnd(b, "/");
								b = StringUtils.stripEnd(b, "#");
							}

							String c = everyLink.getLinkURL().toString();
							if (c.lastIndexOf("/") == c.length() - 1
									|| c.lastIndexOf("#") == c.length() - 1) {
								c = StringUtils.stripEnd(c, "/");
								c = StringUtils.stripEnd(c, "#");
							}

							if (a.equals(b) && !aa.contains(c)) {
								//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&7: " + c);
								aa.add(c);
							}
						}
					}
				}

				//				System.out.println("LINKS SIZE" + links.size());

				float h = (float) 0;


				//for(int w = 0; w < linkers.size(); w++){
				//	if()
				//}
				//System.out.println(aa.size());
				for (int w = 0; w < aa.size(); w++) {

					if (oldranks.containsKey(aa.get(w))) {
						//System.out.println(aa.get(w) + " : " + w);
						// 
						int uy = 0;
						for(int p = 0; p < linkers.size(); p++){
							if(linkers.get(p).getLinkURL().equals(aa.get(w))){
								uy = p;
								break;
							}
						}

						//System.out.println(aa.get(w) + "::" + linkers.get(uy).getLinkURL());
						h += oldranks.get(aa.get(w)) / linkers.get(uy).getListOfLinks().size();
					}
				}
				ranks.put(eachLink.getLinkURL(), h);
			}
		}

		for (Entry<String, Float> entry : ranks.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}

		return ranks;

	}

	//rank by the number of words a document has
	public Map<String, Double> addedRanking(Set<CrawledLink> allLinks) {

		Map<String, Integer> tempMap = new HashMap<String, Integer>();
		Map<String, Double> urlNnumWords = new HashMap<String, Double>();
		//Queue<Map.Entry<String, Integer>> arrangedWL = new LinkedList<Map.Entry<String, Integer>>();

		for (CrawledLink link : allLinks) {
			if (link.getWordSet() != null) {
				tempMap.put(link.getLinkURL(), link.getWordSet().size());
			}
		}

		//		for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
		//			System.out.println(entry.getKey() + ": " + entry.getValue());
		//		}
		//		System.out.println(Collections.max(tempMap.values()));

		for (Map.Entry<String, Integer> entry : tempMap.entrySet()) {
			urlNnumWords.put(entry.getKey(), ((double)entry.getValue()/Collections.max(tempMap.values())));
		}

		for (Entry<String, Double> entry : urlNnumWords.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}


		/**
			List<Integer> listVals = new ArrayList<Integer>(numWords.values());
			Collections.sort(listVals);
			Collections.reverse(listVals);

			for (Object i : listVals) {
				for (Map.Entry<String, Integer> entry : numWords.entrySet()) {
					//System.out.println(entry.getKey() + ": " + entry.getValue());
					if (entry.getValue().equals(i)) {
						arrangedWL.add(entry);
					}
				}
			}

			for (Map.Entry<String, Integer> eachEntry : arrangedWL) {
				System.out.println(eachEntry);
			}
		 **/


		//For testing purposes 
		//Storage save = new Storage("index.json", "ranking.json");
		//save.storeRank(urlNnumWords);

		return urlNnumWords;
	}


}

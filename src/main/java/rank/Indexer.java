package rank;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Indexer {
	public Map<String, HashMap<String, Integer>> indexWords(List<CrawledLink> linkList) {

		Set<String> totalWords = new HashSet<String>();
		
		Map<String, HashMap<String, Integer>> totalWordMap = new HashMap<String, HashMap<String, Integer>>();

		for (CrawledLink link : linkList) {
			totalWords.addAll(link.getWordSet());
		}

		for (String word : totalWords) {
			System.out.print(word + " ");
			HashMap<String, Integer> wordURLCount = new HashMap<String, Integer>();
			for (int i = 0; i < linkList.size(); i++) {
				Map<String, Integer> wordMap = linkList.get(i).getWordMap();
				if (wordMap.containsKey(word)) {
					wordURLCount.put(linkList.get(i).getLinkURL(), (Integer) wordMap.get(word));
				}
			}
			System.out.println();
			
			totalWordMap.put(word, wordURLCount);
			
		}
		
		System.out.println("\n\n");
		
		return totalWordMap;
	}
	
	
}
package rank;

/**
 * Source:
 * http://www.tutorialspoint.com/java/util/hashmap_put.htm
 * 
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Indexer {
	public Map<String, HashMap<String, Float>> wordIndexing(Set<String> setSW, List<CrawledLink> allLinks) {
		
		Map<String, HashMap<String, Float>> wordIndex = new HashMap<String, HashMap<String, Float>>();
		TFID tfidCalc = new TFID();
		HashMap<String, Float> urlWordCount = new HashMap<String, Float>();
		Set<String> wordList = new HashSet<String>();
		
		//System.out.println(allLinks.toString());
		
		//put all the words from every link into a set
		for (CrawledLink link : allLinks) {
			//System.out.println(link);
			if (link.getWordSet() != null) {
				wordList.addAll(link.getWordSet());
			}
		}
		
		//removing stop words
		System.out.println("Size Before: " + wordList.size());
		wordList.removeAll(setSW);
		System.out.println("Size After: " + wordList.size());
		
		List<HashMap<String, Float>> tfidf = tfidCalc.tfIdfCalculator(allLinks, wordList);
		
		
		//find words in links
		for (String w : wordList) {
			for (int i = 0; i < allLinks.size(); i++) {
				if (allLinks.get(i).getWordSet() != null && allLinks.get(i).getWordSet().contains(w)) {
					urlWordCount.put(allLinks.get(i).getLinkURL(), tfidf.get(i).get(w));
				}
			}
			wordIndex.put(w, urlWordCount);
			urlWordCount = new HashMap<String, Float>();
		}
		System.out.println(wordIndex.toString());
		
		return wordIndex;
	}
}
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
	public Map<String, HashMap<String, Integer>> wordIndexing(Set<String> setSW, Set<CrawledLink> allLinks) {
		Map<String, HashMap<String, Integer>> wordIndex = new HashMap<String, HashMap<String, Integer>>();
		HashMap<String, Integer> urlWordCount = new HashMap<String, Integer>();
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
		//System.out.println("Size Before: " + wordList.size());
		wordList.removeAll(setSW);
		//System.out.println("Size After: " + wordList.size());
		
		//find words in links
		for (String w : wordList) {
			for (CrawledLink link : allLinks) {
				if (link.getWordSet() != null && link.getWordSet().contains(w)) {
					urlWordCount.put(link.getLinkURL(), link.getWordMap().get(w));
				}
			}
			wordIndex.put(w, urlWordCount);
			urlWordCount = new HashMap<String, Integer>();
		}
		//System.out.println(wordIndex.toString());
		
		return wordIndex;
	}

	public void indexWords(List<CrawledLink> visitedLinks) {
		// TODO Auto-generated method stub
		
	}
}
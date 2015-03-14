package rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TFID {

	// This variable will hold all terms of each document in an array.
	private List<String> allTerms = new ArrayList<String>(); // to hold all
																// terms
	private List<HashMap<String, Float>> tfvectorList = new ArrayList<HashMap<String, Float>>();

	public List<HashMap<String, Float>> tfIdfCalculator(
		List<CrawledLink> linkList, Set<String> allTerms) {
		Float tf; // term frequency
		Float idf; // inverse document frequency
		Float tfidf; // term requency inverse document frequency
		List<String> wordsTotal = new ArrayList<String>();

		for (CrawledLink link : linkList) {
			wordsTotal.addAll(link.getWordSet());
		}

		Map<String, Float> idfMap = new HashMap<String, Float>();

		for (String s : allTerms) {
			idfMap.put(s, idfCalculator(linkList, s));
		}

		for (CrawledLink link : linkList) {
			HashMap<String, Float> tfvectors = new HashMap<String, Float>();
			for (String s : link.getWordSet()) {
				tf = tfCalculator(getWordCount(link), link.getWordMap().get(s));
				tfidf = tf * idfMap.get(s);
				tfvectors.put(s, tfidf);
			}
			tfvectorList.add(tfvectors);
		}

		return tfvectorList;
	}

	public Float tfCalculator(int totalWords, Integer count) {
		return  ((float)count / (float)totalWords);
	}

	public Float idfCalculator(List<CrawledLink> linkList, String termToCheck) {
		double count = 0;
		for (CrawledLink link : linkList) {
			if(link.getWordMap().get(termToCheck) != null){
				count += link.getWordMap().get(termToCheck);
			}
		}
		Float linkSize = (float) linkList.size();
		return (float) (1 + Math.log((linkSize/count)));
	}

	public List<String> getAllTerms() {
		return allTerms;
	}

	public void setAllTerms(List<String> allTerms) {
		this.allTerms = allTerms;
	}

	public int getWordCount(CrawledLink link) {

		int count = 0;
		for (Entry<String, Integer> entry : link.getWordMap().entrySet()) {
			count += entry.getValue();
		}

		return count;

	}

}

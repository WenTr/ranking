package rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TFID {
	
	private List<HashMap<String, Float>> tfvectorList = new ArrayList<HashMap<String, Float>>();

	public List<HashMap<String, Float>> tfIdfCalculator(
		List<CrawledLink> linkList, Set<String> allTerms) {
		Float tfidf; // term requency inverse document frequency
		Map<String, Float> idfMap = new HashMap<String, Float>();

		for (String s : allTerms) {
			idfMap.put(s, idfCalculator(linkList, s));
		}

		for (CrawledLink link : linkList) {
			HashMap<String, Float> tfvectors = new HashMap<String, Float>();
			for (String s : link.getWordSet()) {
				tfidf = link.getWordMap().get(s) * idfMap.get(s);
				tfvectors.put(s, tfidf);
			}
			tfvectorList.add(tfvectors);
		}

		return tfvectorList;
	}

	public Float idfCalculator(List<CrawledLink> linkList, String termToCheck) {
		double count = 0;
		for (CrawledLink link : linkList) {
			if(link.getWordMap().get(termToCheck) != null){
				count ++;
			}
		}
		Float linkSize = (float) linkList.size();
		return (float) (Math.log((linkSize/count)));
	}
}

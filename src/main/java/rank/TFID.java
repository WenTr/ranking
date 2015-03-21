package rank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TFID {


	private List<CrawledLink> linkList;
	private Map<String, Float> idfMap;

	public TFID (List<CrawledLink> linkList){
		this.linkList = linkList;
	}
	

	private List<HashMap<String, Float>> tfvectorList = new ArrayList<HashMap<String, Float>>();

	public List<HashMap<String, Float>> tfIdfCalculator(
		List<CrawledLink> linkList, Set<String> allTerms) {

		Float tfidf; // term requency inverse document frequency
		idfMap = new HashMap<String, Float>();

		for (String s : allTerms) {
//			System.out.println(s);
			
			
			if(!s.equals("") && s != null && s.length() >0 ){
					idfMap.put(s, 0.0F);

			}
		}
		
		
		System.out.println("ALL WORDS COUNT:" + allTerms.size());
		System.out.println("MAP SIZE:" + idfMap.size());
		
		for (String s : allTerms) {
			if(idfMap.containsKey(s)){
				termCount(s);
			}
		}

		for(String s: allTerms){
			if(idfMap.containsKey(s)){
				idfMap.put(s, idfMap.get(s)/linkList.size());
			}
		}

		for (CrawledLink link : linkList) {
			HashMap<String, Float> tfvectors = new HashMap<String, Float>();
			if(link.getWordSet() == null){
				System.out.println(link.getLinkURL());
			}
			else{
				for (String s : link.getWordSet()) {
					if(idfMap.containsKey(s)){
						tfidf = link.getWordMap().get(s) * idfMap.get(s);
						tfvectors.put(s, tfidf);
					}
					
				}
			}

			tfvectorList.add(tfvectors);
		}

		return tfvectorList;
	}


	public void termCount(String termToCheck) {
//		System.out.println(termToCheck);
		if (!idfMap.containsKey(termToCheck)){
			System.out.println("ERROR TERM" + termToCheck);
		}
		double count = idfMap.get(termToCheck);
		for (CrawledLink link:  linkList) {
			if(link.getWordSet() != null){
				if(link.getWordSet().isEmpty()){
					continue;
				}
				else{
					if(link.getWordSet().contains(termToCheck)){
						idfMap.put(termToCheck, idfMap.get(termToCheck) + 1);
					}
				}
			}
	
		}
	}
}

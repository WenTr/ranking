package rank;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PageRanker {
	
	public Double [] rankPages(List<CrawledLink> linkList){
		
		System.out.println("PAGE RANKS");
		
		Double[] pageRanks = new Double[linkList.size()];
		
		
		for(int i = 0; i < linkList.size(); i++ ){
			pageRanks[i] = (1.0/linkList.size());
//			System.out.println(pageRanks[i]);
		}
		
		Double[] tempPageRanks = new Double[linkList.size()];
		
		for (int iterations = 0; iterations < 10; iterations++){
			for(int i = 0; i < linkList.size(); i++ ){
				Double rank = 0.0;
				String currentLink = linkList.get(i).getLinkURL();
				for(int j = 0; j < linkList.size(); j++){
					if (i == j) continue;
					else{
						
						Set<String> links = new HashSet<String>();
						for(Link l: linkList.get(j).getListOfLinks()){
							links.add(l.getUrl());
						}
						
						if (links.contains(currentLink)){
							rank += (pageRanks[j]/links.size());
						}
					}
				}
				
				tempPageRanks[i] = rank;
			}
			
			pageRanks = tempPageRanks;
		}

		
		return pageRanks;


	}
	
	

}

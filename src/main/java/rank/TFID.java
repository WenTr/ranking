package rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TFID {

	//This variable will hold all terms of each document in an array.
    private List<CrawledLink> linkList = new ArrayList<CrawledLink>();
    private List<String> allTerms = new ArrayList<String>(); //to hold all terms
    private List tfidfDocsVector = new ArrayList<Object>();

    /**
     * Method to read files and store in array.
     * @param filePath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency
        
        
        for (CrawledLink link: linkList) {
        	Set<String> words = link.getWordSet();
        	//String[] docTermsArray : termsDocsArray
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = tfCalculator(words, terms);
                idf = idfCalculator(linkList, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
        }
    }
	
	
	//Source: http://computergodzilla.blogspot.com/2013/07/how-to-calculate-tf-idf-of-document.html
    /**
     * Calculates the tf of term termToCheck
     * @param totalterms : Array of all the words under processing document
     * @param termToCheck : term of which tf is to be calculated.
     * @return tf(term frequency) of term termToCheck
     */
    public double tfCalculator(Set<String> words, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : words) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / words.size();
    }

    /**
     * Calculates idf of term termToCheck
     * @param allTerms : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public double idfCalculator(List<CrawledLink> linkList, String termToCheck) {
        double count = 0;
        for (CrawledLink link : linkList) {
            for (String s : link.getWordSet()) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        return 1 + Math.log(linkList.size() / count);
    }
    
    
    public List<String> getAllTerms() {
		return allTerms;
	}


	public void setAllTerms(List<String> allTerms) {
		this.allTerms = allTerms;
	}
	
	
	
	
	

}

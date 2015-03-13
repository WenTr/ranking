package rank;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Source:
 * http://xpo6.com/wp-content/uploads/2015/01/stop-word-list.txt
 * http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
 * http://tutorials.jenkov.com/java-collections/set.html
 *
 */

public class IndexerRankerApp {
	public static void main(String args[]) {
		String metaJsonFile = "C:\\Users\\tranw_000\\Desktop\\cs454SE\\data_extraction\\metadata2.json";
		String stopWordFile = "C:\\Users\\tranw_000\\Desktop\\cs454SE\\rank\\words.txt";
		Set<String> setSW = new HashSet<String>();
		
		Storage store = new Storage();
		Set<CrawledLink> visitedLinks = null;
		
		try {
			BufferedReader bR = new BufferedReader(new FileReader(stopWordFile));
			String line = bR.readLine();

			while(line != null) {
				//System.out.println(line);
				setSW.add(line);
				line = bR.readLine();
			}

			bR.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch  (IOException e) {
			e.printStackTrace();
		}

		//System.out.println(setSW.toString());
		//System.out.println(setSW.size());
		
		visitedLinks = store.readJSON(metaJsonFile);
		//System.out.println(visitedLinks.size());
	}
}

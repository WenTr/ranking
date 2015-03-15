package rank;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Storage {
	File indexJSON = new File("index.json");
	File rankingJSON = new File("ranking.json");
	File directoryFile = new File("files");
	ObjectMapper obMap = new ObjectMapper();

	public Storage(String index, String ranking) {
		this.indexJSON = new File(index);
		this.rankingJSON = new File(ranking);
	}

	public void storeIndex(Map<String, HashMap<String, Integer>> wordIndex) {
		try {
			obMap.writeValue(indexJSON, wordIndex);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void storeRank(Map<String, Integer> numWords) {
		try {
			obMap.writeValue(rankingJSON, numWords);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Set<CrawledLink> readJSON(String jsonLink) {

		Set<CrawledLink> links = null;
		try {
			links = obMap.readValue(new File(jsonLink),
					new TypeReference<Set<CrawledLink>>() {
					});
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return links;
	}

}

package rank;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Storage {
	File jsonFile = new File("index.json");
	File jsonFile2 = new File("ranking.json");
	File directoryFile = new File("files");
	ObjectMapper obMap = new ObjectMapper();
	
//	public Storage(){
//		directoryFile.mkdir();
//	}
	
	public void store1(List<CrawledLink> linkList) {
		try {
			System.out.println("Saving to JSON");
			obMap.writeValue(jsonFile, linkList);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void store2(List<CrawledLink> linkList) {
		try {
			System.out.println("Saving to JSON");
			obMap.writeValue(jsonFile, linkList);
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
			links = obMap.readValue(new File(jsonLink), new TypeReference<Set<CrawledLink>>(){});
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


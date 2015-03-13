package rank;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Storage {
	File indexJSON = new File("index.json");
	File rankingJSON = new File("ranking.json");
	File directoryFile = new File("files");
	ObjectMapper obMap = new ObjectMapper();
	
	public Storage(){
		directoryFile.mkdir();
	}
	
//	public void store(String currentURL) {
//		Extractor extr = new Extractor();
//		
//		// initial extraction
//		try {
//			obMap.writeValue(jsonFile, extr.parseExample(currentURL));
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//
//		}
//	}
	
//	public void store2(Map<String, Map<String,String>> linkMap) {
//		try {
//			obMap.writeValue(jsonFile, linkMap);
//		} catch (JsonGenerationException e) {
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void storeIndex(Map<String, HashMap<String, Integer>> wordIndex) {
		try {
			System.out.println("Saving to JSON");
			obMap.writeValue(indexJSON, wordIndex);
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
	
	
	//http://www.programcreek.com/2012/12/download-image-from-url-in-java/
	
	public static void saveImage(String imageUrl) throws IOException {
		URL url = new URL(imageUrl);
		String fileName = url.getFile();
		String destName = "./figures" + fileName.substring(fileName.lastIndexOf("/"));
		System.out.println(destName);
	 
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destName);
	 
		byte[] b = new byte[2048];
		int length;
	 
		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}
	 
		is.close();
		os.close();
	}
	
	
//	public String downloadFiles(String fileUrl) throws IOException {
//		/**
//		 * Source:
//		 * http://stackoverflow.com/questions/17101276/java-download-all-files-and-folders-in-a-directory
//		 * http://stackoverflow.com/questions/3024002/how-to-create-a-folder-in-java
//		 * http://stackoverflow.com/questions/9658297/java-how-to-create-a-file-in-a-directory-using-relative-path
//		 * http://www.java2s.com/Tutorial/Java/0180__File/Removefileordirectory.htm
//		 * http://stackoverflow.com/questions/4875064/jsoup-how-to-get-an-images-absolute-url
//		 * http://www.avajava.com/tutorials/lessons/how-do-i-save-an-image-from-a-url-to-a-file.html
//		 * http://stackoverflow.com/questions/3987921/not-able-to-delete-the-directory-through-java
//		 * 
//		 */
//
////		String[] folders = fileUrl.split("/");
////
////		try{
////			if(folder.mkdirs()) { 
////				System.out.println("Directory Created");
////			} else {
////				System.out.println("Directory exists");
////			}
////		} catch(Exception e){
////			e.printStackTrace();
////		}
//		
//		
//		String file = saveFiles(fileUrl);
//		
//		System.out.println("File Saved: " + file);
//		return file;
//	}

	public String[] saveFiles(String fileUrl){
		
		URL fileURL2;
		String folderName = "";
		File savedFile = null;
		String contentType = "";
		try {
			fileURL2 = new URL(fileUrl);
			contentType = getContentType(fileUrl);
			System.out.println("URL: " + fileUrl + " --------- Content-Type" + contentType);
			
			if(contentType.contains("text/html")){
				savedFile = new File(directoryFile, UUID.randomUUID().toString() + ".html");
				savedFile.createNewFile();
			}
			else{
				savedFile = new File(directoryFile, UUID.randomUUID().toString());
				savedFile.createNewFile();
			}
		
	    	InputStream is = fileURL2.openStream();
	    	
	    	folderName = savedFile.getAbsolutePath();
	    	System.out.println(folderName);
	    	OutputStream os = new FileOutputStream(savedFile);
	    	
			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String [] info = new String[3];
		info[0] = folderName;
		info[1] = contentType;
		
		
		return info;
	}
	
	
	public String getContentType (String urlString){
		URL url = null;
		String contentType = "";
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection)  url.openConnection();
			connection.setRequestMethod("HEAD");
			connection.connect();
			contentType = connection.getContentType();
			
			System.out.println(contentType);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contentType;
	}

}


package rank;

import java.util.ArrayList;
import java.util.List;

import gnu.getopt.Getopt;

public class App {
	
	Indexer indexer = new Indexer();
	Storage storage = new Storage();

	public static void main(String[] args) {

		
		Getopt g = new Getopt("testprog", args, "c:");
		int a;
		String arg;
		
		String dir = "";
		while ((a = g.getopt()) != -1)
		{
			switch(a)
			{
			case 'c':
				arg = g.getOptarg();
				if (arg != null) dir = arg;
				break;
			case '?':
				break; // getopt() already printed an error
			default:
				System.out.print("getopt() returned " + a + "\n");
			}
		}
		
		System.out.println("Inputted depth: " + dir);		


	}
	
	
	private void startIndexRank(String filepath){
		
		List<CrawledLink> visitedLinks = null;
		try {
			visitedLinks = new ArrayList<CrawledLink>(storage.readJSON(filepath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		indexer.indexWords(visitedLinks);
		
		
		
	}

}

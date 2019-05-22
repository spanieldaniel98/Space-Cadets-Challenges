import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;

public class HomePageGetter {
	
	BufferedReader reader;
	Scanner scanner;
	String line, name, queryName;
	URL homePageURL, queryURL;
	URLConnection connection;

	public HomePageGetter() {
		scanner = new Scanner(System.in);
	}
	
	// Asks the user for a name of a person and returns the first URL returned in the Google search query therefor
	public URL getHomePageURL() throws MalformedURLException, Exception {
			
		// Prompts user for person's name
		System.out.print("Enter the name of a person: ");
		name = scanner.nextLine();
			
		// Instantiates search query URL
		queryName = name.replaceAll(" ", "+");
		queryURL = new URL("https://www.google.com/search?&q=" + queryName);	
			
		// Instantiates URL connection and buffered reader
		connection = new URL("https://www.google.com/search?q=" + queryName).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();
		reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
			
		// Finds the first search query URL in the source code, then extracts and returns it
		while((line = reader.readLine()) != null) {
			if(line.contains("h30\">")) {
				homePageURL = new URL(line.split("h30\">|</c")[1]);
				System.out.println("The URL of " + name + "'s homepage is: " + homePageURL);
				return homePageURL;
			}
		}
			
		// If no results are returned in the search, tell the user the person has no homepage and return null
		System.out.println("Unfortunately " + name + " does not have a homepage...");
		return null;
	}
}
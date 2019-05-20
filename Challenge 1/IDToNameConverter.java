import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.net.URL;

public class IDToNameConverter {
	
	BufferedReader reader;
	Scanner scanner;
	String id, line, name;
	URL userURL;
	
	public IDToNameConverter() {
		scanner = new Scanner(System.in); 
	}
	
	// Takes an email ID and returns the name of the user associated therewith
	public String IDToName() throws Exception {
		
		// Prompt and read user input
		System.out.print("Enter a user email ID: ");
		id = scanner.nextLine();
		 
		// Instantiate URL string
		userURL = new URL("https://www.ecs.soton.ac.uk/people/" + id);
		
		// Instantiate buffered reader from URL
		reader = new BufferedReader(new InputStreamReader(userURL.openStream()));
		
		// If URL's source code contains a name, print and return the name
		while((line = reader.readLine()) != null) {
			if(line.contains("property=\"name\"")) {
				name = line.split("property=\"name\">|<em property")[1];
				System.out.println("The user with email ID " + id + " is called " + name);
				return name; 
			}
		}
		// Else say no name was found, and return null
		System.out.println("The user with email ID " + id + " was not found...");
		return null;
	}
}
import java.util.ArrayList;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;

public class PeopleFromID {
	
	ArrayList<String> relatedPeople;
	BufferedReader reader;
	Scanner scanner;
	String ID, relatedID, line, name, queryName, homePageURL;
	URL userURL, queryURL;
	
	public PeopleFromID() {
		scanner = new Scanner(System.in); 
	}
	
	// Prompt and read a user ID from user input
	public void getID() {
		System.out.print("Enter a user email ID: ");
		ID = scanner.nextLine();
	}
	
	public void makeReader() throws Exception {
		// Instantiate BufferedReader from URL
		reader = new BufferedReader(new InputStreamReader(userURL.openStream()));
	}
	
	// Asks the user for a user email ID, and returns the name of the user associated therewith
	public String getNameFromID() throws Exception {
		
		getID();
		 
		// Instantiate URL string
		userURL = new URL("https://www.ecs.soton.ac.uk/people/" + ID);
		makeReader();
		
		// If URL's source code contains a name, print and return the name
		while((line = reader.readLine()) != null) {
			if(line.contains("property=\"name\"")) {
				name = line.split("property=\"name\">|<em property")[1];
				System.out.println("The user with email ID " + ID + " is called " + name);
				return name; 
			}
		}
		// Else say no name was found, and return null
		System.out.println("The user with email ID " + ID + " was not found...");
		return null;
	}
	
	// Takes an email ID and returns the name of the user associated therewith
	public String getNameFromID(String ID) throws Exception {

		// Instantiate URL string
		userURL = new URL("https://www.ecs.soton.ac.uk/people/" + ID);
		makeReader();
			
		// If URL's source code contains a name, print and return the name
		while((line = reader.readLine()) != null) {
			if(line.contains("property=\"name\"")) {
				name = line.split("property=\"name\">|<em property")[1];
				System.out.println("The user with email ID " + ID + " is called " + name);
				return name; 
			}
		}
		// Else say no name was found, and return null
		System.out.println("The user with email ID " + ID + " was not found...");
		return null;
	}
	
	// Takes an email ID and returns an ArrayList of the names of people related thereto
	// TODO: make this work for the protected URLs using authentication!
	public ArrayList<String> getRelatedPeopleFromID() throws Exception {
		
		getID();
		
		// Instantiate URL string
		userURL = new URL("https://secure.ecs.soton.ac.uk/people/" + ID + "/related_people");	
		makeReader();
		
		System.out.println(userURL);
		
		// For each link to a person in URL's source code, store that person's name in an ArrayList
		while((line = reader.readLine()) != null) {
			if(line.contains("https://secure.ecs.soton.ac.uk/people/")) {
				relatedID = line.split("https://secure.ecs.soton.ac.uk/people/|'>")[1];
				relatedPeople.add(getNameFromID(relatedID)); 
			}
		}
		
		if(relatedPeople.isEmpty())
			System.out.println("The user with email ID " + ID + " has no related people...");
		else {
			System.out.println("The user with email ID " + ID + " has the following related people:");
			for(String person : relatedPeople) {
				System.out.println(person);
			}
		}
		return relatedPeople;	
	}
}
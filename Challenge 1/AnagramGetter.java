import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.URL;

public class AnagramGetter {
	
	ArrayList<String> nameAnagrams;
	BufferedReader reader;
	Scanner scanner;
	String anagram, line, name, queryName;
	URL queryURL;

	public AnagramGetter() {
		scanner = new Scanner(System.in);
		nameAnagrams = new ArrayList<String>();
	}
	
	// Asks the user for a name, then prints and returns a list of anagrams thereof
	public ArrayList<String> getAnagrams() throws MalformedURLException, Exception {
		
		// Prompts user for name
		System.out.print("Enter a name: ");
		name = scanner.nextLine();
		
		// Instantiates search query URL and BufferedReader
		queryName = name.replaceAll(" ", "+");
		queryURL = new URL("https://new.wordsmith.org/anagram/anagram.cgi?anagram=" + queryName + "&t=500&a=n");
		reader = new BufferedReader(new InputStreamReader(queryURL.openStream()));
		
		// Finds anagrams for the name in the Wordsmith page source code, prints them and adds them to ArrayList
		while((line = reader.readLine()) != null) {
			if(line.contains("wait")) {
				line = reader.readLine();
				anagram = line.split("</b><br>|<br>")[1];
				nameAnagrams.add(anagram);
				
				while(!(line = reader.readLine()).contains("default")) {
					anagram = line.split("<br>")[0];
					nameAnagrams.add(anagram);
				}
			}
		}
		
		// If no anagrams were found for the name tell the user such
		if(nameAnagrams.isEmpty())
			System.out.println("The name " + name + " has no anagrams...");
		
		// If anagrams were found for the name, print a list thereof
		else {
			System.out.println("The name " + name + " has the following anagrams:");
			for(String anagram : nameAnagrams) {
				System.out.println(anagram);
			}
		}
		
		// Returns ArrayList of anagrams
		return nameAnagrams;	
	}
}
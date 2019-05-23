import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.URL;
import java.nio.charset.Charset;

public class EnglishToFrenchTranslator {
	
	BufferedReader reader;
	HttpURLConnection connection;
	Scanner scanner;
	String line, text, queryText, translatedText;
	StringBuffer response;
	URL requestURL;

	public EnglishToFrenchTranslator() {
		scanner = new Scanner(System.in);
	}
	
	public String translate() throws MalformedURLException, Exception {
		
		// Prompts user for text to translate
		System.out.print("Enter text to translate: ");
		text = scanner.nextLine();
		
		// Instantiates translation query URL
		queryText = text.replaceAll(" ", "%20");
		requestURL = new URL("https://translate.google.com/translate_a/single?client=gtx&sl=en&tl=fr&dt=t&q=" + queryText);
		
		// Instantiates URL connection and BufferedReader
		connection = (HttpURLConnection)requestURL.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.157 Safari/537.36");
		reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
		
		// Instantiates StringBuffer and reads first line of response from web page 
		response = new StringBuffer();
        response.append(reader.readLine());
		reader.close();
		
		// Instantiates translated text variable from response, prints and returns translation
		translatedText = response.toString().split("\\[\\[\\[\"|\"")[1];
		System.out.println("\"" + text + "\" in French is \"" + translatedText + "\"");
		return translatedText;
	}
}
package model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Regexer {
	
	private List<Entry<String, Pattern>> myPatterns;

	public static boolean match(String input, Pattern regex) {
		return regex.matcher(input).matches();
	}

	public static List<Entry<String, Pattern>> makePatterns(String syntax) {
		ResourceBundle resources = ResourceBundle.getBundle(syntax);
		List<Entry<String, Pattern>> patterns = new ArrayList<>();
		Enumeration<String> iter = resources.getKeys();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String regex = resources.getString(key);
			patterns.add(new SimpleEntry<String, Pattern>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
		return patterns;
	}

	public Regexer() {
//		String[] examples = { 
//				"# foo", 
//				"foo #", 
//				"#", 
//				"", 
//				"fd", 
//				"FD", 
//				"equal?",
//				"equalp", 
//				"+", 
//				"-", 
//				"*", 
//				"/", 
//				"%", 
//				"~", 
//				"--", 
//				"equal??",
//				"forwardd", 
//				"allOrNothing", 
//				"all_or_nothing", 
//				"allOr_nothing?",
//				"allOr?nothing_", 
//				":allornothing", 
//				"90", 
//				"9.09", 
//				"9.0.0", 
//				"[",
//				"]", 
//				"(", 
//				")" 
//				};
//		String userInput = "fd 50 rt 90 BACK :distance Left :angle";
		
		// create a list of things to check
		myPatterns = new ArrayList<>();
		// these are more specific, so add them first to ensure they are checked
		// first
		myPatterns.addAll(makePatterns("resources/languages/English"));
		myPatterns.addAll(makePatterns("resources/languages/Syntax"));
	}
}
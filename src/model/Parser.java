package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import model.instructions.Instruction;

public class Parser {
	// To parse, first create a parsing tree to solve recursive problems.  Then use reflection to instantiate the proper commands.
	// is created
	private static List<Entry<String, Pattern>> patterns;
	private static Map<String,String> commandMap;
	private static final String[] keyList = new String[]{"Forward","Sum"};
	private static final String[] valueList = new String[]{"Forward","MathInstruction"};
	private static void addAllPatterns(){
		patterns = new ArrayList<>();
		patterns.addAll(makePatterns("resources/languages/English"));
	    patterns.addAll(makePatterns("resources/languages/Syntax"));
	}
	private static void setUp(){
		addAllPatterns();
		makeCommandMap();
		runOnce = false;
	}
	
	private static void makeCommandMap() {
		commandMap = new HashMap();
		for(int i=0; i<keyList.length; i++){
			commandMap.put(keyList[i], valueList[i]);
		}
	}

	// TODO: get rid of this
	static int furthestDepth;
	static boolean runOnce = true;
	public static List<Node> parse(String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		if(runOnce)
			setUp();
		String command = "fd + 50 40 fd fd 50 rt 90 BACK :distance Left :angle";
		furthestDepth = -1;
		String[] splitCommands = command.split(" ");
		List<Node> outList = new ArrayList<Node>();
		for(int i = 0; i<splitCommands.length;i++){
			if(furthestDepth>=i){
				i = furthestDepth+1;
			}
			System.out.println("new tree at " + i+" "+ furthestDepth);
			outList.add(makeTree(splitCommands, i));
			
		}
		for(Node root:outList){
			inOrderTraversal(root);
		}
		return outList;
	}
	private static void inOrderTraversal(Node root){
		if(root==null)
			return;
		if(root.getLeft()!=null){
			inOrderTraversal(root.getLeft());
		}
		System.out.println(root.getValue());
		if(root.getRight()!=null){
				inOrderTraversal(root.getLeft());
		}
		
	}
	private static Node makeTree(String[] command, int start) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// base case
		furthestDepth = start;
		int myVars = 0;
		int neededVars = -1;
		Node myNode = null;
		// go through and determine what type of node we are adding
		String match = testMatches(command[start]);
		switch (match.toUpperCase()){
		case "COMMENT":
			return makeTree(command,start+1);
		case "VARIABLE":
		case "CONSTANT":
			// make node with string
			myVars++;
			return new Node(command[start]);
		case "COMMAND":
			// make node with command, if found
			// check map
			// return new usercommand using map
			// user command makes the tree for us, so just return the root node that returns
		case "LISTSTART":
			// need to count brackets
			break;
		case "GROUPSTART":
			break;
		default:
			// this is either a known command or invalid input.  instantiate the command, if reflection cannot find the file then must be invalid
			Instruction myInt = null;
			try{
				System.out.println(Class.forName("model.instructions."+commandMap.get(match)).asSubclass(Instruction.class).getConstructor(String[].class));
				String[] parameters=new String[]{"hello"};
				myInt = Class.forName("model.instructions."+commandMap.get(match)).asSubclass(Instruction.class).getConstructor(String[].class).newInstance(new Object[]{parameters});
				neededVars = myInt.getNumberOfArguments();
				myNode = new Node(match);
			}
			catch(ClassNotFoundException e){
				// Throw input error
				System.out.println("No such class, yet!");
			}
		}
		if(myVars>=neededVars){
			return myNode;
		}
		System.out.println("making left tree now");
		myNode.addChildLeft(makeTree(command,start+1));
		myVars++;
		if(myVars>=neededVars)
			return myNode;
		System.out.println("making right tree now");
		myNode.addChildRight(makeTree(command,start+1));
		myVars++;
		return myNode;
	}
	public static boolean match (String input, Pattern regex) {
        return regex.matcher(input).matches();
    }
	public static boolean matchString (String input, String regex) {
		return input.matches(regex);
    }

	public static List<Entry<String, Pattern>> makePatterns (String syntax) {
        ResourceBundle resources = ResourceBundle.getBundle(syntax);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String regex = resources.getString(key);
            patterns.add(new SimpleEntry<String, Pattern>(key,
                         Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
        return patterns;
    }
	private static String testMatches (String test) {
            if (test.trim().length() > 0) {
                for (Entry<String, Pattern> p : patterns) {
                    if (match(test, p.getValue())) {
                        System.out.println(String.format("%s matches %s", test, p.getKey()));
                        return p.getKey();
                    }
                }
            }
		System.out.println(String.format("%s not matched", test));
		return "NONE";
}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
	Parser p = new Parser();
	p.parse(null);
	}
}

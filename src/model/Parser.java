package model;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import model.instructions.Instruction;

/**
 * To parse, first create a parsing tree which will be created/traversed recursively
 * Then, use reflection to instantiate the proper commands
 * @author Primary: Greg, Secondary: Sid
 */
public class Parser {
	private static List<Entry<String, Pattern>> patterns;
	private static Map<String,String> commandMap;
	private static final String[] commandTypes = new String[]{"BooleanInstruction","MathInstruction","MovementInstruction"};
	private int furthestDepth;
	
	public Parser(){
		patterns = new ArrayList<>();
		commandMap = new HashMap<String, String>();
		addAllPatterns();
		makeCommandMap();
	}
	
	private void addAllPatterns(){
		makePatterns("resources/languages/English");
	    makePatterns("resources/languages/Syntax");
	}
	
	public void makePatterns (String resourceInput) {
        ResourceBundle resources = ResourceBundle.getBundle(resourceInput);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String value = resources.getString(key);
            patterns.add(new SimpleEntry<String, Pattern>(key,
                         Pattern.compile(value, Pattern.CASE_INSENSITIVE)));
        }
    }
	
	private void makeCommandMap(){
		for(String s:commandTypes){
			Class<?> c = null;
			try {
				c = Class.forName("model.instructions."+s+"$implementers");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for(Object d:c.getEnumConstants()){
				commandMap.put(d.toString(), s);
			}
		}
	}

	// TODO: get rid of this
	public List<Instruction> parseAndExecute(String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		String command = "[ fd + 200 40 fd fd 50 ] rt 90 BACK 40";
		furthestDepth = 0;
		String[] splitCommands = command.split(" ");
		List<Node> nodeList = new ArrayList<Node>();
		while(furthestDepth<splitCommands.length){
			nodeList.add(makeTree(splitCommands));
		}
		for(Node root:nodeList){
			System.out.println("new tree");
			inOrderTraversal(root);
		}
		List<Instruction> outList = new ArrayList<Instruction>();
		// method here to convert from ArrayList to executable instruction, and then pass that to an executor by calling all of the .executes.
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
			inOrderTraversal(root.getRight());
		}
	}
	
	private Node makeTree(String[] command) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// base case
		System.out.println(furthestDepth);
		int myVars = 0;
		int neededVars = -1;
		Node myNode = null;
		// go through and determine what type of node we are adding
		String match = testMatches(command[furthestDepth]).toUpperCase();
		//this switch will eventually be combined into the map.
		switch (match){
		case "LISTSTART":
			furthestDepth++;
			return new Node(command[furthestDepth-1]);
		case "COMMENT":
			furthestDepth++;
			return makeTree(command);
		case "VARIABLE":
		case "CONSTANT":
			// make node with string
			furthestDepth++;
			return new Node(command[furthestDepth-1]);
		case "COMMAND":
			// make node with command, if found
			// check map
			// return new usercommand using map
			// user command makes the tree for us, so just return the root node that returns
		case "LISTEND":
			furthestDepth++;
			return new Node(command[furthestDepth-1]);
		case "GROUPSTART":
			break;
		default:
			//this is either a known command or invalid input.  
			//instantiate the command, if reflection cannot find the file then must be invalid
			try{
				String[] parameters=new String[]{match};
				System.out.println(commandMap.get(match));
				Instruction myInt = Class.forName("model.instructions."+commandMap.get(match)).asSubclass(Instruction.class).getConstructor(String[].class).newInstance(new Object[]{parameters});
				furthestDepth++;
				neededVars = myInt.getNumberOfArguments();
				myNode = new Node(match);
			}
			catch(ClassNotFoundException e){
				// Throw input error
				System.out.println("No such class/function, yet!");
			}
		}
		if(myVars>=neededVars){
			return myNode;
		}
		System.out.println("making left tree now for "+myNode);
		myNode.addChildLeft(makeTree(command));
		myVars++;
		if(myVars>=neededVars){
			System.out.println("im done");
			return myNode;
		}
		System.out.println("making right tree now for "+myNode);
		System.out.println(command[furthestDepth]);
		myNode.addChildRight(makeTree(command));
		myVars++;
		return myNode;
	}
	
	public boolean match (String input, Pattern regex) {
        return regex.matcher(input).matches();
    }
	
	public boolean matchString (String input, String regex) {
		return input.matches(regex);
    }
	
	private String testMatches(String test) {
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
		p.parseAndExecute(null);
	}
}

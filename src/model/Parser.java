package model;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import view.SLogoView;
import model.instructions.Instruction;
import model.turtle.TurtleCommand;

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
	private SLogoView mySLogoView;
	private Map<String, Double> myVariableMap; 
	
	public Parser(SLogoView view){
		mySLogoView = view;
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
			commandMap.put("[", "ListInstruction");
		}
	}
	List<Instruction> outList;
	public List<Node> parseAndExecute(String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		String command = "[ fd + 200 400 ] fd fd 50 rt 90 BACK 40";
		furthestDepth = 0;
		String[] splitCommands = command.split(" ");
		List<Node> nodeList = new ArrayList<Node>();
		while(furthestDepth<splitCommands.length){
			nodeList.add(makeTree(splitCommands));
			// System.out.println("tree done");
		}
		for(Node root:nodeList){
			System.out.println("new tree");
			inOrderTraversal(root,0);
		}
		for(Node root:nodeList){
			System.out.println("new tree");
			inOrderInstructionExecuter(root,0);
		}
		// method here to convert from ArrayList to executable instruction, and then pass that to an executor by calling all of the .executes.
		
		return nodeList;
		
	}
	private void inOrderTraversal(Node root, int depth){
		if(root.getChildren()!=null){
			List<Node> children = root.getChildren();
			depth++;
			for(int i =0; i<children.size();i++){
				inOrderTraversal(children.get(i), depth);
			}
		}
		System.out.println(root.getValue()+" at depth: "+depth);
	}
	private void inOrderInstructionExecuter(Node root, int depth){
		boolean exploring = false;
		if(root==null){
			return;
		}
		String list = null;
		if(root.getChildren()!=null){
			List<Node> children = root.getChildren();
			depth++;
			
			for(int i =0; i<children.size();i++){
				if(root.getValue().equals("[")){
					StringBuffer myBuff = new StringBuffer();
					exploring = true;
					listExplorer(myBuff, children.get(i));
					list = myBuff.toString().trim();
					System.out.println("the list is "+ list);
					break;
				}
				inOrderInstructionExecuter(children.get(i), depth);
			}
		}
		try {
			if(root.getChildren().size()!=0){
			String[] parameters;
			if(exploring)
				parameters= new String[]{list};
			else
				parameters = makeParameters(root);
			System.out.println("model.instructions."+commandMap.get(root.getValue()));
			Instruction myInt = Class.forName("model.instructions."+commandMap.get(root.getValue())).asSubclass(Instruction.class).getConstructor(String[].class).newInstance(new Object[]{parameters});
			root.updateValue(myInt.execute());
			TurtleCommand tempTurtle=myInt.getTurtleCommand();
			if(tempTurtle!=null){
				ArrayList<TurtleCommand> commandList = new ArrayList<TurtleCommand>();
				commandList.add(tempTurtle);
				mySLogoView.updateWorkspace(commandList);
			}
			}
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("FAILED");
		}
	}
	private void listExplorer(StringBuffer sb, Node root) {
		System.out.println(root.getValue());
		if(root!=null){
			sb.append(root.getValue()+" ");
		}
		if(root.getChildren()!=null){
			List<Node> children = root.getChildren();
			for(int i =0; i<children.size();i++){
				System.out.println("Found" + children.get(i).getValue());	
				listExplorer(sb,children.get(i));
			}
		}
		//System.out.println("Found" + root.getValue());	
	}

	private String[] makeParameters(Node root) {
		String[] myKids = root.childrenToStringArray();
		String[] output = new String[myKids.length+1];
		output[0] = root.getValue();
		System.out.println("parameters " + output[0]);
		for(int i =0; i<myKids.length;i++){
			output[i+1] = myKids[i];
			System.out.println("parameters " + output[i+1]);
		}
		return output;
	}

	// add catch for array out of bounds
	private Node makeTree(String[] command) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		// base case
		// System.out.println(furthestDepth);
		int myVars = 0;
		int neededVars = -1;
		Node myNode = null;
		// go through and determine what type of node we are adding
		String match = testMatches(command[furthestDepth]).toUpperCase();
		//this switch will eventually be combined into the map.
		switch (match){
		case "LISTSTART":
			furthestDepth++;
			myNode = new Node(command[furthestDepth-1]);
			Node temp;
			while(!(temp=makeTree(command)).getValue().equals("]")){
				myNode.addChild(temp);
			}
			return myNode;
		case "COMMENT":
			furthestDepth++;
			return makeTree(command);
		case "VARIABLE":
			// look up in map, if not throw error
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
				System.out.println(match + " "+ commandMap.get(match));
				Instruction myInt = Class.forName("model.instructions."+commandMap.get(match)).asSubclass(Instruction.class).getConstructor(String[].class).newInstance(new Object[]{parameters});
				furthestDepth++;
				System.out.println(myInt);
				neededVars = myInt.getNumberOfArguments();
				myNode = new Node(match);
			}
			catch(ClassNotFoundException e){
				// Throw input error
				System.out.println("No such class/function, yet!");
			}
		}
		while(myVars<neededVars){
			myNode.addChild(makeTree(command));
			myVars++;
		}
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
					// System.out.println(String.format("%s matches %s", test, p.getKey()));
					return p.getKey();
				}
			}
		}
		// System.out.println(String.format("%s not matched", test));
		return "NONE";
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		Parser p = new Parser(null);
		p.parseAndExecute(null);
	}
}

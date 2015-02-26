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
import model.instructions.Constant;
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
		myVariableMap = new HashMap<String, Double>();
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
			commandMap.put("REPEAT", "ControlInstruction");
			commandMap.put("IF", "ControlInstruction");
			commandMap.put("IFELSE", "ControlInstruction");
		}
	}
	List<Instruction> outList;
	public void parseAndExecute(String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		input = "[ fd + 200 400 ] fd fd 50 rt 90 BACK 40";
		furthestDepth = 0;
		String[] splitCommands = input.split(" ");
		List<Node> nodeList = new ArrayList<Node>();
		while(furthestDepth<splitCommands.length){
			nodeList.add(makeTree(splitCommands));
			// System.out.println("tree done");
		}
		for(Node root:nodeList){
			System.out.println("new tree");
			printInOrderTraversal(root);
		}
		for(Node root:nodeList){
			System.out.println("new tree");
			inOrderInstructionExecuter(root,0);
			System.out.println(root.getValue()+" "+root.getInstruction());
			root.getInstruction().execute();
			// Call turtlecommand maker
			List<TurtleCommand> commandList =new ArrayList();
			turtleCommandGetter(commandList, root);
			if(!commandList.isEmpty())
				mySLogoView.updateWorkspace(commandList);
			//mySLogoView.updateVariables(myVariableMap);
		}
		
		// method here to convert from ArrayList to executable instruction, and then pass that to an executor by calling all of the .executes.
		
	}
	private void turtleCommandGetter(List<TurtleCommand> cList, Node root) {
		for(Node n: inOrderTraverser(root)){
			System.out.println(n.getValue());
			TurtleCommand t = n.getInstruction().getTurtleCommand();
			System.out.println(t);
			if(t!=null)
				cList.add(t);
		}
	}

	private List<Node> inOrderTraverser(Node root) {
		List<Node> output = new ArrayList();
		inOrderTraverserHelper(output,root);
		return output;
	}
	private void inOrderTraverserHelper(List<Node> in,Node root) {
		if(root.getChildren()!=null){
			List<Node> children = root.getChildren();
			for(int i =0; i<children.size();i++){
				inOrderTraverserHelper(in,children.get(i));
			}
		}
		in.add(root);
	}
	private void printInOrderTraversal(Node root){
		for(Node n:inOrderTraverser(root)){
			System.out.println(n.getValue());
		}
	}
	private void inOrderInstructionExecuter(Node root, int depth){
		if(root==null){
			return;
		}
		if(root.getChildren()!=null){
			List<Node> children = root.getChildren();
			depth++;
			for(int i =0; i<children.size();i++){
				inOrderInstructionExecuter(children.get(i), depth);
			}
		}	
			// System.out.println("model.instructions."+root.getValue()+" "+commandMap.get(root.getValue()));
			List<Instruction> descendantInstructions = getChildInstructions(root);
			Instruction myInt;
			try {
				if(testMatches(root.getValue()).equalsIgnoreCase("Constant"))
					myInt = new Constant(root.getValue());
				else
					myInt = Class.forName("model.instructions."+commandMap.get(root.getValue())).asSubclass(Instruction.class).getConstructor(new Class[]{List.class,String.class,SLogoView.class}).newInstance(new Object[]{descendantInstructions, root.getValue(),mySLogoView});
				System.out.println("made int "+ myInt);
				root.setInstruction(myInt);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private List<Instruction> getChildInstructions(Node root) {
		List<Node> rootChildren = root.getChildren();
		if(root.getChildren().size()==0)
			return null;
		List<Instruction> output = new ArrayList();
		for(Node n:rootChildren){
			output.add(n.getInstruction());
		}
		return output;
	}

	// add catch for array out of bounds
	// BIG TODO: merge instruction tree and this tree to be one and the same
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
				System.out.println(match);
				Instruction myInt = Class.forName("model.instructions."+commandMap.get(match)).asSubclass(Instruction.class).getConstructor(new Class[]{List.class,String.class,SLogoView.class}).newInstance(new Object[]{null, match,mySLogoView});
				furthestDepth++;
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

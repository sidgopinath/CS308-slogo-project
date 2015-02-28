package model;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import model.instructions.Constant;
import model.instructions.Instruction;
import model.instructions.ListInstruction;
import model.instructions.Variable;
import view.SLogoView;

/**
 * To parse, first create a parsing tree which will be created/traversed recursively
 * Then, use reflection to instantiate the proper commands
 * Note, our list implementation does not allow a list to return a value
 * @author Primary: Greg, Secondary: Sid
 */
public class Parser implements Observer{
	
	private List<Entry<String, Pattern>> myPatterns;
	private Map<String,String> myCommandMap;
	private static final String[] COMMAND_TYPES = new String[]{"BooleanInstruction","ControlInstruction","MathInstruction","MovementInstruction","TurtleRequestInstruction"};
	private int myFurthestDepth;
	private SLogoView mySLogoView;
	private ExecutionEnvironment myExecutionParameters;
	
	public Parser(SLogoView view){
		mySLogoView = view;
		myPatterns = new ArrayList<>();
		myCommandMap = new HashMap<String, String>();
		myExecutionParameters = new ExecutionEnvironment();
		myExecutionParameters.addObserver(this);
		myExecutionParameters.addObserver(view);
		addAllPatterns("English");
		makeCommandMap();
	}
	
	private void addAllPatterns(String language){
		makePatterns("resources/languages/" + language);
	    makePatterns("resources/languages/Syntax");
	}
	
	private void makePatterns (String resourceInput) {
        ResourceBundle resources = ResourceBundle.getBundle(resourceInput);
        Enumeration<String> iter = resources.getKeys();
        while (iter.hasMoreElements()) {
            String key = iter.nextElement();
            String value = resources.getString(key);
            myPatterns.add(new SimpleEntry<String, Pattern>(key, Pattern.compile(value, Pattern.CASE_INSENSITIVE)));
        }
    }
	
	private void makeCommandMap() {
		for (String type : COMMAND_TYPES) {
			Class<?> classType = null;
			try {
				classType = Class.forName("model.instructions." + type + "$implementers");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			for (Object d : classType.getEnumConstants()) {
				myCommandMap.put(d.toString(), type);
			}
			myCommandMap.put("[", "ListInstruction");
			myCommandMap.put("VARIABLE", "Variable");
		}
	}

	public void parseAndExecute(String input) throws InstantiationException,IllegalAccessException, IllegalArgumentException,InvocationTargetException, ModelException, NoSuchMethodException,SecurityException {
		try {
			myFurthestDepth = 0;
			// System.out.println("parser input" + input);
			input = input.replaceAll("\\s+", " ");
			String[] splitCommands = input.split(" ");
			List<Node> nodeList = new ArrayList<Node>();
			while (myFurthestDepth < splitCommands.length) {
				nodeList.add(makeTree(splitCommands));
				// System.out.println("tree done");
			}
			
//			for (Node root : nodeList) {
//				System.out.println("new tree");
//				System.out.println(root.getInstruction());
//				printTree(root);
//				printInOrderTraversal(root);
//			}
			
			for (Node root : nodeList) {
				root.getInstruction().execute();
			}
			
		} catch (Exception e) {
			//System.out.println("in parse and execute");
			mySLogoView.openDialog("Invalid input! Try again.");
			throw new ModelException();
		}
	}
	
//	private void turtleCommandGetter(List<TurtleCommand> cList, Node root) {
//		for(Node n: inOrderTraverser(root)){
//			//System.out.println(n.getValue());
//			TurtleCommand t = n.getInstruction().getTurtleCommand();
//			//System.out.println(t);
//			if(t!=null)
//				cList.add(t);
//		}
//	}

//	private List<Node> inOrderTraverser(Node root) {
//		List<Node> output = new ArrayList<Node>();
//		inOrderTraverserHelper(output,root);
//		return output;
//	}
	
//	private void inOrderTraverserHelper(List<Node> in,Node root) {
//		if(root.getChildren()!=null){
//			List<Node> children = root.getChildren();
//			for(int i =0; i<children.size();i++){
//				inOrderTraverserHelper(in,children.get(i));
//			}
//		}
//		in.add(root);
//	}
	
//	private void printInOrderTraversal(Node root){
//		for(Node n:inOrderTraverser(root)){
//			System.out.println(n.getInstruction());
//		}
//	}
//	
//	private void printTree(Node root){
//		System.out.println();
//		System.out.print("start line ");
//		if(root.getChildren().size()==0){
//			return;
//		}
//		for(Node N:root.getChildren()){
//			
//			System.out.print(N.getInstruction());
//		}
//		for(Node N:root.getChildren()){
//			printTree(N);
//		}
//	}

	// BIG TODO: merge instruction tree and this tree to be one and the same
	private Node makeTree(String[] command) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ModelException {
		// base case
		int myVars = 0;
		int neededVars = -1;
		Node myNode = null;

		// go through and determine what type of node we are adding
		String match = testMatches(command[myFurthestDepth]).toUpperCase();

		switch (match) {
		case "LISTSTART":
			myFurthestDepth++;
			List<Instruction> futureInstructions = new ArrayList<Instruction>();
			myNode = new Node(new ListInstruction(futureInstructions, match,mySLogoView, myExecutionParameters));
			Node temp;
			while (true) {
				temp = makeTree(command);
				System.out.println("MAKING LIST " + temp);
				if (temp == null)
					break;
				myNode.addChild(temp);
				futureInstructions.add(temp.getInstruction());
			}
			return myNode;
		case "COMMENT":
			myFurthestDepth++;
			return makeTree(command);
		case "CONSTANT":
			myFurthestDepth++;
			return new Node(new Constant(command[myFurthestDepth - 1]));
		case "VARIABLE":
			myFurthestDepth++;
			Instruction tempInt = new Variable(command[myFurthestDepth - 1]);
			myExecutionParameters.addObserver(tempInt);
			return new Node(tempInt);
		case "COMMAND":
			// make node with command, if found
			// check map
			// return new usercommand using map
			// user command makes the tree for us, so just return the root node
			// that returns
		case "LISTEND":
			myFurthestDepth++;
			return null;
			// case "GROUPSTART":
			// break;
		default:
		}
		// this is either a known command or invalid input.
		// instantiate the command, if reflection cannot find the file then must
		// be invalid
		List<Instruction> futureInstructions = new ArrayList<Instruction>();
		System.out.println("match" + match);
		try {
			Instruction myInt = Class
					.forName("model.instructions." + myCommandMap.get(match))
					.asSubclass(Instruction.class)
					.getConstructor(
							new Class[] { List.class, String.class,
									SLogoView.class, ExecutionEnvironment.class })
					.newInstance(
							new Object[] { futureInstructions, match,
									mySLogoView, myExecutionParameters });
			myFurthestDepth++;
			myExecutionParameters.addObserver(myInt);
			myNode = new Node(myInt);
			neededVars = myInt.getNumberOfArguments();
		} catch (ClassNotFoundException e) {
			throw new ModelException();
		} catch (ArrayIndexOutOfBoundsException e) {
			mySLogoView.openDialog("Out of bounds error.");
			throw new ModelException();
		}

		while (myVars < neededVars) {
			Node level = makeTree(command);
			System.out
					.println("got kid " + level.getInstruction() + " for "
							+ myNode.getInstruction() + " " + myVars + " "
							+ neededVars);
			myNode.addChild(level);
			myVars++;
		}
		for (Node child : myNode.getChildren()) {
			try {
				futureInstructions.add(child.getInstruction());
			} catch (NullPointerException e) {
				mySLogoView.openDialog("Invalid input!");
				throw new ModelException();
			}
		}
		return myNode;
	}
	
	private boolean match (String input, Pattern regex) {
        return regex.matcher(input).matches();
    }
	
//	public boolean matchString (String input, String regex) {
//		return input.matches(regex);
//    }
	
	private String testMatches(String test) {
		if (test.trim().length() > 0) {
			for (Entry<String, Pattern> p : myPatterns) {
				if (match(test, p.getValue())) {
					// System.out.println(String.format("%s matches %s", test, p.getKey()));
					return p.getKey();
				}
			}
		}
		// System.out.println(String.format("%s not matched", test));
		return "NONE";
	}

	public void setLanguage(String language){
		myPatterns = new ArrayList<>();
		addAllPatterns(language);
	}

	@Override
	public void update(Observable o, Object arg) {
		myExecutionParameters = (ExecutionEnvironment) o;
		// System.out.println("notified"+executionParameters.get());
	}
}
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

import view.SLogoView;
import model.instructions.Constant;
import model.instructions.Instruction;
import model.instructions.ListInstruction;
import model.instructions.StringInstruction;
import model.instructions.UserRunningInstruction;
import model.instructions.Variable;
import model.turtle.TurtleCommand;

/**
 * To parse, first create a parsing tree which will be created/traversed recursively
 * Then, use reflection to instantiate the proper commands
 * Note, our list implementation does not allow a list to return a value
 * @author Primary: Greg, Secondary: Sid
 */
public class Parser implements Observer{
	private static List<Entry<String, Pattern>> patterns;
	private static Map<String,String> commandMap;
	private static final String[] commandTypes = new String[]{"BooleanInstruction","ControlInstruction","MathInstruction","MovementInstruction","TurtleRequestInstruction"};
	private int furthestDepth;
	private SLogoView mySLogoView;
	private ExecutionEnvironment executionParameters;
	
	public Parser(SLogoView view){
		mySLogoView = view;
		patterns = new ArrayList<>();
		commandMap = new HashMap<String, String>();
		executionParameters = new ExecutionEnvironment();
		executionParameters.addObserver(this);
		executionParameters.addObserver(view);
		addAllPatterns("English");
		makeCommandMap();
	}
	
	private void addAllPatterns(String language){
		makePatterns("resources/languages/" + language);
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
			commandMap.put("VARIABLE","Variable");
		}
	}
	public void parseAndExecute(String input) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ModelException, NoSuchMethodException, SecurityException{
		//input = "MAKE :var 50 fd :var";
		try{
		furthestDepth = 0;
		System.out.println("parser input" + input);
		input = input.replaceAll("\\s+", " ");
		String[] splitCommands = input.split(" ");
		List<Node> nodeList = new ArrayList<Node>();
		while(furthestDepth<splitCommands.length){
			nodeList.add(makeTree(splitCommands));
			// System.out.println("tree done");
		}
		for(Node root:nodeList){
			System.out.println("new tree");
			System.out.println(root.getInstruction());
			//printTree(root);
			printInOrderTraversal(root);
		}
		List<TurtleCommand> commandList =new ArrayList();
		for(Node root:nodeList){
			root.getInstruction().execute();
			//if(!commandList.isEmpty())
				//mySLogoView.updateWorkspace(commandList);
			  //mySLogoView.updateVariables(myVariableMap);
		}
		}
		catch (Exception e){
			System.out.println("in parse and execute");
			System.out.println(e +" "+e.getMessage());
			e.printStackTrace();
			mySLogoView.openDialog("Invalid input! Try again.");
			throw new ModelException(e.toString());
		}
	}
	private void turtleCommandGetter(List<TurtleCommand> cList, Node root) {
		for(Node n: inOrderTraverser(root)){
			//System.out.println(n.getValue());
			TurtleCommand t = n.getInstruction().getTurtleCommand();
			//System.out.println(t);
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
			System.out.println(n.getInstruction());
		}
	}
	private void printTree(Node root){
		System.out.println();
		System.out.print("start line ");
		if(root.getChildren().size()==0){
			return;
		}
		for(Node N:root.getChildren()){
			
			System.out.print(N.getInstruction());
		}
		for(Node N:root.getChildren()){
			printTree(N);
		}
	}

	// add catch for array out of bounds
	// BIG TODO: merge instruction tree and this tree to be one and the same
	private Node makeTree(String[] command) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ModelException {
		int myVars = 0;
		int neededVars = -1;
		Node myNode = null;
		List<Instruction> futureInstructions = new ArrayList();
		// go through and determine what type of node we are adding
		String match = testMatches(command[furthestDepth]).toUpperCase();
		//this switch will eventually be combined into the map.
		switch (match){
		//instead of while loop, find where closing bracket is
		//run until you hit that
		//have some way to account for nested loops
		case "LISTSTART":
			// count number of strings til you reach a ], thats number of dependencies
			furthestDepth++;
			myNode = new Node(new ListInstruction(futureInstructions, match,mySLogoView, executionParameters));
			Node temp;
			while(true){
				temp=makeTree(command);
				System.out.println("MAKING LIST "+temp);
				if(temp==null)
					break;
				myNode.addChild(temp);
				futureInstructions.add(temp.getInstruction());
			}
			return myNode;
		case "COMMENT":
			furthestDepth++;
			return makeTree(command);
		case "CONSTANT":
			furthestDepth++;
			return new Node(new Constant(command[furthestDepth-1], executionParameters));
		case "VARIABLE":
			furthestDepth++;
			Instruction tempInt=new Variable(command[furthestDepth-1], executionParameters);
			executionParameters.addObserver(tempInt);
			return new Node(tempInt);
		case "COMMAND":
			furthestDepth++;
			if(executionParameters.getCommand(command[furthestDepth-1])!=null){
				myNode = new Node(new UserRunningInstruction(futureInstructions, command[furthestDepth-1], mySLogoView, executionParameters));
				System.out.println(" Node "+ myNode);
				neededVars = 1;
			}
			else{
				return new Node(new StringInstruction(command[furthestDepth-1], executionParameters));
			}
			break;
		case "LISTEND":
			furthestDepth++;
			return null;
		default:
		}
			//this is either a known command or invalid input.  
			//instantiate the command, if reflection cannot find the file then must be invalid
		if(myNode==null){
			System.out.println("match" + match);
			try{
				Instruction myInt = Class.forName("model.instructions."+commandMap.get(match)).asSubclass(Instruction.class).getConstructor(new Class[]{List.class,String.class,SLogoView.class,ExecutionEnvironment.class}).newInstance(new Object[]{futureInstructions, match,mySLogoView, executionParameters});
				furthestDepth++;
				executionParameters.addObserver(myInt);
				myNode = new Node(myInt);
				neededVars = myInt.getNumberOfArguments();
			}
			catch(ClassNotFoundException e){
				throw new ModelException(e.toString());
			}
			catch(ArrayIndexOutOfBoundsException e){
				mySLogoView.openDialog("Out of bounds error.");
				throw new ModelException(e.toString());
			}
		}
		while(myVars<neededVars){
			Node level = makeTree(command);
			System.out.println("got kid "+ level.getInstruction()+" for "+myNode.getInstruction()+" "+ myVars+ " "+ neededVars);
			myNode.addChild(level);
			myVars++;
		}
		for(Node child:myNode.getChildren()){
			try{
				futureInstructions.add(child.getInstruction());
			}
			catch(NullPointerException e){
				mySLogoView.openDialog("Invalid input!");
				throw new ModelException(e.toString());
			}
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

	public void setLanguage(String language){
		patterns = new ArrayList<>();
		addAllPatterns(language);
	}

	@Override
	public void update(Observable o, Object arg) {
		executionParameters = (ExecutionEnvironment) o;
		//System.out.println("notified"+executionParameters.get());
			}
}
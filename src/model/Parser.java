package model;

import java.lang.reflect.InvocationTargetException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
import model.instructions.StringInstruction;
import model.instructions.UserRunningInstruction;
import model.instructions.Variable;
import view.SLogoView;
import view.ViewUpdateModule;
import view.ViewUpdater;

/**
 * This class is used as the primary backend class.  The parser reads in a string value and creates an expression tree of instructions that
 * can are then executed.  Valid instructions are given in an enum at the head of each instruction file, and are read into the Command Types array.
 * This class also handles enabing all other classes to observe the execution environment passed in to each instruction.
 * Note, our list implementation does not allow a list to return a value
 * @author Primary: Greg, Secondary: Sid
 */
public class Parser implements Observer{
	
	private List<Entry<String, Pattern>> myPatterns;
	private Map<String,String> myCommandMap;
	private static final String[] COMMAND_TYPES = new String[]{"BooleanInstruction","ControlInstruction","FrontEndInstruction","MathInstruction","MovementInstruction","MultipleTurtlesInstruction","TurtleRequestInstruction"};
	private int myFurthestDepth;
	private SLogoView mySLogoView;
	private ViewUpdater myViewUpdater;
	private ExecutionEnvironment myExecutionParameters;
	private ViewUpdateModule myModule;
	public Parser(SLogoView view){
		mySLogoView = view;
		myModule = new ViewUpdateModule();
		myPatterns = new ArrayList<>();
		myCommandMap = new HashMap<String, String>();
		myExecutionParameters = new ExecutionEnvironment();
		myExecutionParameters.addObserver(this);
		myViewUpdater = new ViewUpdater(view);
		myModule.addObserver(myViewUpdater);
		myExecutionParameters.addObserver(myViewUpdater); //create the viewupdater and store as global and pass to others
		view.setEnvironment(myExecutionParameters);
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

	public void parseAndExecute(String input) {
		try {
			System.out.println(input);
			myFurthestDepth = 0;
			String[] splitCommands = input.split("\\s+");
			List<Node> nodeList = new ArrayList<Node>();
			while (myFurthestDepth < splitCommands.length) {
				nodeList.add(makeTree(splitCommands));
			}
			for (Node root : nodeList) {
					try{
						for(int turtle:myExecutionParameters.getActiveList()){
							myExecutionParameters.setActiveTurtle(turtle);
							root.getInstruction().execute();
						}
					}
					catch (ConcurrentModificationException e){
						
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			mySLogoView.openDialog("Invalid input! Try again.");
		}
	}
	private Node makeTree(String[] command) throws ModelException{
	int myVars = 0;
		int neededVars = -1;
		Node myNode = null;
		List<Instruction> futureInstructions = new ArrayList<Instruction>();
		String match = testMatches(command[myFurthestDepth]).toUpperCase();
		System.out.println("match " + match);
		switch (match){
		//TODO: instead of while loop, find where closing bracket is
		//run until you hit that
		//have some way to account for nested loops
		case "LISTSTART":
			// count number of strings til you reach a ], thats number of dependencies
			myFurthestDepth++;
			myNode = new Node(new ListInstruction(futureInstructions, match,myViewUpdater,myModule, myExecutionParameters));
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
			return new Node(new Constant(command[myFurthestDepth - 1], myExecutionParameters));
		case "VARIABLE":
			myFurthestDepth++;
			Instruction tempInt = new Variable(command[myFurthestDepth - 1], myExecutionParameters);
			myExecutionParameters.addObserver(tempInt);
			return new Node(tempInt);
		case "COMMAND":
			myFurthestDepth++;
			if(myExecutionParameters.getUserCommandMap().containsKey(command[myFurthestDepth-1])&&(myFurthestDepth<2||myFurthestDepth>=2&&testMatches(command[myFurthestDepth-2]).toUpperCase()!="MAKEUSERINSTRUCTION")){
				myNode = new Node(new UserRunningInstruction(futureInstructions, command[myFurthestDepth-1], myViewUpdater, myModule, myExecutionParameters));
				System.out.println(" Node "+ myNode);
				neededVars = 1;
			}
			else{
				myExecutionParameters.addCommand(command[myFurthestDepth-1], null);
				
				return new Node(new StringInstruction(command[myFurthestDepth-1], myExecutionParameters));
			}
			break;
		case "LISTEND":
			myFurthestDepth++;
			return null;
		default:
		}
			//this is either a known command or invalid input.  
			//instantiate the command, if reflection cannot find the file then must be invalid
		if(myNode==null){
					Instruction myInt;
					try {
					myInt = Class.forName("model.instructions."+myCommandMap.get(match)).asSubclass(Instruction.class).getConstructor(new Class[]{List.class,String.class,ViewUpdater.class,ViewUpdateModule.class,ExecutionEnvironment.class}).newInstance(new Object[]{futureInstructions, match,myViewUpdater, myModule,myExecutionParameters});
					myFurthestDepth++;
					myExecutionParameters.addObserver(myInt);
					myNode = new Node(myInt);
					neededVars = myInt.getNumberOfArguments();
					}
					catch (InstantiationException | IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException
								| SecurityException | ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		}
		while(myVars<neededVars){
			Node level = makeTree(command);
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
	
	private String testMatches(String test) {
		if (test.trim().length() > 0) {
			for (Entry<String, Pattern> p : myPatterns) {
				if (match(test, p.getValue())) {
					return p.getKey();
				}
			}
		}
		return "NONE";
	}

	public void setLanguage(String language){
		myPatterns = new ArrayList<>();
		addAllPatterns(language);
	}

	@Override
	public void update(Observable o, Object arg) {
		myExecutionParameters = (ExecutionEnvironment) o;
	}

	public ViewUpdateModule getModule() {
		// TODO Auto-generated method stub
		return myModule;
	}

}
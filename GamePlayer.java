import game.Game;												 //import the required library for game
//import game.Location;											//Supposedly this is the .Game's equivalence to our Position
import game.exception.GamePlayException;						//needed to throw in method
import game.exception.InvalidGameActionException;				//needed to throw in method
import game.exception.InvalidGameMoveException;					//needed to throw in method
import java.util.Stack;   										 //using stacks for DFS strategy to game player
import java.util.HashMap;										//setting up gameMap internally
import java.util.LinkedList;									 //using linked list for internal memory
import java.util.Map;											//needed for our Hashmap

public class GamePlayer {
	static class Position {										//setting the constructor
		int x, y;
		Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		public boolean equals(Object obj) {
			if (this == obj) return true;  									//same reference
			if (obj == null || getClass() != obj.getClass()) return false;  // Check type
			Position other = (Position) obj;
			return this.x == other.x && this.y == other.y;  				//check if x and y are equal
		}
		public String toString() {								//giving the output more readability
			return "(" + x + ", " + y + ")";
		}
	}
	
	static Stack<Position> stack = new Stack<>();					//This is how we are travelling
	static LinkedList<Position> visited = new LinkedList<>();		//keeps track where we have travel & where we havent
	static LinkedList<Position> obstacles = new LinkedList<>(); 	//Will delete later once final code is complete
	static Map<String, LinkedList<Position>> gameMap = new HashMap<>();	//Setting a string to 
	
	private static int x, y;  									//x= rows, y= columns
	
	private static void addObstacles(int x, int y) {		//Method to add obstacle, adjust once final code is done
		Position pos = new Position(x, y);
		if (!gameMap.containsKey("Obstacle")) {				//Creating key for hashmap if not created already
			gameMap.put("Obstacle", new LinkedList<>());
		}
		if (!obstacles.contains(pos)) {						//Adding a new obstacle if not already added
			obstacles.add(pos);								//Adding to LinkedList(for now)
			gameMap.get("Obstacle").add(pos);				//Adding to HashMap
		}
	}
	
	private static void moveDown() {                            //AI will move down if it's allowed
		try {
			Game.moveDown();
			++x;												//must change the value before other tasks
			stack.push(new Position(x, y));						//updating our posistion + memory
			visited.add(new Position(x,y));
			System.out.println("location is (" + x + ", " + y + ")" );
		} 
		catch (Exception e) {									//When we find an game exception we add it to gameMap
			System.out.println("Can't move there, finding another way around.");
			addObstacles(x + 1, y);						//adding to gameMap/Obstacle(linkedList) (will fix for final code)
		}
	}
	private static void moveUp() { 								//AI will move down if it's allowed
		try {
			Game.moveUp();
			--x;												//must change the value before other tasks
			stack.push(new Position(x, y));						//updating our posistion + memory
			visited.add(new Position(x,y));
			System.out.println("location is (" + x + ", " + y + ")" );
		} catch (Exception e) {									//When we find an game exception we add it to gameMap
			System.out.println("Can't move there, finding another way around.");
			addObstacles(x - 1, y);						//adding to gameMap/Obstacle(linkedList) (will fix for final code)
		}
	}
	private static void moveRight() { 							//AI will move down if it's allowed
		try {
			Game.moveRight();
			++y;												//must change the value before other tasks
			stack.push(new Position(x, y));						//updating our posistion + memory
			visited.add(new Position(x,y));
			System.out.println("location is (" + x + ", " + y + ")" );
		} catch (Exception e) {									//When we find an game exception we add it to gameMap
			System.out.println("Can't move there, finding another way around.");
			addObstacles(x, y + 1);						//adding to gameMap/Obstacle(linkedList) (will fix for final code)
		}
	}
	private static void moveLeft() { 							//AI will move down if it's allowed
		try {
			Game.moveLeft();
			--y;												//must change the value before other tasks
			stack.push(new Position(x, y));						//updating our posistion + memory
			visited.add(new Position(x,y));
			System.out.println("location is (" + x + ", " + y + ")" );
		} catch (Exception e) {									//When we find an game exception we add it to gameMap
			System.out.println("Can't move there, finding another way around.");
			addObstacles(x, y - 1);						//adding to gameMap/Obstacle(linkedList) (will fix for final code)
		}

	}

	private static boolean isObstacle(int x, int y) {
		return obstacles.contains(new Position(x, y));
	}
	private static boolean isVisited(int x, int y){				//boolean to check if it is visited
		return visited.contains(new Position(x, y));
		}
	
	private static boolean canMoveDown() throws GamePlayException { 						//AI will check if it is able to move down
		return ((x < Game.getRows() -1) && !isVisited(x+1, y) && !isObstacle(x+1, y));	
	}	
	private static boolean canMoveRight() throws GamePlayException { 					//AI will check if it is able to move right
		return ((y < Game.getColumns() - 1) && !isVisited(x, y+1) && !isObstacle(x, y+1));
	}
	private static boolean canMoveUp() {						//AI will check if it is able to move up	
		return (x > 0 && !isVisited(x-1, y) && !isObstacle(x-1, y));
	}
	private static boolean canMoveLeft() {						//AI will check if it is able to move left
		return (y > 0 && !isVisited(x, y-1) && !isObstacle(x, y-1));
	}
	private static void backtrack() {						//Starting a method to backtrack and 
		if (!stack.empty()) {								//ensuring the maps allign internally & externally
			try {									//Trying incase we run into a obstacle
				Position backtrack = stack.pop();		//assigning the lastest pos to backtrack
				while (!Game.getLocation().toString().equals(backtrack.toString())) {	//loop to make sure we are alligned
					if (x > backtrack.x) {
						Game.moveUp();	
					} else if (x < backtrack.x) {
						Game.moveDown();
					} else if (y > backtrack.y) {
						Game.moveLeft();
					} else if (y < backtrack.y) {
						Game.moveRight();
					}
				}
				x = backtrack.x;					
				y = backtrack.y;
				
				System.out.println("Backtracking from (" + x + ", " + y + ")"); //annoucing for debugging
				
			} catch (Exception e) {
				System.out.println("Error:" + e.getMessage()); //I saw professor use the getmessage which
			}													// displays what the error message is
		}
		else {
			System.out.println("Stack is empty");
		}
	}
	
	private static void shovelToTreasure() throws GamePlayException, InvalidGameActionException, InvalidGameMoveException {
	    // Retrieve positions of shovel and treasure
	    LinkedList<Position> shovelList = gameMap.get("Shovel");
	    LinkedList<Position> treasureList = gameMap.get("Treasure");

	    if (shovelList == null || treasureList == null || shovelList.isEmpty() || treasureList.isEmpty()) {
	        System.out.println("Shovel or Treasure positions are missing in the game map.");
	        return;
	    }

	    Position shovelPosition = shovelList.getFirst();
	    Position treasurePosition = treasureList.getFirst();

	    int shovelX = shovelPosition.x;
	    int shovelY = shovelPosition.y;
	    int treasureX = treasurePosition.x;
	    int treasureY = treasurePosition.y;

	    System.out.println("Moving from shovel at (" + shovelX + ", " + shovelY + ") to treasure at (" + treasureX + ", " + treasureY + ")");

	    // Align current position with the shovel's position if necessary
	    while (x != shovelX || y != shovelY) {
	        if (x < shovelX) moveDown();
	        else if (x > shovelX) moveUp();
	        else if (y < shovelY) moveRight();
	        else if (y > shovelY) moveLeft();
	    }

	    // Move from shovel to treasure
	    while (x != treasureX || y != treasureY) {
	        if (x < treasureX && canMoveDown()) moveDown();
	        else if (x > treasureX && canMoveUp()) moveUp();
	        else if (y < treasureY && canMoveRight()) moveRight();
	        else if (y > treasureY && canMoveLeft()) moveLeft();
	        else { // Handle obstacles or invalid moves
	            System.out.println("Obstacle or no valid move. Backtracking...");
	            backtrack();
	        }
	    }

	    System.out.println("Reached the treasure at (" + treasureX + ", " + treasureY + ")");
	    System.out.println(Game.hasShovel());
	    Game.digTreasure();
	}
	
	private static void explore() throws GamePlayException, InvalidGameActionException, InvalidGameMoveException {//Main AI function of how it is getting around
		if (Game.hasShovel()) {

			Game.pickShovel();
			try { 
				Game.pickShovel();
			}
			catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}
			System.out.println("At location (" + x + ", " + y + "), hasShovel: " + Game.hasShovel());

			gameMap.put("Shovel", new LinkedList<>());								//When we find shovel, create a new key 
			gameMap.get("Shovel").add(new Position(x, y));							//and store the location as the value
			System.out.println("Found the shovel at (" + x + ", " + y + ")");		//Found at (3, 6)
			System.out.println(Game.hasShovel());
			if (gameMap.containsKey("Treasure")) {
				shovelToTreasure();
				Game.digTreasure();
				return;
			}
		}
		if (Game.hasTreasure()) {
			gameMap.put("Treasure", new LinkedList<>());						//When we find treasure, create a new key
			gameMap.get("Treasure").add(new Position(x, y));					//and store the location as the value
			System.out.println("Found the treasure at (" + x + ", " + y + ")");    //Found at (6, 2)
			if (Game.hasShovel()) {										//incase we have the shovel, we finish the game
				Game.digTreasure();
			}
		}			//**************************************************************************************************\\
		if (canMoveDown()) {				//this is not DFS, we must figure out a way to properly implement it 
			moveDown();						//our program goes in a circle
		} 			//**************************************************************************************************\\
		else if (canMoveRight()) {
			moveRight();
		}
		else if (canMoveUp()) {
			moveUp();
		}
		else if (canMoveLeft()) {
			moveLeft();
		}
		else {
			backtrack();
			System.out.println(Game.getLocation());
    }
		if (stack.size() < 31) {			//this is used for debugging, currently setting it 32 will crash the program
		 explore();							//looping recursion for now since we don't have a method for shovel to treasure
		 } else {
			 return;
		 }
	} 
		
	public static void main(String[] args) throws GamePlayException, InvalidGameActionException, InvalidGameMoveException {
		Game.play();					//starting the game
		explore();						//calling our AI program to execute
		while (!stack.isEmpty()) {			//Debugging purpose
			System.out.println(stack.pop());
		}
		if (!Game.hasTreasure()) {				//Debugging purpose
			System.out.println();
			System.out.println("Could not complete task.");	
		}
		System.out.println(obstacles);				//Debugging purpose
		System.out.println(gameMap);				//Debugging purpose
		Game.quit();								//Quiting the game 
	} 
}
/*
 * 	Things to figure out
 * 		1. Properly implementing DFS 
 * 		2. Setting a method to go to treasure if we find it first and then the shovel 
 */








package TreasureIslandGame;
import game.Game;												 //import the required library for game
import game.exception.GamePlayException;
import game.exception.InvalidGameActionException;
import java.util.Stack;   										 //using stacks for DFS strategy to game player
import java.util.LinkedList;									 //using linked list for internal memory

public class GamePlayer {
	static class Position {										//setting the constructor
		int x, y;
		Position(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	
	static Stack<Position> stack = new Stack<>();
	static LinkedList<Position> visited = new LinkedList<>();
	
	private static int x, y;  									//x= rows, y= columns
	

	private static void moveDown() {                            //AI will move down if it's allowed
		stack.push(new Position(x, y));
		System.out.println("Moving down to (" + ++x + ", " + y + ")");
		visited.add(new Position(x,y));
	}
	private static void moveUp() { 	    //AI will move down if it's allowed
		stack.pop();
		System.out.println("Moving up to (" + --x + ", " + y + ")");
		//visited.add(new Position(x,y));
	}
	private static void moveRight() { 							//AI will move down if it's allowed
		stack.push(new Position(x, y));
		System.out.println("Moving right to (" + x + ", " + ++y + ")");
		visited.add(new Position(x,y));
	}
	private static void moveLeft() {      //AI will move down if it's allowed
		stack.pop();
		System.out.println("Moving left to (" + x + ", " + --y + ")");
		//visited.add(new Position(x,y));
	}
	
	 private static boolean isVisited(int x, int y){				//boolean to check if it is visited
		return visited.contains(new Position(x, y));
	}
	 
	 private static boolean canMoveDown() throws GamePlayException, InvalidGameActionException { 						//AI will check if it is able to move down
		 if (x + 1 < Game.getRows() && !isVisited(x + 1, y)) {                                                          // checks if the move is valid by calling on to the library's method
			 return true;
		 }
		 
		 return false;
	}
	
	private static boolean canMoveRight() throws GamePlayException, InvalidGameActionException { 					//AI will check if it is able to move right
		if (y + 1 < Game.getColumns() && isVisited(x, y + 1)) {                                                     // checks if the move is valid by calling on to the library's method
			return true;
		}
		
		return false;
	}
	
	private static boolean canMoveUp() throws GamePlayException, InvalidGameActionException {						//AI will check if it is able to move up	
		if (x - 1 >= 0 && !isVisited(x - 1, y)) {
			return true;
		}
		
		return false;
	}
	private static boolean canMoveLeft() throws GamePlayException, InvalidGameActionException {	 //AI will check if it is able to move left
		if (y - 1 >= 0 && !isVisited(x, y - 1)) {
			return true;
		}
		
		return false;
		
	} 

	
	
	private static void explore() throws GamePlayException, InvalidGameActionException {//Main AI function of how it is getting around
		if (Game.hasShovel()) {
			Game.pickShovel();
			System.out.println("Found the shovel at (" + x + ", " + y + ")");
			
		}
		if (Game.hasTreasure()) {
			System.out.println("Found the treasure at (" + x + ", " + y + ")");
			Game.digTreasure();
		}
		
		 if (canMoveDown()) {
			 moveDown();
		}
		 
		else if (canMoveRight()) {
			moveRight();	
		}
		else if (canMoveUp()) {
			moveUp();
		}
		else if (canMoveLeft()) {
			moveLeft();
		}
		 
	}
		
		
		/* if (!Game.hasShovel() && !Game.hasTreasure()) {
			 explore();
		 } else return;
		 */
		
	public static void main(String[] args) throws GamePlayException, InvalidGameActionException {
		Game.play();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		explore();
		
		
		
		if (!Game.hasTreasure()) {
			System.out.println();
			System.out.println("Could not complete task.");
		}
	} 
}

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
	private static void moveUp() { 								//AI will move down if it's allowed
		stack.pop();
		System.out.println("Moving up to (" + --x + ", " + y + ")");
		visited.add(new Position(x,y));
	}
	private static void moveRight() { 							//AI will move down if it's allowed
		stack.push(new Position(x, y));
		System.out.println("Moving right to (" + x + ", " + ++y + ")");
		visited.add(new Position(x,y));
	}
	private static void moveLeft() { 							//AI will move down if it's allowed
		stack.pop();
		System.out.println("Moving left to (" + x + ", " + --y + ")");
		//visited.add(new Position(x,y));
	}
	
	private static boolean isVisited(int x, int y){				//boolean to check if it is visited
		return visited.contains(new Position(x, y));
	}
	
	private static boolean canMoveDown() { 						//AI will check if it is able to move down
		return ((x < 6) && !isVisited(x+1, y));	
	}	
	private static boolean canMoveRight() { 					//AI will check if it is able to move right
		return ((y < 6) && !isVisited(x, y+1));
	}
	private static boolean canMoveUp() {						//AI will check if it is able to move up	
		return (x > 0 && !isVisited(x-1, y));
	}
	private static boolean canMoveLeft() {						//AI will check if it is able to move left
		return (y > 0 && !isVisited(x, y-1));
	}
	
	
	private static void explore() throws GamePlayException, InvalidGameActionException {				//Main AI function of how it is getting around
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
			 if (!Game.hasShovel() && !Game.hasTreasure()) {
				 explore();
			 }
		}
		else if (canMoveRight()) {
			moveRight();
			 if (!Game.hasShovel() && !Game.hasTreasure()) {
				 explore();
			 }
		}
		else if (canMoveUp()) {
			moveUp();
			 if (!Game.hasShovel() && !Game.hasTreasure()) {
				 explore();
			 }
		}
		else if (canMoveLeft()) {
			moveLeft();
			 if (!Game.hasShovel() && !Game.hasTreasure()) {
				 explore();
			 }
		}
	}
		
	public static void main(String[] args) throws GamePlayException, InvalidGameActionException {
		Game.play();
		explore();
		
		if (!Game.hasTreasure()) {
			System.out.println();
			System.out.println("Could not complete task.");
		}
	} 
}

		/*
		create a AI game player that will play the game
		use a stack to do DFS, and going around the obstacles but storing the information
		finding the shovel first and finding the treasure chest
		Testing to perfect the program to be able to do various maps.
		
		
		at every space check if the space has the shovel with the method call
		 */


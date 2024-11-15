import game.Game;												 //import the required library for game
import game.exception.GamePlayException;
import game.exception.InvalidGameActionException;
import game.exception.InvalidGameMoveException;
import java.util.Stack;   										 //using stacks for DFS strategy to game player
import java.util.LinkedList;									 //using linked list for internal memory

public class GamePlayer {
	static class Position {										//setting the constructor
		int x, y;
		Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		public boolean equals(Object obj) {
			if (this == obj) return true;  						//same reference
			if (obj == null || getClass() != obj.getClass()) return false;  // Check type
			Position other = (Position) obj;
			return this.x == other.x && this.y == other.y;  	//check if x and y are equal
		}
		public String toString() {								//giving the output more readability
			return "(" + x + ", " + y + ")";
		}
	}
	
	static Stack<Position> stack = new Stack<>();
	static LinkedList<Position> visited = new LinkedList<>();
	
	private static int x, y;  									//x= rows, y= columns
	
	private static void moveDown() {                            //AI will move down if it's allowed
		stack.push(new Position(x, y));
		visited.add(new Position(x,y));
		++x;
	}
	private static void moveUp() { 								//AI will move down if it's allowed
		stack.push(new Position(x, y));
		visited.add(new Position(x,y));
		--x;
	}
	private static void moveRight() { 							//AI will move down if it's allowed
		stack.push(new Position(x, y));
		visited.add(new Position(x,y));
		++y;	
	}
	private static void moveLeft() { 							//AI will move down if it's allowed
		stack.push(new Position(x, y));
		visited.add(new Position(x,y));
		--y;
	}
	
	private static boolean isVisited(int x, int y){				//boolean to check if it is visited
		return visited.contains(new Position(x, y));			//uses an equals method to compare
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
	
	
	private static void explore() throws GamePlayException, InvalidGameActionException, InvalidGameMoveException {//Main AI function of how it is getting around
		if (Game.hasShovel()) {
			System.out.println("Found the shovel at (" + x + ", " + y + ")");
			Game.pickShovel();
		}
		if (Game.hasTreasure()) {
			System.out.println("Found the treasure at (" + x + ", " + y + ")");    //Found at (6, 2)
			if (Game.hasShovel()) {
				Game.digTreasure();
			}
		}
		try {
			if (canMoveDown()) {
				moveDown();
				Game.moveDown();
			}
			else if (canMoveRight()) {
				moveRight();
				Game.moveRight();
			}
			else if (canMoveUp()) {
				moveUp();
				Game.moveUp();
			}
			else if (canMoveLeft()) {
				moveLeft();
				Game.moveLeft();
			}
		} catch (Exception e) {
			System.out.println("Can't move there, finding a away around.");
		}
		if (!Game.hasShovel() && stack.size() < 48) {
		 explore();
		 } else {
			 return;
		 }
	} 
		
	public static void main(String[] args) throws GamePlayException, InvalidGameActionException, InvalidGameMoveException {
		Game.play();	
		explore();
		while (!stack.isEmpty()) {
			System.out.println(stack.pop());
		}
		
		if (!Game.hasTreasure()) {
			System.out.println();
			System.out.println("Could not complete task.");	
		}
		
		Game.quit();
	} 
}


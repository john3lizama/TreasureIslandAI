package TreasureIslandGame;
import game.Game;												 //import the required library for game
import game.exception.GamePlayException;
import game.exception.InvalidGameActionException;
import game.exception.InvalidGameMoveException;
import java.util.Stack;                                          //using stacks for DFS strategy to game player
import java.util.HashSet; 
import java.util.Objects;

public class GamePlayer {
	static class Position {										//setting the constructor
		int x, y;
		Position(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		@Override                                              // with a hashSet the equals() method needs to be overridden 
                                                               // so it checks the actual value of the variables, instead of object reference     
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Position position = (Position) o;
			return x == position.x && y == position.y;

		}

		@Override                                             // computes a hashCode for the given coordinates
		public int hashCode() {
			return Objects.hash(x, y);

		}
	}
	
	static Stack<Position> stack = new Stack<>();
	static HashSet<Position> visited = new HashSet<>();
	
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
	
	private static boolean isMoundTile(int x, int y) {         // need to implement this method to check for obstacles
		return false;
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
				return;
			}
		}
			if (canMoveDown()) {
				moveDown();
				Game.moveDown();
				explore();
			}
			else if (canMoveRight()) {
				moveRight();
				Game.moveRight();
				explore();
			}
			else if (canMoveUp()) {
				moveUp();
				Game.moveUp();
				explore();
			}
			else if (canMoveLeft()) {
				moveLeft();
				Game.moveLeft();
				explore();
			}
			
	} 
		
	public static void main(String[] args) throws GamePlayException, InvalidGameActionException, InvalidGameMoveException {
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
		explore();
		
		if (!Game.hasTreasure()) {
			System.out.println();
			System.out.println("Could not complete task.");	
		}
		
		Game.quit();
	} 
}


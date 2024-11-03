import java.util.Random;
//import game.Game;
import java.util.Stack;   										 //using stacks for DFS strategy to game player
import java.util.LinkedList;									 //using linked list for internal memory

public class GamePlayer {
	private static int[][] map; 								//internal map that will be updated as we move
	private static boolean[][] visited; 						//internal map that will be updated as we move
	private static int x, y;  									//x= columns, y= rows
	

	private static void moveDown() {							 //AI will move down if it's allowed
		System.out.println("Exploring down at (" + ++x + ", " + y + ")");
		visited[x][y] = true;
	}
	private static void moveUp() { 								//AI will move down if it's allowed
		System.out.println("Moving back up to (" + --x + ", " + y + ")");
		visited[x][y] = true;
	}
	private static void moveRight() { 							//AI will move down if it's allowed
		System.out.println("Exploring right at (" + x + ", " + ++y + ")");
		visited[x][y] = true;
	}
	private static void moveLeft() { 							//AI will move down if it's allowed
		System.out.println("Moving back left to (" + x + ", " + --y + ")");
		visited[x][y] = true;
	}
	
	private static boolean canMoveDown() { 						//AI will check if it is able to move down
		if ((x < 6) && !visited[x+1][y]) {
			return true;
		}
		else {
			return false;
		}
	}
	private static boolean canMoveRight() { 					//AI will check if it is able to move right
		if ((y < 6) && !visited[x][y+1]) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static boolean exploreDown() { 			//AI will decide where to move depending on if it is able to move down
		if (canMoveDown()) {
			moveDown();
			if (map[x][y] == 1) {
				System.out.println("Found it at (" + x + ", " + y + ")");
				return true;
			}
			else {
				boolean result = exploreDown();
				if (result) {
					return result;
				}
				else {
					result = exploreRight();
					if (!result) {
						moveUp();
					}
					return result;
				}
			}
		}
		return false;	
	}
	
	private static boolean exploreRight() { 		//AI will decide where to move depending on if it is able to move right
		if (canMoveRight()) {
			moveRight();
			if (map[x][y] == 1) {
				System.out.println("Found it at (" + x + ", " + y + ")");
				return true;
			}
			else {
				boolean result = exploreDown();
				if (result) {
					return result;
				}
				else {
					result = exploreRight();
					if (!result) {
						moveLeft();
					}
					return result;
				}
			}
		}
		return false;
	}
	
	private static void findTheValue( ) {
		System.out.println("Starting at (" + x + ", " + y + ")");
		visited[x][y] = true;
		if (map[x][y] == 1) {
			System.out.println("Found it at (" + x + ", " + y + ")");
			return;
		}
		boolean result = exploreDown();
		if (!result) {
			exploreRight();
		}
	}
	public static void main(String[] args) {
		map = new int[7][7];
		visited = new boolean[7][7];
		Random generator = new Random();
		map[generator.nextInt(0, 7)][generator.nextInt(0, 7)] = 1;
		
		x = 0;
		y = 0;
		
		findTheValue();
	}
}

		/*
		create a AI game player that will play the game
		use a stack to do DFS, and going around the obstacles but storing the information
		finding the shovel first and finding the treasure chest
		Testing to perfect the program to be able to do various maps.
		 */



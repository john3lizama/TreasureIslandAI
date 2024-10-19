import java.util.Random;
//import game.Game;

public class GamePlayer {
	private static int[][] map;
	private static boolean[][] visited;
	private static int x, y;
	
	private static boolean canMoveDown() {
		if ((x < 6) && !visited[x+1][y]) {
			return true;
		}
		else {
			return false;
		}
	}
	private static boolean canMoveRight() {
		if ((y < 6) && !visited[x][y+1]) {
			return true;
		}
		else {
			return false;
		}
	}
	private static void moveDown() {
		System.out.println("Exploring down at (" + ++x + ", " + y + ")");
		visited[x][y] = true;
	}
	private static void moveUp() {
		System.out.println("Moving back up to (" + --x + ", " + y + ")");
		visited[x][y] = true;
	}
	private static void moveRight() {
		System.out.println("Exploring right at (" + x + ", " + ++y + ")");
		visited[x][y] = true;
	}
	private static void moveLeft() {
		System.out.println("Moving back left to (" + x + ", " + --y + ")");
		visited[x][y] = true;
	}
	private static boolean exploreDown() {
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
	private static boolean exploreRight() {
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

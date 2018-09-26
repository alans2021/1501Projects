import java.util.*;
import java.io.*;
import java.lang.*;

public class Crossword{
	
	public static void main(String[]args) throws IOException{
		DictInterface dict = new MyDictionary();
		Scanner dictScan = new Scanner(new FileInputStream("dict8.txt"));
		while (dictScan.hasNext()){
			String word = dictScan.nextLine();
			dict.add(word);
		}

		// Create Board object
		String board = "test6a.txt";
		Scanner boardScan = new Scanner(new FileInputStream(board));
		int size = Integer.parseInt( boardScan.nextLine() );
		Board crossword = new Board(size, boardScan, dict);

		crossword.initBoard();
		crossword.solveBoard(0, 0);
		crossword.printSolution();
		


	}
}
import java.util.*;
import java.io.*;
import java.lang.*;

public class Crossword{
	
	public static void main(String[]args) throws IOException{
		DictInterface dict = new MyDictionary();
		DictInterface DLB = new DLB();
		Scanner dictScan = new Scanner(new FileInputStream("dict8.txt"));
		while (dictScan.hasNext()){
			String word = dictScan.nextLine();
			dict.add(word);
			DLB.add(word);
		}

		// Create Board object
		String board = "test8c.txt";
		Scanner boardScan = new Scanner(new FileInputStream(board));
		int size = Integer.parseInt( boardScan.nextLine() );
		
//		Board crossword = new Board(size, boardScan, dict);
//		System.out.println("Solving board " + board + " using MyDictionary implementation");
//		crossword.initBoard();
//		crossword.solveBoard(0, 0);
//		crossword.printSolution(true);
		
		Board crosswordAll = new Board(size, boardScan, DLB);
		System.out.println("Solving board " + board + " using DLB implementation");
		crosswordAll.initBoard();
		crosswordAll.solveBoard(0, 0);
		crosswordAll.printSolution(true);

	}
}
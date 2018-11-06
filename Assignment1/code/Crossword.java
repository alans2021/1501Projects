import java.util.*;
import java.io.*;
import java.lang.*;

public class Crossword{
	
	public static void main(String[]args) throws IOException{
		String dictType = args[0];
		DictInterface dict;
		if(dictType.equals("DLB"))
			dict = new DLB();
		else
			dict = new MyDictionary();
		Scanner dictScan = new Scanner(new FileInputStream("dict8.txt"));
		while (dictScan.hasNext()){
			String word = dictScan.nextLine();
			dict.add(word);
		}

		// Create Board object
		String board = args[1];
		Scanner boardScan = new Scanner(new FileInputStream(board));
		int size = Integer.parseInt( boardScan.nextLine() );
		
		Board crossword = new Board(size, boardScan, dict, dictType);
		if (dictType.equals("DLB"))
			System.out.println("Solving board " + board + " using DLB implementation");
		else
			System.out.println("Solving board " + board + " using MyDictionary implementation");
		crossword.initBoard();
		crossword.solveBoard(0, 0);
		if (dictType.equals("DLB"))
			System.out.println(crossword.getSol() + " Solutions found");
		else
			crossword.printSolution();

	}
}
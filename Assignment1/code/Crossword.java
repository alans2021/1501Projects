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

		String board = args[0];
		Scanner boardScan = new Scanner(new FileInputStream(board));
		String test = boardScan.nextLine();
		int size = Integer.parseInt(test);
		String[][] Crossword = new String[size][size];

		for (int i = 0; i < size; i++){
			String line = boardScan.nextLine();
			for (int j = 0; j < size; j++){
				String charac = line.substring(j, j + 1);
				if (charac.equals("+"))
					Crossword[i][j] = "+";
				else if (charac.equals("-"))
					Crossword[i][j] = "-";
				else
					Crossword[i][j] = charac;
			}

		}

		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++)
				System.out.print(Crossword[i][j]);
			System.out.println();
		}


	}
}
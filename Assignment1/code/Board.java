import java.util.*;
import java.lang.*;
import java.io.*;

public class Board{
	private int size;
	private String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
	private Scanner inputs;
	private DictInterface dict;
	private String[][] solution;
	private StringBuilder[] colStr;
	private StringBuilder[] rowStr;

	public Board(int n, Scanner sc, DictInterface d){
		size = n;
		inputs = sc;
		dict = d;
		solution = new String[size][size];
		colStr = new StringBuilder[size];
		rowStr = new StringBuilder[size];
		for (int i = 0; i < size; i++){
			rowStr[i] = new StringBuilder();
			colStr[i] = new StringBuilder();
		}
	}

	// Fill board with right characters based on information in file
	public void initBoard(){
		for (int i = 0; i < size; i++){
			String line = inputs.nextLine();
			for (int j = 0; j < size; j++){
				String charac = line.substring(j, j + 1);
				solution[i][j] = charac;
			}

		}
	}

	
	// Recursive method: Returns when board filled correctly
	public void solveBoard(int row, int col){
	
		for (int i = 0; i < letters.length; i++){
			String letter = letters[i];
			
			if(valid(row, col, letter)){
				rowStr[row].append(letter);
				colStr[col].append(letter);
				solution[row][col] = letter;
				
				if (row >= size - 1 && col >= size - 1)
					printSolution();
				else{
					if (col >= size - 1){ // Move to next row if all columns of one row filled
						//printSolution();
						solveBoard(row + 1, 0);
					}
					else{
						//printSolution();
						solveBoard(row, col + 1);
					}
				}
				
				rowStr[row].deleteCharAt(rowStr[row].length() - 1);
				colStr[col].deleteCharAt(colStr[col].length() - 1);
				solution[row][col] = "";
			}
		}
		if (row == 0 && col == 0)
			System.out.println("No Solution found");
	
	}
	
	//Checks to make sure that letter added to that square is valid
	private boolean valid(int row, int col, String letter){
		StringBuilder rowString = new StringBuilder(rowStr[row]);
		StringBuilder colString = new StringBuilder(colStr[col]);
		rowString.append(letter);
		colString.append(letter);
		
		int rowCondition = dict.searchPrefix(rowString);
		int colCondition = dict.searchPrefix(colString);
		

		if(rowString.equals("aba") && colString.equals("aba")){
			System.out.println("It exists");
			System.out.println("Rowcond: " + rowCondition + "Colcond: " + colCondition);
		}
		
		if (rowCondition < 2 || colCondition < 2){
			if (rowCondition == 0 || colCondition == 0)
				return false;
			if (rowCondition == 1 && col == size - 1)
				return false;
			if (colCondition == 1 && row == size - 1)
				return false;
		}
		return true;
		
	}
	

	// Print out initial board
	public void printBoard(){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++)
				System.out.print(solution[i][j]);
			System.out.println();
		}
	}
	
	public void printSolution(){
		System.out.println("Solution Found: ");
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++)
				System.out.print(solution[i][j] + "\t");
			System.out.println();
		}
	}
}
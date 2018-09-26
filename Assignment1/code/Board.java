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
	private ArrayList<Integer> rowFill = new ArrayList<Integer>();
	private ArrayList<Integer> colFill = new ArrayList<Integer>();;
	private boolean solved;

	public Board(int n, Scanner sc, DictInterface d){
		size = n;
		inputs = sc;
		dict = d;
		solved = false;
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
				if (charac.equals("-")){
					rowFill.add(i);
					colFill.add(j);
				}
			}

		}
	}

	
	// Recursive method: Returns when board filled correctly
	public void solveBoard(int row, int col){
		boolean isFixed = false; //Determines if square in board is fixed or not
		if (row >= size){ //Indicates solved crossword
			solved = true;
			return;
		}
		
		for (int i = 0; i < letters.length; i++){
			String letter;
			if (!solution[row][col].equals("+")){ //If not "+", then letter is pre-determined
				isFixed = true;
				letter = solution[row][col];
			}
			else
				letter = letters[i];
			
			if(valid(row, col, letter)){ //Check if that letter still leads to valid crossword
				rowStr[row].append(letter); // If so, modify crossword by adding letter
				colStr[col].append(letter);
				solution[row][col] = letter;
				
				if (col >= size - 1){ // Move to next row if all columns of one row filled
					printSolution();
					solveBoard(row + 1, 0);
				}
				else{
					printSolution();
					solveBoard(row, col + 1);
				}

				if(!solved){ //Only do this if puzzle has not been solved yet
					rowStr[row].deleteCharAt(rowStr[row].length() - 1); //Delete letter
					colStr[col].deleteCharAt(colStr[col].length() - 1); //Delete letter
					if(!isFixed) //If square not fixed, revert it back to '+' form
						solution[row][col] = "+";
					else
						break; //Break out of for loop if fixed square
				}
				else
					return;
			}
			else{
				if(isFixed) //Break out of for loop if it's a fixed letter since it can't change anyway
					break;
			}
		}
		if (row == 0 && col == 0)
			System.out.println("No Solution found");
	
	}
	
	//Checks to make sure that letter added to that square is valid
	private boolean valid(int row, int col, String letter){
		boolean mustWordC = false;
		boolean mustWordR = false;
		for(int i = 0; i < colFill.size(); i++){ //Check through all squares that are filled
			if (col == colFill.get(i)){ //If col variable matches with a column index that is filled
				if (row == rowFill.get(i)) // Return true if that row and column combo is filled
					return true;
				if (rowFill.get(i) - row == 1) //If the string at that column now goes up to the filled square, 
					mustWordC = true;				//That column string must be a word 
			}
			else if (row == rowFill.get(i)){
				if (colFill.get(i) - col == 1) //If the string at that row now goes up to the filled square,
					mustWordR = true;				//That row string must be a word
			}
		}
		StringBuilder rowString = new StringBuilder(rowStr[row]);
		StringBuilder colString = new StringBuilder(colStr[col]);
		rowString.append(letter);
		colString.append(letter);
		if (rowString.indexOf("-") != -1) //Remove substring before '-', those don't matter
			rowString = rowString.delete(0, rowString.indexOf("-") + 1);
		if (colString.indexOf("-") != -1) //Remove substring before '-', those don't matter
			colString = colString.delete(0, colString.indexOf("-") + 1);
		
		int rowCondition = dict.searchPrefix(rowString);
		int colCondition = dict.searchPrefix(colString);
		
		if (rowCondition < 2 || colCondition < 2){
			if (rowCondition == 0 || colCondition == 0)
				return false;
			if (rowCondition == 1 && (col == size - 1 || mustWordR)) //Can't be prefix if it has to be a word
				return false;
			if (colCondition == 1 && (row == size - 1 || mustWordC)) //Can't be prefix if it has to be a word
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
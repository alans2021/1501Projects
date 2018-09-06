public class Crossword{
	
	public static void main(String[]args){
		DictInterface dict = new MyDictionary();
		Scanner dictScan = new Scanner(new FileInputStream("dict8.txt"));
		while (dictScan.hasNext()){
			word = dictScan.nextLine();
			dict.add(word);
		}

		String board = args[0];
		Scanner boardScan = new Scanner(new FileInputStream(board));		
	}
}
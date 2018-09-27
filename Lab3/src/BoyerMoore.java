public class BoyerMoore
{
	//possible characters: A-Za-z and ' '
	//ignore casing
	//you should be printing each step the algorithm is taking
	//peforms a search of the pattern in the text between the startPos and endPos
	public static int find(String text, String pattern, final int startPos, final int endPos)
	{
		//edge cases

		//naive search only for one character (special case)
		
		//Actual Boyer-Moore Algorithm
		//develop skip tables based on the pattern
		
		//use the tables
		
	}

	//creates the table of spaces to skip if there was a bad character match
	public static int[] badCharacterGenerator()
	{
		
	}

	//creates the table of spaces to skip if there was a good suffix match
	public static int[] goodSuffixGenerator()
	{
		
	}

	public static void main(String[] args)
	{
		//test cases
	}
}
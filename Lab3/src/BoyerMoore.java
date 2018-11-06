public class BoyerMoore
{
	private static char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' '};
	//possible characters: A-Z, a-z and ' '
	//ignore casing
	//you should be printing each step the algorithm is taking
	//peforms a search of the pattern in the text between the startPos and endPos
	public static int find(String text, String pattern, final int startPos, final int endPos)
	{
		//edge cases
		if(text == null || pattern == null || startPos >= text.length() || endPos < startPos || endPos >= text.length() || startPos < 0 || endPos < 0 || pattern.length() > endPos - startPos)
			return -1;
		
		if(pattern.length() == 0)
			return 0;
		
		//naive search only for one character (special case)
		if (pattern.length() == 1){
			for(int i = startPos; i <= endPos; i++){
				if (pattern.charAt(0) == text.charAt(i))
					return i;
			}
			return -1;
		}

		pattern = pattern.toLowerCase();
		text = text.toLowerCase();
		//Actual Boyer-Moore Algorithm
		//develop skip tables based on the pattern
		final int[] badCharacter = badCharacterGenerator(pattern);
		final int[] goodSuffix = goodSuffixGenerator(pattern);
		
		int i = startPos; //Start from startPos
		while(i + pattern.length() - 1 <= endPos){ //While it's less than endPos
			System.out.println("Text:\t\t" + text);
			System.out.print("Pattern:\t"); //Print out pattern, make sure matches up with text at correct locations
			for(int j = 0; j < i; j++)
				System.out.print(" ");
			System.out.println(pattern);
			
			char textLetter = text.charAt(i + pattern.length() - 1); //Find letter corresponding to same location as last character of patter
			if(pattern.charAt(pattern.length() - 1) != textLetter){ //If bad character
				if(textLetter == ' ')
					i = i + badCharacter[26]; //
				else
					i = i + badCharacter[((int) textLetter) - 97];
				System.out.println("Bad character:\tMoving index of start of pattern to index " + i + " of text");
			}
			else{
				StringBuilder suffix = new StringBuilder();
				int offset = pattern.length() - 1; int j;
				int start = i;
				for (j = i + offset; j >= i; j--){ //Go through text
					if (text.charAt(j) != pattern.charAt(offset)){ // If letters at location don't match
						i = i + goodSuffix[suffix.length()]; //Increment index by value in goodSuffix array
						System.out.println("Good suffix:\tMoving index of start of pattern to index " + i + " of text");
						break;
					}
					else{
						suffix.insert(0, text.charAt(j)); //If match, add to stringbuilder
						offset--;
					}
				}
				if(j < start) //Means everything matches, so return index
					return i;
			}
			System.out.println();
		}
		//use the tables
		return -1; //If while loop iterated through without breaking or returning, pattern not found
	}

	//creates the table of spaces to skip if there was a bad character match, for each letter
	public static int[] badCharacterGenerator(String pattern)
	{
		int[] skips = new int[27]; //Skips for each letter
		for(int i = 0; i < pattern.length(); i++){ //Number of skips for letters in the pattern
			char letter = pattern.charAt(i);
			if (letter == ' ')
				skips[26] = (pattern.length() - 1) - pattern.lastIndexOf(' ');
			else{
				int index = (int) letter - 97; //Ascii value of character minus 97, so 'a' = 0, 'b' = 1, etc...
				skips[index] = (pattern.length() - 1) - pattern.lastIndexOf(letter); //
			}
		}
		for(int i = 0; i < skips.length; i++){
			if(skips[i] == 0)
				skips[i] = pattern.length(); //Looks for letters that are not in pattern, skip pattern's length
		}
		return skips;
	}

	//creates the table of spaces to skip if there was a good suffix match
	public static int[] goodSuffixGenerator(String pattern)
	{
		int[] sufSkips = new int[pattern.length() + 1]; //Number of potential suffix matches is length of pattern
		for(int i = pattern.length() - 1; i >= 0; i--){
			String suffix = pattern.substring(i); //Get suffix based on i
			for(int j = 0; j < suffix.length(); j++){
				String prefix = pattern.substring(0, suffix.length() - j); //Look at potential prefixes that might match with some portion of suffix
				if(prefix.equals(suffix.substring(j))){
					sufSkips[pattern.length() - i] = i + (suffix.length() - prefix.length()); //If it does, give a value
					break;
				}
			}
			if(sufSkips[pattern.length() - i] == 0){ //If a portion of good suffix not a part of prefix
				if(pattern.indexOf(suffix) != i) //If there's another instance of suffix
					sufSkips[pattern.length() - i] = i - pattern.indexOf(suffix); //Move specific amount
				else
					sufSkips[pattern.length() - i] = pattern.length(); //No other instance, move by pattern length
			}
		}
		return sufSkips;
	}

	public static void main(String[] args)
	{
		String[] p = {"odbb od", "aaa", "bcdeb", "eeee  fff", "AlanShen", "abababab"};
		String[] t = {"abffodjffogjaabbddcgggffsssgodbbod od odbb odffffff", "bbbaabababafff", "bfffbfffbcdeb", "eeeeee fffff", "AlanAlanAlanShAlanShen", "abcbababababababab"};
		for(int i = 0; i < p.length; i++){
			int index = find(t[i], p[i], 0, t[i].length() - 1);
			System.out.println();
			if(index == -1)
				System.out.println("Pattern '" + p[i] + "' not found in text '" + t[i] + "'");
			else
				System.out.println("Pattern '" + p[i] + "' found in text '" + t[i] + "' at location " + index);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println();
		}
		
		int[] starts = {0, 2, 3, 98, 1, 1};
		int[] ends = {30, 9, 15, 0, 6, 10};
		for(int i = 0; i < p.length; i++){
			int index = find(t[i], p[i], starts[i], ends[i]);
			System.out.println();
			if(index == -1)
				System.out.println("Pattern '" + p[i] + "' not found in text '" + t[i] + "' from index " + starts[i] + " to " + ends[i]);
			else
				System.out.println("Pattern '" + p[i] + "' found in text '" + t[i] + "' from index " + starts[i] + " to " + ends[i] + " at location " + index);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println();
		}
	}
}
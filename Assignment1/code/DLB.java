import java.util.*;

public class DLB implements DictInterface{
	private Node first;

	public DLB()
	{
		first = new Node();
	}

	// Add new String to end of list.  If String should come before
	// previous last string (i.e. it is out of order) sort the list.
	// We are keeping the data sorted in this implementation of
	// DictInterface to make searches a bit faster.
	public boolean add(String s)
	{
		Node curr = first;
		for (int i = 0; i < s.length(); i++){
			char letter = s.charAt(i);
			Node temp = curr;
			for (int j = 0; j < curr.children.size(); j++){
				Node node = curr.children.get(j);
				if(node.data == letter) //See if node contains that letter
					curr = node;		//Have curr reference one node down
			}
			if(curr == temp){			//Means letter not found
				Node node = new Node(letter);
				curr.children.add(node); //Add a node to the children
				curr = node;
			}			
		}
		curr.value = true;
		
		return true;
	}

	// Implement the searchPrefix method as described in the
	// DictInterface class.
	public int searchPrefix(StringBuilder s)
	{
		return searchPrefix(s, 0, s.length()-1);
	}

	public int searchPrefix(StringBuilder s, int start, int end)
	{
		Node curr = first;
		for(int i = 0; i < s.length(); i++){ //Iterate through letters of s
			char letter = s.charAt(i);
			Node temp = curr;
			for(int j = 0; j < curr.children.size(); j++){
				if(curr.children.get(j).data == letter){
					curr = curr.children.get(j); //Have node reference the child that contains the letter
					break;
				}
			}
			if(curr == temp) //Return zero if it doesn't exist
				return 0;
		}
		if(!curr.value) //Return 1 if no value, so not a word
			return 1;
		else if(curr.children.size() == 0) //If no children and has a value, just a word
			return 2;
		else //Word and prefix
			return 3;
	}

	private class Node
	{
		private char data;
		public LinkedList<Node> children;
		private boolean value; //Indicates as a word

		public Node(){
			value = false;
			children = new LinkedList<Node>();
		}
		
		public Node(char c)
		{
			data = c;
			value = false;
			children = new LinkedList<Node>();
		}
		
	}
}


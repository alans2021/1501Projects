import java.util.*;

public class DLB implements DictInterface{
	private ArrayList<String> list;
	private Node first;

	public DLB()
	{
		list = new ArrayList<String>();
	}

	// Add new String to end of list.  If String should come before
	// previous last string (i.e. it is out of order) sort the list.
	// We are keeping the data sorted in this implementation of
	// DictInterface to make searches a bit faster.
	public boolean add(String s)
	{
		for (int i = 0; i < s.length(); i++){
			char letter = s.charAt(i); 
		}
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
		return 0;
	}

	private class Node
	{
		private char data;
		private Node next;
		private boolean value;

		public Node(char c)
		{
			data = c;
			next = null;
		}

		public Node(char c, Node n)
		{
			data = c;
			next = n;
		}
	}
}


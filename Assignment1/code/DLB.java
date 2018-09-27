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
			
			Node node = curr.search(letter);
			if(node == null){
				node = new Node(letter);
				curr.addChild(node);
			}
			curr = node;			
		}
		curr.setWord();
		
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
		for(int i = 0; i < s.length(); i++){
			char letter = s.charAt(i);
			curr = curr.search(letter);
			if(curr == null)
				return 0;
		}
		if(!curr.isWord())
			return 1;
		else if(curr.children.size() == 0)
			return 2;
		else
			return 3;
	}

	private class Node
	{
		private char data;
		public LinkedList<Node> children;
		public Node left;
		public Node right;
		private boolean value; //Indicates as a word

		public Node(){
			value = false;
			children = new LinkedList<Node>();
			left = null;
			right = null;
		}
		
		public Node(char c)
		{
			data = c;
			value = false;
			children = new LinkedList<Node>();
			left = null;
			right = null;
		}
		
		public char getChar(){
			return data;
		}

		public void addChild(Node n)
		{
			if (children.size() > 1)
			{
				Node curr = children.get(0);
				int i = 0;
				while(curr != null && n.getChar() > curr.getChar()){
					curr = curr.right;
					i++;
				}
				children.add(i, n);
				addSibling(n, curr);
			}
			else
				children.add(n);
		}
		
		private void addSibling(Node n, Node r){
			Node left;
			if (r != null){
				left = r.left;
				r.left = n;
			}
			else
				left = children.get(children.size() - 2);
			n.right = r;
			n.left = left;
			if (left != null)
				left.right = n;	
		}
		
		public Node search(char c)
		{
			for(int i = 0; i < children.size(); i++){
				if(children.get(i).getChar() == c)
					return children.get(i);
			}
			return null;
		}
		
		public void setWord(){
			value = true;
		}
		
		public boolean isWord(){
			return value;
		}
		
	}
}


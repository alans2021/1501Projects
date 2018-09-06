import java.util.*;

public class DLB implements DictInterface{
	private ArrayList<String> list;

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
}

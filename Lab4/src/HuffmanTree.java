import java.util.Hashtable;
import java.util.Set;
import java.util.PriorityQueue;

public class HuffmanTree<E>
{
	private final Hashtable<E,? extends Integer> count;
	private final Hashtable<E,String> encoding;
	private HuffmanNode<E> root;

	public HuffmanTree(final Hashtable<E,? extends Integer> count)
	{
		this.count = count;
		this.encoding = new Hashtable<>();
		this.developTree();
		this.developEncoding(this.root,"",0);
	}
	
	//Encode several items at once
	public Integer[] encode(final E[] items)
	{
		final Integer[] encodes = new Integer[items.length];
		for(int i = 0; i < items.length; i++)
			encodes[i] = getEncoding(items[i]);
		return encodes;
	}

	//Encode a single item only
	public Integer getEncoding(final E item)
	{
		return Integer.parseInt(this.encoding.get(item));
	}

	//Decode several items
	public E[] decode(Integer[] encoding)
	{
		final E[] decodes = (E[]) new Object[encoding.length];
		for(int i = 0; i < decodes.length; i++)
			decodes[i] = getDecoding(encoding[i]);
		return decodes;
	}

	//Decode a single item only
	public E getDecoding(int code)
	{
		HuffmanNode<E> treeLoc = this.root;
		int codeLevel = 0;
		while(codeLevel < Integer.SIZE) //While not at leaf node
		{
			final int masked = code & 1; //figure out what bit
			HuffmanNode<E> newTreeLoc = treeLoc.getChild(masked == 0); //move pointer to correct direction
			if(newTreeLoc == null)
			{
				return treeLoc.item; //When at leaf node, return the object at that node
			}
			treeLoc = newTreeLoc;
			code = code >>> 1; //Remove bit from int
			codeLevel++;
		}
		return null;
	}

	//Generates the Huffman Tree
	private final void developTree()
	{
		PriorityQueue<HuffmanNode> nodes = new PriorityQueue<>(); //Create priority queue of Huffman nodes
		Set<E> setKey = count.keySet(); //Get set of all keys
		E[] keys = (E[]) setKey.toArray(); //Convert to array
		for(int i = 0; i < keys.length; i++){
			HuffmanNode<E> node = new HuffmanNode<E>(keys[i], count.get(keys[i]));
			nodes.add(node); //Add huffman node to queue 
		
		}
		
		while(nodes.size() > 1){
			HuffmanNode<E> left = nodes.poll(); //Pull two lowest Huffman nodes
			HuffmanNode<E> right = nodes.poll();
			HuffmanNode<E> subtree = new HuffmanNode<E>(); 
			subtree.setChildren(left, right); //Make subtree with left and right as children
			nodes.add(subtree); //Add to queue
		}
		
		root = nodes.poll();
		checkTree(this.root);
		System.out.println("Test");
		System.out.println();
	}

	//helper method to ensure your tree is valid
	private final void checkTree(HuffmanNode<E> loc)
	{
		if(loc == null)
		{
			return;
		}
		if(loc.getChild(true) == null && loc.getChild(false) == null && loc.item == null)
		{
			throw new IllegalArgumentException("Illegal leaf node");
		}
		if((loc.getChild(true) == null && loc.getChild(false) != null) || (loc.getChild(true) != null && loc.getChild(false) == null))
		{
			throw new IllegalArgumentException("Illegal branch node");
		}
		checkTree(loc.getChild(true));
		checkTree(loc.getChild(false));
	}

	//develops the encoding map based on the Huffman Tree
	private final void developEncoding(final HuffmanNode<? extends E> node, final String prefix, final int level)
	{

	}
}

import java.util.Hashtable;
import java.util.Set;

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
		int min = count.get('a');
		int min2 = count.get('a');
		Set<E> keys = count.keySet();
		HuffmanNode<E> left = null;
		HuffmanNode<E> right = null;
		HuffmanNode<E> combine = new HuffmanNode<E>();
		for(E key: keys){
			if(count.get(key) < min)
				min = count.get(key);
			left = new HuffmanNode<E>(key, min);	
		}
		for(E key: keys){
			if(count.get(key) < min2 && !left.item.equals(key))
				min2 = count.get(key);
			right = new HuffmanNode<E>(key, min2);
		}
		combine.setChildren(left, right);
		
		root = new HuffmanNode<E>();
		checkTree(this.root);
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

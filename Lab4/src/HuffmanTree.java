import java.util.Hashtable;

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
		
	}

	//Encode a single item only
	public Integer getEncoding(final E item)
	{
		return Integer.parseInt(this.encoding.get(item), 2);
	}

	//Decode several items
	public E[] decode(Integer[] encoding)
	{
		
	}

	//Decode a single item only
	public E getDecoding(int code)
	{
		HuffmanNode<E> treeLoc = this.root;
		int codeLevel = 0;
		while(codeLevel < Integer.SIZE)
		{
			final int masked = code & 1;
			HuffmanNode<E> newTreeLoc = treeLoc.getChild(masked == 0);
			if(newTreeLoc == null)
			{
				return treeLoc.item;
			}
			treeLoc = newTreeLoc;
			code = code >>> 1;
			codeLevel++;
		}
		return null;
	}

	//Generates the Huffman Tree
	private final void developTree()
	{
		//checkTree(this.root);
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

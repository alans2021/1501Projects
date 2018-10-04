public class HuffmanNode<T> implements Comparable<HuffmanNode<T>>
{
	public final T item;
	private int count;
	private HuffmanNode<T> leftChild;
	private HuffmanNode<T> rightChild;

	public HuffmanNode()
	{
		this.item = null;
		this.count = 0;
	}
	
	public HuffmanNode(final T item, final int count)
	{
		if(count < 0)
		{
			throw new IllegalArgumentException("Negative count value");
		}
		this.item = item;
		this.count = count;
		this.leftChild = null;
		this.rightChild = null;
	}
	
	public int getCount()
	{
		return this.count;
	}
	
	public HuffmanNode<T> getChild(final boolean leftChild)
	{
		return leftChild ? this.leftChild : this.rightChild;
	}

	public void setChildren(final HuffmanNode<T> leftChild, final HuffmanNode<T> rightChild)
	{
		if(leftChild == null || rightChild == null)
		{
			throw new IllegalArgumentException("Children of HuffmanNode cannot be null");
		}
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.count = leftChild.getCount() + rightChild.getCount();
	}

	/**
	 * Note: this class has a natural ordering that is inconsistent with equals.
	 */
	@Override	
	public int compareTo(final HuffmanNode<T> o)
	{
		return this.count - o.getCount();
	}
}
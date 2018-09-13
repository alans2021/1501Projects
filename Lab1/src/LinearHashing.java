import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

//assumes linear hashing
public class LinearHashing<K,V>
{
	//fields
	private int numBucketsInLevel;
	private int numLevels;
	private int splitBucketPointer;
	private List<List<K>> keys;
	private List<List<V>> table;
	
	//static fields
	private static final int BUCKET_LIMIT = 4;
	
	//constructor
	public LinearHashing()
	{
		this.numBucketsInLevel = 4;
		this.numLevels = 0;
		this.splitBucketPointer = 0;
		this.keys = new ArrayList<List<K>>();
		this.table = new ArrayList<List<V>>();
		for(int i = 0; i < this.numBucketsInLevel; i++)
		{
			this.keys.add(new LinkedList<K>());
			this.table.add(new LinkedList<V>());
		}
	}

	//checks if the key is already in use
	public boolean containsKey(final K key)
	{
		final List<K> keyBucket = this.keys.get(this.hashValue(key));
		for(int j = 0; j < keyBucket.size(); j++)
		{
			if(keyBucket.get(j).equals(key))
			{
				return true;
			}
		}
		return false;
	}

	//checks if the value is already in use (no guarantee about the specific key, given there may be duplicates)
	public boolean containsValue(final V value)
	{
		for(int i = 0; i < this.table.size(); i++)
		{
			final List<V> valueBucket = this.table.get(i);
			for(int j = 0; j < valueBucket.size(); j++)
			{
				if(valueBucket.get(j).equals(value))
				{
					return true;
				}
			}
		}
		return false;
	}

	//insert function
	public boolean insert(final K key, final V value)
	{

	}

	//retrieves the value from the hash table based on the key
	public V retrieve(final K key)
	{
		return find(key,false);
	}
	
	//removes and returns the value from the hash table based on the key
	public V remove(final K key)
	{
		return find(key,true);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		//provide keys
		sb.append("Keys:\n");
		for(int i = 0; i < this.keys.size(); i++)
		{
			sb.append(i + "|");
			final List<K> keyBucket = this.keys.get(i);
			for(int j = 0; j < keyBucket.size(); j++)
			{
				sb.append(keyBucket.get(j).toString() + ",");
			}
			sb.append("\n");
		}
		
		//provide values
		sb.append("\nValues:\n");
		for(int i = 0; i < this.table.size(); i++)
		{
			sb.append(i + "|");
			final List<V> valueBucket = this.table.get(i);
			for(int j = 0; j < valueBucket.size(); j++)
			{
				sb.append(valueBucket.get(j).toString() + ",");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

	//locates a value based on its key, and may remove the value from the table
	private V find(final K key, boolean remove)
	{
		//retrieve correct bucket
		final int hashValue = this.hashValue(key);
		final List<K> keyBucket = this.keys.get(hashValue);
		final List<V> valueBucket = this.table.get(hashValue);

		//find item
		for(int i = 0; i < keyBucket.size(); i++)
		{
			if(keyBucket.get(i).equals(key))
			{
				if(remove)
				{
					keyBucket.remove(i);
					return valueBucket.remove(i);
				}
				else
				{
					return valueBucket.get(i);
				}
			}
		}
		return null;
	}

	//performs the linear split, where applicable
	private void split()
	{

	}

	//hash value from keys
	private int hashValue(K key)
	{

	}

	public static void main(String[] args)
	{
		LinearHashing<String,String> lh = new LinearHashing<>();
		lh.insert("12","A");
		lh.insert("16","B");
		lh.insert("52","C");
		lh.insert("56","D");
		System.out.println(lh.toString());
		
		System.out.println("First split");
		lh.insert("5","E");
		System.out.println(lh.toString());
		
		System.out.println("Second split");
		lh.insert("9","F");
		System.out.println(lh.toString());

		System.out.println("Remove");
		System.out.println(lh.remove("12"));
		System.out.println(lh.toString());
		
		System.out.println(lh.containsKey("12"));
		System.out.println(lh.containsKey("16"));
		System.out.println(lh.containsValue("D"));
	}
}
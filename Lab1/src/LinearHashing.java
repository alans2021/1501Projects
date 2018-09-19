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
		//Get a hash value for a key and insert keys and values at that specific hash index
		int hashVal = hashValue(key);
		keys.get(hashVal).add(key);
		table.get(hashVal).add(value);
		
		for(int i = 0; i < keys.size(); i++){
			List<K> keyBucket = keys.get(i);
		
			//If one bucket is overflowing, add a bucket and do a split
			if (keyBucket.size() > BUCKET_LIMIT){
				keys.add(new LinkedList<K>());
				table.add(new LinkedList<V>());
				numBucketsInLevel++;
				split();
				//If split pointer reaches 2 ^ (numLevels) * numBuckets, increment levels, reset split
				if (splitBucketPointer == numBucketsInLevel * (int)(Math.pow(2, numLevels))){
					numLevels++;
					splitBucketPointer = 0;
				}
				//Else, just increment split Pointer
				else
					splitBucketPointer++;
			}
		}
		return true;
		
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
		List<K> KeyList = keys.get(splitBucketPointer);
		//Do nothing if split pointer doesn't point to anything that's overflowing
		if (KeyList.size() <= BUCKET_LIMIT) 
			return;
		List<V> ValueList = table.get(splitBucketPointer);
		
		//For each key in overflowing bucket, apply hash function
		for(int i = ValueList.size() - 1; i >= 0; i--){
			int newHash = hashValue(KeyList.get(i));
			K key = KeyList.remove(i); //Remove key and value from old position
			V value = ValueList.remove(i);
			keys.get(newHash).add(key); //Add key and value to new index based on new hashvalue
			table.get(newHash).add(value);
		}
		
		return;
	}

	//hash value from keys
	private int hashValue(K key)
	{
		int exp = (int)(Math.pow(2, numLevels));
		int hash = key.hashCode() & 0x7fffffff;
		//Different hash functions based on pointer
		int num = hash % (numBucketsInLevel * exp);
		if (num < splitBucketPointer)
			num = hash % (numBucketsInLevel * exp * 2);
		return num;
	}

	public static void main(String[] args)
	{
		LinearHashing<String,String> lh = new LinearHashing<String, String>();
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
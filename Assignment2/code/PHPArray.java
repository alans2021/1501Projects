import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PHPArray<V> implements Iterable<V> {

	private int size;
	private Object[] values;
	private Node first;
	private Node last;
	private Node current = null;
	private Node pairPointer = null;
	
	public PHPArray(int i) {
		size = 0;
		values = new Object[i];
	}
	
	private int hash(String key) {
		return (key.hashCode() & 0x7fffffff) % (values.length);
    }
	
	private void resize(Object[] val){
		values = new Object[2 * val.length];
		Node curr = first;
		while(curr != null){
			int newHash = hash(curr.key);
			while(values[newHash] != null)
				newHash = (newHash + 1) % values.length;
			values[newHash] = curr;
			curr = curr.next;
		}
	
	}

	public void put(Object key, Object value){
		if(size >= values.length / 2)
			resize(values);
		
		String inter =  new String(key.toString());
		int hashCode = hash(inter);
		while(values[hashCode] != null)
			hashCode = (hashCode + 1) % (values.length);
		
		Node keyVal = new Node(inter, (V) value);
		values[hashCode] = keyVal;
		if(current == null){
			first = keyVal;
			current = keyVal;
		}
		else{
			current.next = keyVal;
			keyVal.prev = current;
			current = keyVal;
		}
		last = keyVal;
		
		size++;
		
	}
	
	public V get(Object k){
		int j = hash(k.toString());
        for (int i = hash(k.toString()); values[i] != null; i = (i + 1) % values.length){ 
            Node temp = (Node) values[i];
        	if (temp.key.equals(k.toString()))
                return temp.value;
        }
        return null;
	}
	
	public void unset(Object key){
		if(get(key) == null)
			return;
		int hashValue = hash(key.toString());
		Node temp = (Node) values[hashValue];
		while (!key.toString().equals( temp.key)) {
            hashValue = (hashValue + 1) % values.length;
            temp = (Node) values[hashValue];
        }
		
		Node delete = temp;
		Node previous = delete.prev;
		previous.next = delete.next;
		values[hashValue] = null;
		
		hashValue = (hashValue + 1) % values.length;
		while(values[hashValue] != null){ //Rehash those in same cluster
			temp = (Node) values[hashValue];
			String keyRehash = temp.key;
			Object valRehash = temp.value;
			values[hashValue] = null;
			put(keyRehash, valRehash);
			hashValue = (hashValue + 1) % values.length;
		}	
	}
	
	public ArrayList<String> keys(){
		Node curr_Node = first;
		ArrayList<String> keyList = new ArrayList<String>(size);
		while(curr_Node != null){
			keyList.add(curr_Node.key);
			curr_Node = curr_Node.next;
		}
		return keyList;
	}
	
	public ArrayList<V> values(){
		Node curr_Node = first;
		ArrayList<V> valuesList = new ArrayList<V>(size);
		while(curr_Node != null){
			valuesList.add(curr_Node.value);
			curr_Node = curr_Node.next;
		}
		return valuesList;
	}
	
	public void showTable(){
		System.out.println("\tRaw hash table contents:");
		for(int i = 0; i < values.length; i++){
			System.out.print(i + ": ");
			if(values[i] == null)
				System.out.println("null");
			else{
				Node n = (Node)values[i];
				System.out.println("Key: " + n.key + " Value: " + n.value);
			}
				
		}
	}
	
	public void sort(){
		
	}
	
	public void asort(){
		
	}
	
	public PHPArray<String> array_flip(){
		return null;
	}
	
	public static class Pair<V> {
		public String key;
		public V value;
		
		public Pair(String k, V val){
			key = k;
			value = val;
		}
	}
	

	public Pair<V> each() {
		if(pairPointer == null)
			pairPointer = first;
		else
			pairPointer = pairPointer.next;
		if(pairPointer == null)
			return null;
		
		Pair<V> next = new Pair<V>(pairPointer.key, pairPointer.value);
		return next;
	}

	public void reset(){
		pairPointer = null;
	}

	public Iterator<V> iterator() {
		return new ListIterator<V>(first);
	}


	public int length(){
		return size;
	}
	
	private class Node{
		private String key;
		private V value;
		private Node next;
		private Node prev;
		
		public Node(String k, V val)
		{
			key = k;
			value = val;
		}

	}
	
    private class ListIterator<V> implements Iterator<V> {
        private Node current;

        public ListIterator(Node first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public V next() {
            if (!hasNext()) throw new NoSuchElementException();
            V item = (V) current.value;
            current = current.next; 
            return item;
        }
    }

}

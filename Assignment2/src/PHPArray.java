import java.util.ArrayList;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class PHPArray<V> implements Iterable<V>{

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
	
	private int hash(String key) { //Get hash value
		return (key.hashCode() & 0x7fffffff) % (values.length);
    }
	
	private void resize(Object[] val){ //Double hash table length
		System.out.println("\t\tSize: " + size + " -- resizing array from " + val.length + " to " + 2 * val.length);
		values = new Object[2 * val.length];
		Node curr = first;
		while(curr != null){ //Rehash all nodes
			int newHash = hash(curr.key);
			while(values[newHash] != null)
				newHash = (newHash + 1) % values.length;
			values[newHash] = curr;
			curr = curr.next;
		}
	
	}

	public void put(Object key, Object value){
		if(size >= values.length / 2) //Resize if array length less than double the size
			resize(values);
		
		String inter =  new String(key.toString());
		int hashCode = hash(inter);
		
		while(values[hashCode] != null){
			Node temp = (Node) values[hashCode];
			if(temp.key.equals(inter)){ //See if key already exists, if so, just modify value
				temp.value = (V) value;
				return;
			}
			hashCode = (hashCode + 1) % (values.length);
		}
		
		Node keyVal = new Node(inter, (V) value); //Add node to hash table
		values[hashCode] = keyVal;
		if(current == null){
			first = keyVal; // Set node relationships  
			current = keyVal;
		}
		else{
			current.next = keyVal; //Set next and prev relationship
			keyVal.prev = current;
			current = keyVal;
		}
		last = keyVal;
		
		size++; 
		
	}
	
	public V get(Object k){
		int j = hash(k.toString()); //Get hash value
        for (int i = hash(k.toString()); values[i] != null; i = (i + 1) % values.length){ 
            Node temp = (Node) values[i];
        	if (temp.key.equals(k.toString())) //Return the value if key is found
                return temp.value;
        }
        return null;
	}
	
	public void unset(Object key){
		if(get(key) == null) //See if key exists
			return;
		int hashValue = hash(key.toString()); 
		Node temp = (Node) values[hashValue]; //Find node with key
		while (!key.toString().equals( temp.key)) {
            hashValue = (hashValue + 1) % values.length;
            temp = (Node) values[hashValue];
        }
		if(temp == first)
			first = first.next;
		else{
			Node delete = temp; //Update next and prev relationships
			Node previous = delete.prev;
			previous.next = delete.next;
		}
		values[hashValue] = null;
		
		hashValue = (hashValue + 1) % values.length;
		while(values[hashValue] != null){ //Rehash those in same cluster
			temp = (Node) values[hashValue];
			values[hashValue] = null;
			System.out.println("\t\tKey " + temp.key + " rehashed ");
			System.out.println();
			reput(temp); //Put in new hash location
			hashValue = (hashValue + 1) % values.length;
		}
		size--;
	}
	
	private void reput(Node temp){
		int rehash = hash(temp.key);
		while(values[rehash] != null){
			rehash = (rehash + 1) % values.length;
		}
		values[rehash] = temp; //Node placed in updated location
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
		try{
			Comparable[] vals = new Comparable[size];
			Node curr = first;
			int i = 0;
			while(curr != null){ //Put values in comparable array
				vals[i] = (Comparable) curr.value; //Try to cast
				curr = curr.next;
				i++;
			}
			vals = mergeSort(vals, 0, vals.length); //Mergesort vals
			for(i = 0; i < values.length; i++) //Set array to null
				values[i] = null;
			first = null; 
			current = null;
			last = null;
			size = 0;
			
			for(int j = 0; j < vals.length; j++) //Put sorted nodes in table with updated keys
				put(Integer.toString(j), vals[j]);
			
		}
		catch(ClassCastException e){
			System.out.println("PHPArray values are not Comparable -- cannot be sorted");
		}
	}
	
	private Comparable[] mergeSort(Comparable[] c, int start, int end){
		if((end - start) == 1){
			Comparable[] inter = {c[start]};
			return inter;
		}
		Comparable[] left = mergeSort(c, start, (start + end) / 2);
		Comparable[] right = mergeSort(c, (start + end) / 2, end);
		Comparable[] inter = new Comparable[end - start]; //Following is the merge
		int leftCurr = 0;
		int leftEnd = left.length;
		int rightCurr = 0;
		int rightEnd = right.length;
		int mergeIndex = 0;
		while(leftCurr < leftEnd && rightCurr < rightEnd){
			if(left[leftCurr].compareTo(right[rightCurr]) <= 0){
				inter[mergeIndex] = left[leftCurr];
				leftCurr++;
			}
			else{
				inter[mergeIndex] = right[rightCurr];
				rightCurr++;
			}
			mergeIndex++;
		}
		if(leftCurr == leftEnd){
			for(int i = rightCurr; i < rightEnd; i++){
				inter[mergeIndex] = right[i];
				mergeIndex++;
			}
		}
		else{
			for(int i = leftCurr; i < leftEnd; i++){
				inter[mergeIndex] = left[i];
				mergeIndex++;
			}
		}
		return inter;
		
	}
	
	public void asort(){
		try{
			Object[] nodes = new Object[size]; //Store nodes
			Node curr = first;
			int i = 0;
			while(curr != null){
				nodes[i] = curr;
				curr = curr.next;
				i++;
			}
			nodes = nodeSort(nodes, 0, nodes.length); //Sort nodes based off of values field
			
			for(i = 0; i < values.length; i++)
				values[i] = null;
			first = null;
			current = null;
			last = null;
			size = 0;
			
			for(int j = 0; j < nodes.length; j++){
				curr = (Node) nodes[j];
				put(curr.key, curr.value); //Place in hash table
			}
			
		}
		catch(ClassCastException e){
			System.out.println("PHPArray values are not Comparable -- cannot be sorted");
		}
	}
	
	private Object[] nodeSort(Object[] c, int start, int end){
		if((end - start) == 1){
			Object[] inter = {c[start]};
			return inter;
		}
		Object[] left = nodeSort(c, start, (start + end) / 2);
		Object[] right = nodeSort(c, (start + end) / 2, end);
		Object[] inter = new Object[end - start];
		int leftCurr = 0;
		int leftEnd = left.length;
		int rightCurr = 0;
		int rightEnd = right.length;
		int mergeIndex = 0;
		while(leftCurr < leftEnd && rightCurr < rightEnd){
			if(((Node)left[leftCurr]).compareTo((Node)right[rightCurr]) <= 0){
				inter[mergeIndex] = left[leftCurr];
				leftCurr++;
			}
			else{
				inter[mergeIndex] = right[rightCurr];
				rightCurr++;
			}
			mergeIndex++;
		}
		if(leftCurr == leftEnd){
			for(int i = rightCurr; i < rightEnd; i++){
				inter[mergeIndex] = right[i];
				mergeIndex++;
			}
		}
		else{
			for(int i = leftCurr; i < leftEnd; i++){
				inter[mergeIndex] = left[i];
				mergeIndex++;
			}
		}
		return inter;
		
	}
	
	public PHPArray<String> array_flip() throws ClassCastException{
		PHPArray<String> flip = new PHPArray<String>(values.length);
		
		Node curr = first;
		while(curr != null){
			String newKey = (String) curr.value;
			String newVal = curr.key;
			flip.put(newKey, newVal); //Put new key value pairs in flip PHPArray
			curr = curr.next;
		}
		
		return flip;
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
		if(pairPointer == null) //Set pairPointer to first if haven't started iterating through
			pairPointer = first;
		else
			pairPointer = pairPointer.next; //Set to next
		if(pairPointer == null)
			return null;
		
		Pair<V> next = new Pair<V>(pairPointer.key, pairPointer.value);
		return next;
	}

	public void reset(){
		pairPointer = null; //Set to null
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

		public int compareTo(Node n) throws ClassCastException{
			Comparable first = (Comparable) this.value; //If values are comparable, compare values
			Comparable second = (Comparable) n.value;
			return first.compareTo(second);
		}

	}
	
    private class ListIterator<V> implements Iterator<V> {
        private Node current;

        public ListIterator(Node first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public V next() { //Go to Node's next reference
            if (!hasNext()) throw new NoSuchElementException();
            V item = (V) current.value;
            current = current.next; 
            return item;
        }
    }

}

import java.util.*;

public class DLB<Value>{
    private Node<Value> first;

    public DLB()
    {
        first = new Node<Value>();
    }

    // Add new String to end of list.  If String should come before
    // previous last string (i.e. it is out of order) sort the list.
    // We are keeping the data sorted in this implementation of
    // DictInterface to make searches a bit faster.
    public boolean add(StringBuilder s, Value v)
    {
        if(s.equals("ess "))
            System.out.println("Testing");
        Node<Value> curr = first;
        for (int i = 0; i < s.length(); i++){
            char letter = s.charAt(i);
            Node<Value> temp = curr;
            for (int j = 0; j < curr.children.size(); j++){
                Node<Value> node = curr.children.get(j);
                if(node.data == letter) { //See if node contains that letter
                    curr = node;        //Have curr reference one node down
                    break;
                }
            }
            if(curr == temp){			//Means letter not found
                Node<Value> node = new Node<Value>(letter);
                curr.children.add(node); //Add a node to the children
                curr = node;
            }
        }
        curr.value = v;

        return true;
    }

    public int searchPrefix(StringBuilder s)
    {
        Node<Value> curr = first;
        for(int i = 0; i < s.length(); i++){ //Iterate through letters of s
            char letter = s.charAt(i);
            Node<Value> temp = curr;
            for(int j = 0; j < curr.children.size(); j++){
                if(curr.children.get(j).data == letter){
                    curr = curr.children.get(j); //Have node reference the child that contains the letter
                    break;
                }
            }
            if(curr == temp) //Return zero if it doesn't exist
                return 0;
        }
        if(curr.children.size() == 0) //If no children and has a value, just a word
            return 1;
        else //Word and prefix
            return 2;
    }

    public Value get(StringBuilder s){
        Node<Value> curr = first;
        for(int i = 0; i < s.length(); i++){
            char letter = s.charAt(i);
            for(int j = 0; j < curr.children.size(); j++){
                if(curr.children.get(j).data == letter) {
                    curr = curr.children.get(j);
                    break;
                }
            }
        }
        if(curr.value == null)
            System.out.println(s);
        return curr.value;
    }

    private class Node<Value>
    {
        private char data;
        public LinkedList<Node<Value>> children;
        private Value value; //Indicates as a word

        public Node(){
            value = null;
            children = new LinkedList<Node<Value>>();
        }

        public Node(char c)
        {
            data = c;
            value = null;
            children = new LinkedList<Node<Value>>();
        }

    }
}



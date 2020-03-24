import java.util.Iterator; 

public class Deque<Item> implements Iterable<Item> {

    private class Node{
        Item item;
        Node next; // Next Link
        Node prev; // Previous Link
    }

    private Node first;
    private Node tail;

    private int size;
    
    // construct an empty deque
    public Deque(){
        first   = null;
        tail    = null;
        size    = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return (first == null);
    }

    // return the number of items on the deque
    public int size(){
        return size;
    }

    // add the item to the front
    // Case 1) No item -> Asign to head , tail = head
    // Case 2) One item -> Assign to head , tail.prev = head
    // Case 3) At least two item -> Assign to head , head.next.prev = current
    public void addFirst(Item item){
        if (item == null) throw new IllegalArgumentException();
        //Asign to head
        Node current = new Node();
        current.item = item;
        current.next = first;
        first = current;
        
        if (size == 0) tail = current;
        if (size == 1) tail.prev = current;
        if (size >= 2) first.next.prev = current;

        size++;
    }

    // add the item to the back
    // Case 1) No item -> Assign to Tail, head = tail
    // Case 2) One item -> Assign to Tail , tail.prev = head
    // Case 3) At least two item -> Node.prev = tail , tail.next = Node, tail = Node
    public void addLast(Item item){
        if (item == null) throw new IllegalArgumentException();
        Node current = new Node();
        current.item = item;
        current.prev = tail;
        tail = current;

        if (size == 0) first = current;
        if (size == 1) first.next = current;
        if (size >= 2) tail.prev.next = current;

        size++;
    }

    // remove and return the item from the front
    // Case 1) One item -> Change to zero item , head = null , pretail = null , tail = null
    // Case 2) Two item -> Change to one item, head = next , tail = head (no change) . head.prev = null
    public Item removeFirst(){
        if (size == 0) throw new java.util.NoSuchElementException();

        Item item       = first.item;

        if (size == 1) {first = null ; tail = null;}
        if (size >= 2) {
            first      = first.next;
            first.prev = null;
        }

        size--;
        return item;
    }

    // remove and return the item from the back
    // Case 1) One item -> Change to zero item , head = null , tail = null 
    // Case 2) Two item -> Change to one item , pretail = head , tail = pretail head (no change)
    // Case 3) At least three items -> tail = tail.prev
    public Item removeLast(){
        if (size == 0) throw new java.util.NoSuchElementException();

        Item item = tail.item;

        if (size == 1) {first=null;tail=null;}
        if (size >= 2) {
            tail      = tail.prev;
            tail.next = null;
        }

        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public void remove(){ throw new UnsupportedOperationException();}
        public Item next()
        {
            if (current.next == null) throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        System.out.println("Hello World");

        // Test Case 1) Throw an IllegalArgumentException if the client calls either addFirst() or addLast() with a null argument.
        // Test Case 2) Throw a java.util.NoSuchElementException if the client calls either removeFirst() or removeLast when the deque is empty.
        // Test Case 3) Throw a java.util.NoSuchElementException if the client calls the next() method in the iterator when there are no more items to return.
        // Test Case 4) Throw an UnsupportedOperationException if the client calls the remove() method in the iterator.
    }

}
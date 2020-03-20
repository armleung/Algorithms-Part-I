import java.util.Iterator; 

public class Deque<Item> implements Iterable<Item> {

    private class Node{
        Item item;
        Node next;
    }

    private Node first;
    private Node tail;
    private Node pretail;
    private int size;
    
    // construct an empty deque
    public Deque(){
        first   = null;
        tail    = null;
        pretail = null;
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
    public void addFirst(Item item){
        if (item == null) throw new IllegalArgumentException();
        Node current = new Node();
        current.item = item;
        current.next = first;
        first = current;
        size++;
    }

    // add the item to the back
    public void addLast(Item item){
        if (item == null) throw new IllegalArgumentException();
        Node current = new Node();
        current.item = item;
        tail.next = current;
        tail = current;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if (size == 0) throw new java.util.NoSuchElementException();
        Item item = first.item;
        first = first.next;
        size--;
        return item;
    }

    // remove and return the item from the back
    // TODO : How to get previous node of tail ? 
    public Item removeLast(){
        if (size == 0) throw new java.util.NoSuchElementException();
        Item item = tail.item;
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
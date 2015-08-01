/*  Matt Walther
    0211
*/
package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class OrderedList<E> implements OrderedListADT<E> {
    
    public Node<E> head;
    private int currentSize;
    
    public OrderedList(){
        head = null;
        currentSize = 0;
    }
    
    /**
     * Adds the Object obj to the list in the correct position as determined by the Comparable interface.
     * @param obj 
     */
    public void insert(E obj){
        Node<E> newNode = new Node<E>(obj),
                previous = null,
                current = head;
        while ( current != null && doCompare(obj, current.data) >= 0 ){
            previous = current;
            current = current.next;
        }
        
        newNode.next = current;
        if ( previous == null ) head = newNode;
        else previous.next = newNode;
        currentSize++;
    }

    /**
     * Removes and returns the object located at the parameter index position (zero based).
     * Throws IndexOutOfBoundsException if the index does not map to a valid position within the list.
     * @param index
     * @return E
     */
    public E remove(int index){
        if (index < 0 || index > this.currentSize-1) throw new IndexOutOfBoundsException();
        Node<E> prev = null,
                curr = head;
        for ( int idx = 0 ; (idx < index && curr != null) /* I'm scared, okay? */ ; idx++ ){
            prev = curr;
            curr = curr.next;
        }
        if ( curr == head ) head = curr.next;
        else prev.next = curr.next;
        currentSize--;
        return curr.data;
    }
    
    /**
     * Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
     * @param obj
     * @return E
     */
    public E remove(E obj){
        Node<E> prev = null,
                curr = head;
        while ( curr != null && doCompare(obj, curr.data) != 0 ){
            prev = curr;
            curr = curr.next;
        }
        if ( curr == null ) return null;
        if ( curr == head ) head = head.next;
        else if ( curr.next == null ) prev.next = null;
        else prev.next = curr.next;
        currentSize--;
        return curr.data;
    }
    
//  Removes and returns the smallest element in the list and null if the it is empty.
    /**
     * Removes and returns the smallest element in the list and null if the it is empty.
     * @return E
     */
    public E removeMin(){
        if ( head == null ) return null;
        E temp = head.data;
        head = head.next;
        currentSize--;
        return temp;
    }
    
    /**
     * Removes and returns the largest element in the list and null if the it is empty.
     * @return E
     */
    public E removeMax(){
        if ( head == null ) return null;
        Node<E> prev = null,
                curr = head;
        while ( curr != null && curr.next != null ){
            prev = curr;
            curr = curr.next;
        }
        if ( prev == null ) head = null;
        else prev.next = null;
        currentSize--;
        return curr.data;
    }

    /**
     * Returns the parameter object located at the parameter index position (zero based).
     * Throws IndexOutOfBoundsException if the index does not map to a valid position within the underlying array
     * @param index
     * @return E
     */
    public E get(int index){
        if (index < 0 || index > currentSize-1) throw new IndexOutOfBoundsException();
        Node<E> curr = head;
        for ( int idx = 0 ; (idx < index && curr != null) ; idx++ )
            curr = curr.next;
        if ( curr == null ) return null;
        return curr.data;
    }
    
    /**
     * Returns the list object that matches the parameter, and null if the list is empty. 
     * Also returns null if the obj is NOT in the list.
     * This method is stable, if obj matches more than one element, the element that
     * has been in the list longest is returned.
     * @param obj
     * @return E
     */
    public E get(E obj){
        Node<E> curr = head;
        while ( curr != null && doCompare(obj, curr.data) != 0 )
            curr = curr.next;
        if ( curr == null ) return null;
        return curr.data;
    }
    
    /**
     * Returns the index of the first element that matches the parameter obj
     * and -1 if the item is not in the list.
     * @param obj
     * @return E
     */
    public int find(E obj){
        Node<E> curr = head;
        int index = 0;
        while ( curr != null && doCompare(obj, curr.data) != 0 ){
            curr = curr.next;
            index++;
        }
        if ( curr == null ) return -1;
        return index;
    }

    /**
     * Returns true if the parameter object obj is in the list, false otherwise.
     * @param obj
     * @return boolean
     */
    public boolean contains(E obj){
        return ( find(obj) > 0 ? true : false );
    }

    /**
     * The list is returned to an empty state.
     */
    public void clear(){
        head = null;
        currentSize = 0;
    }

    /**
     * Returns true if the list is empty, otherwise false
     * @return boolean
     */
    public boolean isEmpty(){
        return ( head == null ? true : false );
    }

//  
    /**
     * Returns the number of Objects currently in the list.
     * @return int
     */
    public int size(){
        return currentSize;
    }
    
    /**
     * Returns an Iterator of the values in the list, presented in
     * the same order as the list.
     * @return Iterator<E>
     */
    public Iterator<E> iterator(){
        return new InnerIterator();
    }

    class InnerIterator implements Iterator<E>{
        private Node<E> iterNode;
        
        public InnerIterator(){
            iterNode = head;
        }
        
        public boolean hasNext(){
            return (iterNode != null);
        }
        
        public E next(){
            if (!hasNext()) throw new NoSuchElementException();
            E temp = iterNode.data;
            iterNode = iterNode.next;
            return temp;
        }
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    
    class Node<T>{
        public T data;
        public Node<T> next;
        
        public Node(T obj){
            data = obj;
            next = null;
        }
    }
    
    private int doCompare(E elem1, E elem2){
        return ( (Comparable<E>)elem1 ).compareTo(elem2);
    }
    
}

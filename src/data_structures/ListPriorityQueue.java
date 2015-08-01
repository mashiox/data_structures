/*  Matt Walther
    0211
*/

package data_structures;

import java.util.Iterator;

public class ListPriorityQueue<E> implements PriorityQueue<E> {
    private OrderedListADT<E> q;
    
    public ListPriorityQueue(){
        q = new OrderedList<E>();
    }
    
    /**
     * Insert a new object into the priority queue.
     * @param obj 
     */
    public void insert(E obj){
        q.insert(obj);
    }
    
    /**
     * Removes the object of highest priority that has been in the priority 
     * queue the longest, and returns it. Returns null if the queue is empty.
     * @return E
     */
    public E remove(){
        return q.removeMin();
    }
    
    /**
     * Returns the object of highest priority that has been in the queue the
     * longest but does not remove it. Returns null if the queue is empty.
     * @return E
     */
    public E peek(){
        return ( q.size() == 0 ? null : q.get(0) );
    }
    
    /**
     * Returns true if the priority queue contains an element that is equal to
     *  the input obj parameter as determined by the Comparable<E> interface.
     * @param obj
     * @return boolean
     */
    public boolean contains(E obj){
        return q.contains(obj);
    }
    
    /**
     * Returns the number of objects currently in the queue.
     * @return int
     */
    public int size(){
        return q.size();
    }
    
    /**
     * Returns the queue to an empty state.
     */
    public void clear(){
        q.clear();
    }
    
    /**
     * REturns true if the queue is empty, false otherwise.
     * @return boolean
     */
    public boolean isEmpty(){
        return q.isEmpty();
    }
    
    /**
     * Returns an iterator of the objects in the queue, in no particular order.
     * @return Iterator<E>
     */
    public Iterator<E> iterator(){
        return q.iterator();
    }
    
}

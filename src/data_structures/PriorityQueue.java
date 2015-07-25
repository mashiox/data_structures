/*  The PriorityQueue ADT may store objects in any order.  However,
    removal of objects from the PQ must follow specific criteria.
    The object of highest priority that has been in the PQ longest
    must be the object returned by the remove() method.  FIFO return
    order must be preserved for objects of identical priority.
   
    Ranking of objects by priority is determined by the Comparable<E>
    interface.  All objects inserted into the PQ must implement this
    interface.
*/   

package data_structures;

import java.util.Iterator;

public interface PriorityQueue<E> extends Iterable<E> {
    
    //  Inserts a new object into the priority queue.  
    public void insert(E object);   
   
    //  Removes the object of highest priority that has been in the
    //  PQ the longest, and returns it.  Returns null if the PQ is empty.
    public E remove();
   
    //  Returns the object of highest priority that has been in the
    //  PQ the longest, but does NOT remove it. 
    //  Returns null if the PQ is empty.
    public E peek();   
    
    //  Returns true if the priority queue contains an element 
    //  that is equal to the obj parameter as determined by 
    //  the Comparable<E> interface.
    public boolean contains(E obj); 
   
    //  Returns the number of objects currently in the PQ.
    public int size();
      
    //  Returns the PQ to an empty state.
    public void clear();
   
    //  Returns true if the PQ is empty, otherwise false
    public boolean isEmpty();
    
    //  Returns an iterator of the objects in the PQ, in no particular
    //  order.
    public Iterator<E> iterator();             
   
}
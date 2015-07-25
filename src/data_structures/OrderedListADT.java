/*  Your name
    Your rohan class account number
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface OrderedListADT<E> extends Iterable<E> {
    public static final int DEFAULT_MAX_CAPACITY = 100;

//  Adds the Object obj to the list in the correct position as determined by the Comparable interface.
    public void insert(E obj);

//  Removes and returns the object located at the parameter index position (zero based).
//  Throws IndexOutOfBoundsException if the index does not map to a valid position within the list.
    public E remove(int index);
    
//  Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
    public E remove(E obj);  
    
//  Removes and returns the smallest element in the list and null if the it is empty.
    public E removeMin(); 
    
//  Removes and returns the largest element in the list and null if the it is empty.
    public E removeMax();      

//  Returns the parameter object located at the parameter index position (zero based).
//  Throws IndexOutOfBoundsException if the index does not map to a valid position within the underlying array
    public E get(int index);
    
//  Returns the list object that matches the parameter, and null if the list is empty. 
//  Also returns null if the obj is NOT in the list.
//  This method is stable, if obj matches more than one element, the element that
//  has been in the list longest is returned.
    public E get(E obj); 
    
//  Returns the index of the first element that matches the parameter obj
//  and -1 if the item is not in the list.
    public int find(E obj);   

//  Returns true if the parameter object obj is in the list, false otherwise.
    public boolean contains(E obj);    

//  The list is returned to an empty state.
    public void clear();

//  Returns true if the list is empty, otherwise false
    public boolean isEmpty();

//  Returns the number of Objects currently in the list.
    public int size();
    
//  Returns an Iterator of the values in the list, presented in
//  the same order as the list.
    public Iterator<E> iterator();

}
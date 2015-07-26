/*  Matt Walther
    0211
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Comparator;

public class OrderedVector<E> implements OrderedListADT<E> {
    private E[] storage;
    private int arraySize,
                currentSize;
    
    public OrderedVector(){
        this.arraySize       = DEFAULT_MAX_CAPACITY;
        this.currentSize     = 0;
        this.storage         = (E[]) new Object[arraySize];
    }

    /**
     * Adds the Object obj to the list in the correct position as determined by the Comparable interface.
     * @param obj 
     */
    public void insert(E obj){
        int ip = this.findInsertionPoint(obj, 0, this.currentSize-1);
        if ( this.currentSize == this.arraySize ) this.increaseArraySize();
        this.shiftArrayRight(ip);
        this.storage[ip] = obj;
    }

    /**
     * Removes and returns the object located at the parameter index position (zero based).
     * Throws IndexOutOfBoundsException if the index does not map to a valid position within the list.
     * @param index
     * @return Removed element type E, or null given non-existence.
     */
    public E remove(int index){
        if ( index < 0 || index > this.currentSize-1 ) throw new IndexOutOfBoundsException();
        E temp = this.storage[index];
        this.shiftArrayLeft(index);
        if ( (this.currentSize << 2) <= this.arraySize ) this.decreaseArraySize();
        return temp;
    }
    
    /**
     * Removes and returns the parameter object obj from the list if the list contains it, null otherwise.
     * @param obj
     * @return Removed element type E, or null given non-existence.
     */
    public E remove(E obj){
        if ( !this.contains(obj) ) return null;
        int index = this.findLastInsertionPoint(obj, 0, this.currentSize-1);
        return this.remove(index);
    }
    
    /**
     * Removes and returns the smallest element in the list and null if the it is empty.
     * @return Removed element type E, or null given non-existence.
     */
    public E removeMin(){
        return ( this.currentSize == 0 ? null : this.remove(0) );
        //if ( this.currentSize == 0 ) return null;
        //return this.remove(0);
    }
    
    /**
     * Removes and returns the largest element in the list and null if the it is empty.
     * @return Removed element type E, or null given non-existence.
     */
    public E removeMax(){
        return ( this.currentSize == 0 ? null : this.storage[this.currentSize--] );
        //return this.storage[this.currentSize--];
    }

    /**
     * Returns the parameter object located at the parameter index position (zero based).
     * Throws IndexOutOfBoundsException if the index does not map to a valid position within the underlying array
     * @param index
     * @return Found element of type E, or null given non-existence.
     */
    public E get(int index){
        if (index < 0 || index > this.currentSize-1) throw new IndexOutOfBoundsException();
        return this.storage[index];
    }
    
    /**
     * Returns the list object that matches the parameter, and null if the list is empty. 
     * This method is stable, if obj matches more than one element, the element that has been in the list longest is returned.
     * @param obj
     * @return Found element of type E, or null given non-existence.
     */
    public E get(E obj){
        if ( this.currentSize == 0 ) return null;
        int index = this.find(obj);
        return ( index < 0 ? null : this.storage[index] );
    }
    
    /**
     * Returns the index of the first element that matches the parameter obj and -1 if the item is not in the list.
     * @param obj
     * @return Integer index of oldest element matching obj. 
     */
    public int find(E obj){
        if ( this.binSearch(obj, 0, this.currentSize-1) < 0 ) return -1;
        return this.findLastInsertionPoint(obj, 0, this.currentSize-1);
    }

    /**
     * Returns true if the parameter object obj is in the list, false otherwise.
     * @param obj
     * @return True given obj is in list.
     */
    public boolean contains(E obj){
        if ( this.currentSize == 0) return false;
        return ( this.binSearch(obj, 0, this.currentSize-1) < 0 ? false : true );
    }   

    /**
     * The list is returned to an empty state.
     */
    public void clear(){
        this.currentSize = 0;
    }

    /**
     * Returns true if the list is empty, otherwise false
     * @return True if the list is empty.
     */  
    public boolean isEmpty(){
        return ( this.currentSize > 0 ? false : true );
    }

    /**
     * Returns the number of Objects currently in the list.
     * @return Integer size of list
     */
    public int size(){
        return this.currentSize;
    }
    
    public Iterator<E> iterator(){
        return new InnerIterator();
    }
    
    class InnerIterator implements Iterator<E>{
        private int iterIndex;
        
        public InnerIterator(){
            this.iterIndex = 0;
        }
        
        public boolean hasNext(){
            return ( this.iterIndex < currentSize );
        }
        
        public E next(){
            if (!this.hasNext()) throw new NoSuchElementException();
            return storage[this.iterIndex++];
        }
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    
    private int doCompare( E elem1, E elem2 ){
        return ( (Comparable<E>)elem1 ).compareTo(elem2);
    }
    
    private int binSearch( E key, int lo, int hi){
        if (hi < lo) 
            return -1;
        int mid = (lo + hi)>>1;
        int result = this.doCompare(key, this.storage[mid]);
        if ( result == 0 ) {
            return mid;
        }
        if ( result < 0 ) {
            return this.binSearch(key, lo, mid-1);
        }
        return this.binSearch(key, mid+1, hi);
    }
    
    private int findInsertionPoint(E obj, int lo, int hi){
        if ( hi < lo ){
            return lo;
        }
        int mid = ( lo + hi ) >> 1;
        if ( this.doCompare(obj, this.storage[mid]) < 0 )
            return this.findInsertionPoint(obj, lo, mid-1);
        return this.findInsertionPoint(obj, mid+1, hi);
    }
    
    private int findLastInsertionPoint(E obj, int lo, int hi){
        if ( hi < lo) return lo;
        int mid = ( lo + hi ) >> 1;
        if ( this.doCompare(obj, this.storage[mid]) <= 0 ) 
            return this.findLastInsertionPoint(obj, lo, mid-1);
        return this.findLastInsertionPoint(obj, mid+1, hi);
    }
    
    private void increaseArraySize(){
        int tempSize = this.arraySize << 1,
            index = 0;
        E[] temp = (E[]) new Object[tempSize];
        Iterator<E> it = this.iterator();
        while ( it.hasNext() ) temp[index++] = it.next();
        this.storage = temp;
        this.arraySize = tempSize;
    }
    
    private void decreaseArraySize(){
        int tempSize = this.arraySize >> 1,
            index = 0;
        E[] temp = (E[]) new Object[tempSize];
        Iterator<E> it = this.iterator();
        while ( it.hasNext() ) temp[index++] = it.next();
        this.storage = temp;
        this.arraySize = tempSize;
    }
    
    /**
     * Shifts the right side of an array down by 1 at index and decrements the size.
     * @param index Shifting point of array.
     */
    private void shiftArrayLeft(int index){
        if ( index < 0 || index > this.currentSize-1 ) throw new IndexOutOfBoundsException();
        this.currentSize--;
        for ( int i = index ; i < this.currentSize ; i++ )
            this.storage[i] = this.storage[i+1];
    }
    
    /**
     * At some fixed point, index, shifts the array to the right, and increments the size.
     * @param index Shifting point of the array
     */
    private void shiftArrayRight(int index){
        if ( index < 0 || index > this.currentSize ) throw new IndexOutOfBoundsException();
        for ( int i = this.currentSize ; i > index ; i-- )
            this.storage[i] = this.storage[i-1];
        this.currentSize++;
    }

}
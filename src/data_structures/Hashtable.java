/*
 * Matt Walther
 * 0211
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable<K,V> implements DictionaryADT<K,V> {
    
    public int maxSize,
               tableSize,
               currentSize;
    private long sequenceNumber;
    private OrderedListADT<DictionaryNode<K,V>>[] list;
    
    /**
     * Constructor
     * @param size Size of table
     */
    public Hashtable(int size){
        currentSize = 0;
        maxSize = size;
        tableSize = (int)(maxSize * (1.3f));
        list = new OrderedList[tableSize];
        sequenceNumber = 0;
        for (int idx = 0 ; idx < tableSize ; idx++ )
            list[idx] = new OrderedList<DictionaryNode<K,V>>();
    }
    
    /**
     * Returns true if the dictionary has an object identified by
     * key in it, otherwise false.
     * @param key
     * @return 
     */
    public boolean contains(K key){
        return ( getValue(key) != null ? true : false );
    }

    /**
     * Adds the given key/value pair to the dictionary.  Returns
     * false if the dictionary is full, or if the key is a duplicate.
     * Returns true if addition succeeded.
     * @param key
     * @param value
     * @return 
     */
    public boolean insert(K key, V value){
        if ( isFull() ) return false;
        int index = getIndex(key);
        if ( list[index].contains(new DictionaryNode<K,V>(key,null) ) ) 
            return false;
        list[index].insert(new DictionaryNode<K,V>(key,value));
        currentSize++;
        sequenceNumber++;
        return true;
    }

    /**
     * Deletes the key/value pair identified by the key parameter.
     * Returns true if the key/value pair was found and removed,
     * otherwise false.
     * @param key
     * @return boolean
     */
    public boolean remove(K key){
        if ( !contains(key) ) return false;
        int index = getIndex(key);
        if ( list[index].remove( new DictionaryNode<K,V>(key,null) ) == null )
            return false;
        currentSize--;
        sequenceNumber++;
        return true;
    }

    /**
     * Returns the value associated with the parameter key.  Returns
     * null if the key is not found or the dictionary is empty.
     * @param key
     * @return V
     */
    public V getValue(K key){
        DictionaryNode<K,V> temp = 
                list[ getIndex(key) ].get( new DictionaryNode<K,V>(key,null) );
        return ( temp == null ? null : temp.value );
    }

    /**
     * Returns the key associated with the parameter value.  Returns
     * null if the value is not found in the dictionary.  If more
     * than one key exists that matches the given value, returns the
     * first one found.
     * @param value
     * @return K
     */
    public K getKey(V value){
        for ( int idx = 0 ; idx < tableSize ; idx++ )
            for ( DictionaryNode<K,V> n : list[idx] )
                if ( doCompareValues(value, n.value) == 0 ) return n.key;
        return null;
    }

    /**
     * Returns the number of key/value pairs currently stored
     * in the dictionary
     * @return int
     */
    public int size(){
        return currentSize;
    }

    /**
     * Returns true if the dictionary is at max capacity
     * @return boolean
     */
    public boolean isFull(){
        return (maxSize == currentSize);
    }

    /**
     * Returns true if the dictionary is empty
     * @return boolean
     */
    public boolean isEmpty(){
        return (currentSize == 0);
    }

    /**
     * Returns the Dictionary object to an empty state.
     */
    public void clear(){
        for ( int i = 0 ; i < tableSize ; i++ ) list[i].clear();
        currentSize = 0;
    }

    /**
     * Returns an Iterator of the keys in the dictionary, in ascending
     * sorted order.  The iterator is fail-fast.
     * @return Iterator<K>
     */
    public Iterator<K> keys(){
        return new KeyIterator();
    }
    
    class KeyIterator<K> extends IteratorHelper<K>{
        public KeyIterator(){
            super();
        }
        
        public K next(){
            return (K) nodes[index++].key;
        }
    }

    /**
     * Returns an Iterator of the values in the dictionary.  The
     * order of the values must match the order of the keys.
     * The iterator is fail-fast.
     * @return Iterator<V>
     */
    public Iterator<V> values(){
        return new ValueIterator();
    }
    
    class ValueIterator<V> extends IteratorHelper<V>{
        public ValueIterator(){
            super();
        }
        
        public V next(){
            return (V) nodes[index++].value;
        }
    }
    
    abstract class IteratorHelper<E> implements Iterator<E>{
        protected DictionaryNode<K,V> [] nodes;
        protected int index;
        protected long seqCheck;
        
        public IteratorHelper(){
            seqCheck = sequenceNumber;
            nodes = new DictionaryNode[currentSize];
            index = 0;
            int j = 0;
            for ( int i = 0 ; i < tableSize ; i++ )
                for ( DictionaryNode<K,V> n : list[i] )
                    nodes[j++] = n;
            shellSort();
        }
        
        public boolean hasNext(){
            if ( seqCheck != sequenceNumber ) 
                throw new ConcurrentModificationException();
            return ( index < currentSize );
        }
        
        public abstract E next();
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
        
        private void shellSort(){
            int in, out, h = 1;
            DictionaryNode<K,V> temp = null;
            while ( h <= currentSize ) h = 3*h + 1;
            while ( h > 0 ){
                for ( out = h ; out < currentSize ; out++ ){
                    temp = nodes[out];
                    in = out;
                    while ( in > h - 1 && nodes[in-h].compareTo(temp) >= 0 ){
                        nodes[in] = nodes[in-h];
                        in -= h;
                    }
                    nodes[in] = temp;
                }
                h = (h-1)/3;
            }
        }
        
        private DictionaryNode<K,V>[] quickSort(){
            return null;
        }
    }
    
    class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
        K key;
        V value;
        
        public DictionaryNode(K k, V v){
            key = k;
            value = v;
        }
        
        public int compareTo(DictionaryNode<K,V> node){
            return ((Comparable<K>)key).compareTo((K)node.key);
        }
    }
    
    private int getIndex(K key){
        return ( ( key.hashCode() & 0x7FFFFFFF ) % tableSize );
    }
    
    private int doCompareValues(V a, V b){
        return ( (Comparable<V>)a ).compareTo(b);
    }
    
    private int doCompareKeys(K a, K b){
        return ( (Comparable<K>)a ).compareTo(b);
    }
    
}

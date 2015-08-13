/*
 * Matt Walther
 * 0211
 */

package data_structures;

import java.util.Iterator;
import java.util.TreeMap;

public class BalancedTree<K,V> implements DictionaryADT<K,V> {
    TreeMap<K,V> tree;
    
    /**
     * Constructor
     */
    public BalancedTree(){
        tree = new TreeMap<K,V>();
    }
    
    /**
     * Returns true if the dictionary has an object identified by
     * key in it, otherwise false.
     * @param key
     * @return boolean
     */
    public boolean contains(K key){
        return tree.containsKey(key);
    }

    /**
     * Adds the given key/value pair to the dictionary.  Returns
     * false if the dictionary is full, or if the key is a duplicate.
     * Returns true if addition succeeded.
     * @param key
     * @param value
     * @return boolean
     */
    public boolean insert(K key, V value){
        if ( contains(key) ) return false;
        return ( tree.put(key, value) == null ? false : true );
    }

    
    /**
      * Deletes the key/value pair identified by the key parameter.
      * Returns true if the key/value pair was found and removed,
      * otherwise false.
      * @param key
      * @return boolean
      */
    public boolean remove(K key){
        return ( tree.remove(key) == null ? false : true );
    }

    /**
     * Returns the value associated with the parameter key.  Returns
     * null if the key is not found or the dictionary is empty.
     * @param key
     * @return V
     */
    public V getValue(K key){
        return tree.get(key);
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
        K kTmp = null;
        V vTmp = null;
        Iterator<K> itK = keys();
        Iterator<V> itV = values();
        while ( itK.hasNext() ){
            kTmp = itK.next();
            vTmp = itV.next();
            if ( doCompareValues(value, vTmp) == 0 )
                return kTmp;
        }
        return null;
    }

    /**
     * Returns the number of key/value pairs currently stored
     * in the dictionary
     * @return int
     */
    public int size(){
        return tree.size();
    }

    /**
     * Returns true if the dictionary is at max capacity
     * @return boolean
     */
    public boolean isFull(){
        return false;
    }

    /**
     *  Returns true if the dictionary is empty
     * @return boolean
     */
    public boolean isEmpty(){
        return tree.isEmpty();
    }

    /**
     * Returns the Dictionary object to an empty state.
     */
    public void clear(){
        tree.clear();
    }

    /**
     * Returns an Iterator of the keys in the dictionary, in ascending
     sorted order.  The iterator is fail-fast.
     * @return Iterator<K>
     */
    public Iterator<K> keys(){
        return tree.keySet().iterator();
    }

    /**
     * Returns an Iterator of the values in the dictionary.  The
     * order of the values must match the order of the keys.
     * The iterator is fail-fast.
     * @return Iterator<V>
     */
    public Iterator<V> values(){
        return tree.values().iterator();
    }
	
	private int doCompareValues(V a, V b){
        return ( (Comparable<V>)a ).compareTo(b);
    }
    
    private int doCompareKeys(K a, K b){
        return ( (Comparable<K>)a ).compareTo(b);
    }

}


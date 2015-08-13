/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 *
 * @author Matt
 */
public class BinarySearchTree<K,V> implements DictionaryADT<K,V>{
    
    private Node<K,V> root;
    public int currentSize;
    private long sequenceNumber;
    
    public BinarySearchTree(){
        root = null;
        currentSize = 0;
        sequenceNumber = 0;
    }
    
    // Returns true if the dictionary has an object identified by
    // key in it, otherwise false.
    public boolean contains(K key){
        return (find(key, root) != null);
    }

    // Adds the given key/value pair to the dictionary.  Returns
    // false if the dictionary is full, or if the key is a duplicate.
    // Returns true if addition succeeded.
    public boolean insert(K key, V value){
        if ( root == null ) root = new Node<K,V>(key,value);
        else insert(key, value, root, null, false);
        currentSize++;
        sequenceNumber++;
        return true;
    }
    
    private void insert(K k, V v, Node<K,V> n, Node<K,V> parent, 
            boolean wentLeft){
        if ( n == null ){
            if ( wentLeft ) parent.leftChild = new Node<K,V>(k, v);
            else parent.rightChild = new Node<K,V>(k, v);
        }
        else if ( doCompareKeys(k, n.key) < 0 ) 
            insert(k, v, n.leftChild, n, true);
        else insert(k, v, n.rightChild, n, false);
    }

    // Deletes the key/value pair identified by the key parameter.
    // Returns true if the key/value pair was found and removed,
    // otherwise false.
    public boolean remove(K key){    	
        if ( removeHelper(key) ){
            currentSize--;
            sequenceNumber++;
            return true;
        }
        return false;
        
    }
    
    private boolean removeHelper(K key){
        if ( root == null ) return false;
        Node<K,V> ntd = findNode(key, root);
        if ( ntd == null ) return false;
        Node<K,V> parent = findParent(key, root),
                  iOS = ntd.rightChild,
                  iOSp = null;
        while ( iOS != null && iOS.leftChild != null ){
            iOSp = iOS;
            iOS = iOS.leftChild;
        }
        if ( parent == null ){
            // handle root
            if ( root.leftChild == null && root.rightChild == null )
                root = null;
            else if ( root.rightChild == null )
                root = root.leftChild;
            else if ( root.leftChild == null )
                root = root.rightChild; 
            else {
            	if ( iOSp == null )
            		root.rightChild = iOS.rightChild;
            	else 
            		iOSp.leftChild = iOS.rightChild;
                root.key = iOS.key;
                root.value = iOS.value;
            }
        }
        else {
            boolean leftChild = isLeftChildNode(parent, ntd);
            if ( ntd.leftChild == null && ntd.rightChild == null ){//no kids
                if ( leftChild )
                	parent.leftChild = null;
                else
                	parent.rightChild = null;
            }
            else if ( ntd.leftChild == null ){// has right kid
                if ( leftChild ) // ntd exists on left of parent
                	parent.leftChild = ntd.rightChild;
                else // ntd exists on right of parent
                	parent.rightChild = ntd.rightChild;
            }
            else if ( ntd.rightChild == null ){// has left kid
                if ( leftChild ) // ntd exists on left of parent
                	parent.leftChild = ntd.leftChild;
                else // ntd exists on right of parent
                	parent.rightChild = ntd.leftChild;
            }
            else {//2 kids
            	if ( leftChild ){ // ntd exists on left of parent
            		if ( iOSp == null ){
            			ntd.key = iOS.key;
            			ntd.value = iOS.value;
                                ntd.rightChild = iOS.rightChild;
            		}
            		else {
            			ntd.key = iOS.key;
            			ntd.value = iOS.value;
                                iOSp.leftChild = iOS.rightChild;
            		}
            	}
            	else { // ntd exists on right of parent
            		if ( iOSp == null ){
            			ntd.key = iOS.key;
            			ntd.value = iOS.value;
            			ntd.rightChild = iOS.rightChild;
            		}
            		else {
            			ntd.key = iOS.key;
            			ntd.value = iOS.value;
            			iOSp.leftChild = iOS.rightChild;
            		}
            	}
            }
        }
        return true;
    }

    // Returns the value associated with the parameter key.  Returns
    // null if the key is not found or the dictionary is empty.
    public V getValue(K key){
        return find(key, root);
    }

    // Returns the key associated with the parameter value.  Returns
    // null if the value is not found in the dictionary.  If more
    // than one key exists that matches the given value, returns the
    // first one found.
    public K getKey(V value){
        if ( root == null ) return null;
        Node<K,V> node = null;
        Iterator<Node<K,V>> it = nodes();
        while ( it.hasNext() ){
            node = (Node<K,V>) it.next();
            if ( doCompareValues(value,node.value) == 0 ) return (K) node.key;
        }
        return null;
    }

    // Returns the number of key/value pairs currently stored
    // in the dictionary
    public int size(){
        return currentSize;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        return false;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        return ( currentSize == 0 );
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        root = null;
        currentSize = 0;
    }
    
    private V find(K key, Node<K,V> n){
        if ( n == null ) return null;
        int cmp = doCompareKeys(key, n.key);
        if ( cmp < 0 ) return find(key, n.leftChild);
        if ( cmp > 0 ) return find(key, n.rightChild);
        return (V) n.value;
    }
    
    private Node<K,V> findNode(K key, Node<K,V> n){
        if ( n == null ) return null;
        int cmp = doCompareKeys(key, n.key);
        if ( cmp == 0 ) return n;
        if ( cmp < 0 ) return findNode(key, n.leftChild);
        return findNode(key, n.rightChild);
    }
    private Node<K,V> findParent(K key, Node<K,V> n){
        if ( n == null ) return null;
        int cmp = doCompareKeys(key, n.key);
        
        if ( cmp == 0 ) return null; // parent is root
        
        if ( cmp < 0 ){
            if ( doCompareKeys(key, n.leftChild.key ) == 0 )
                return n;
            return findParent(key, n.leftChild);
        }
        
        if ( doCompareKeys( key, n.rightChild.key ) == 0 )
            return n;
        return findParent(key, n.rightChild);
    }
    
    /**
     * Quick and dirty check to indicate whether a child is left or right.
     * @param parent
     * @param child
     * @return boolean
     */
    private boolean isLeftChildNode(Node<K,V> parent, Node<K,V> child ){
        if ( parent.leftChild != null && parent.rightChild != null ){
            if ( doCompareKeys( parent.leftChild.key, child.key ) == 0 )
                return true;
            return false;
        }
        if ( parent.rightChild == null )
            return true;
        return false;
    }
    
    private Node<K,V> inOrderSuccessor(Node<K,V> n){
        if ( n != null ){
            n = n.rightChild;
            while ( n.leftChild != null )
                n = n.leftChild;
            return n;
        }
        return null;
    }
    
    // Returns an Iterator of the keys in the dictionary, in ascending
    // sorted order.  The iterator must be fail-fast.
    public Iterator<K> keys(){
        return new KeyIterator();
    }

    // Returns an Iterator of the values in the dictionary.  The
    // order of the values must match the order of the keys.
    // The iterator must be fail-fast.
    public Iterator<V> values(){
        return new ValueIterator();
    }
    
    private Iterator<Node<K,V>> nodes(){
        return new NodeIterator();
    }
    
    class KeyIterator<K> extends IteratorHelper<K>{
        public KeyIterator(){
            super();
        }
        
        public K next(){
            return (K) array[index++].key;
        }
    }
    
    class ValueIterator<V> extends IteratorHelper<V>{
        public ValueIterator(){
            super();
        }
        
        public V next(){
            return (V) array[index++].value;
        }
    }
    
    private class NodeIterator<T> extends IteratorHelper<T>{
        public NodeIterator(){
            super();
        }
        
        public T next(){
            return (T) array[index++];
        }
    }
    
    abstract class IteratorHelper<E> implements Iterator<E>{
        public Node<K,V> [] array;
        protected int index;
        protected long seqCheck;
        
        public IteratorHelper(){
            array = new Node[currentSize];
            index = 0;
            inOrderFillArray(root);
            index = 0;
        }
        
        public boolean hasNext(){
            //if ( seqCheck != sequenceNumber ) 
            //    throw new ConcurrentModificationException();
            return ( index < currentSize );
        }
        
        public abstract E next();
        
        public void remove(){
            throw new UnsupportedOperationException();
        }
        
        private void inOrderFillArray(Node<K,V> n){
            if ( n == null ) return;
            inOrderFillArray( n.leftChild );
            array[index++] = n;
            inOrderFillArray( n.rightChild );
        }
        
    }
    
    class Node<K,V> {
        private K key;
        private V value;
        private Node<K,V> leftChild,
                          rightChild;
        
        public Node(K k, V v){
            key = k;
            value = v;
            leftChild = rightChild = null;
        }
        
        public int compareTo(Node<K,V> node){
            return ((Comparable<K>)key).compareTo((K)node.key);
        }
    }
    
    private int doCompareValues(V a, V b){
        return ( (Comparable<V>)a ).compareTo(b);
    }
    
    private int doCompareKeys(K a, K b){
        return ( (Comparable<K>)a ).compareTo(b);
    }
    
    public void printT(){
        printA(root);
    }
    private void printA(Node<K,V> n){
        if (n==null)return;
        this.printA(n.leftChild);
        System.out.println(n.key);
        this.printA(n.rightChild);
    }

}

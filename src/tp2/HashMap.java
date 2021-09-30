package tp2;

import java.util.List;

public class HashMap<KeyType, DataType> {

    private static final int DEFAULT_CAPACITY = 20;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    /**
     * Constructeur par défaut
     */
    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    /**
     * Constructeur par parametre
     * @param initialCapacity
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity > 0 ? initialCapacity : DEFAULT_CAPACITY,
                DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructeur par parametres
     * @param initialCapacity
     * @param loadFactor
     */
    public HashMap(int initialCapacity, float loadFactor) {
        capacity = initialCapacity;
        this.loadFactor = 1 / loadFactor;
        map = new Node[capacity];
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType within map
     * @return Index value where this key should be placed in attribute map
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if map should be rehashed
     */
    private boolean needRehash() {
        return size * loadFactor > capacity;
    }

    /**
     * @return Number of elements currently in the map
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space for the map
     */
    public int capacity(){ return capacity; }

    /**
     * @return if map does not contain any element
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** TODO
     * Increases capacity by CAPACITY_INCREASE_FACTOR (multiplication) and
     * reassigns all contained values within the new map
     */
    private void rehash() {
        if (!this.needRehash()) return; //for safety

        Node<KeyType, DataType>[] oldMap = this.map;
        HashMap<KeyType, DataType> newMap = new HashMap<>(CAPACITY_INCREASE_FACTOR * oldMap.length); //voir si nextPrime

        for(int i = 0; i < oldMap.length; i++ ) {
            if (oldMap[i] != null) {
                newMap.map[i] = oldMap[i];
                newMap.size++;
            }
        }
        this.map = newMap.map;
        this.size = newMap.size;
        this.capacity = newMap.capacity;
    }

    public Node<KeyType, DataType> getNode(KeyType key){
        for (int i = 0; i < (this.map).length; i++) {
            HashMap.Node<KeyType, DataType> node = this.map[i];
            if (node != null && node.key == key) return node;
        }
       /* for (HashMap.Node<KeyType, DataType> node : this.map) {
            if (node != null && node.key == key) return node;
        }*/
        return null;
    }

    /** TODO
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        for (int i = 0; i < (this.map).length; i++) {
            HashMap.Node<KeyType, DataType> node = this.map[i];
            if (node != null && node.key == key) return true;
        }
        /*for (Node<KeyType, DataType> node: this.map) {
            if (node != null && node.key == key) return true;
        }*/
        return false;
    }

    /** TODO
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        if (this.getNode(key) == null) return null; // pas très beau, mais fonctionne
        return this.getNode(key).data;
    }

    /**TODO
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        DataType oldData = this.get(key);
        if (oldData != null) {
            for(int i = 0; i < this.map.length; ++i) {
                HashMap.Node<KeyType, DataType> node = this.map[i];
                if (node != null && node.key == key) {
                    node.data = value;
                    return oldData;
                }
            }
        }
        else {
            if (this.map[hash(key)] == null) {
                this.map[hash(key)] = new Node(key, value);
                if (++size > capacity)
                    rehash();
            }
            else {
                this.map[hash(key)].next = new Node(key, value);
            }
        }

        return oldData;
        /*DataType oldData = this.get(key);
        for (int i = 0; i < (this.map).length; i++) {
            HashMap.Node<KeyType, DataType> node = this.map[i];
            if (node != null && node.key == key) node.data = value;
        }
        for (Node<KeyType, DataType> node: map) {
            if (node != null && node.key == key) node.data = value;
        }
        return oldData;*/
    }

    /**TODO
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        Node<KeyType, DataType> currentNode = this.getNode(key);

        if (currentNode == null) return null;

        DataType oldData = currentNode.data;
        Node<KeyType, DataType> next =  currentNode.next;

        Node<KeyType, DataType> previous = null;
        for (Node<KeyType, DataType> node : map)
            if (node != null && node.next == currentNode) {
                previous = node;
                break; //we found what we were looking for so exit
            }

        if (previous != null) previous.next = next;
        currentNode = null;
        this.size--;


        return oldData;
    }

    /**TODO
     * Removes all nodes contained within the map
     */
    public void clear() {
        for (Node<KeyType, DataType> node: map) {
            if (node != null) this.remove(node.key);
        }
        this.size = 0; //for safety
    }

    /**
     * Definition et implementation de la classe Node
     */
    static class Node<KeyType, DataType> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }
    }
}
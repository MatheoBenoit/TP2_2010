package tp2;

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
        Node<KeyType, DataType>[] oldMap = map;

        //allocateMap( CAPACITY_INCREASE_FACTOR * oldMap.length ); //ecq on utilise nextPrime pour avoir un nombre premier
        size = 0;

        /*for(int i = 0; i < oldMap.length; i++ )
            if( oldMap[ i ] != null && oldMap[ i ].isActive )
                insert( oldMap[ i ].element );*/
    }

    public Node getNode(KeyType key){
        for (Node node: map) {
            if (node.key == key) return node;
        }
        return null;
    }

    /** TODO
     * Finds if map contains a key
     * @param key Key which we want to know if exists within map
     * @return if key is already used in map
     */
    public boolean containsKey(KeyType key) {
        for (Node node: map) {
            if (node.key == key) return true;
        }
        return false;
    }

    /** TODO
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        return (DataType)this.getNode(key).data;
    }

    /**TODO
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) { //A REVOIR
        DataType oldData = this.get(key); //sassurer que cest pas une reference
        for (Node node: map) { //on pourrait rappeler get et assigner une nouvelle valeur mais avant verifier la ref
            if (node.key == key) node.data = value;
        }
        return oldData;
    }

    /**TODO
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        DataType oldData = this.get(key); //sassurer que cest pas une referencede
        //this.getNode(key) = null; ??????????????????? cmt fkn delete, faudrait trouver celui qui pointe et le faire pointer vers celui dapres
        this.size -= 1;

        return oldData;
    }

    /**TODO
     * Removes all nodes contained within the map
     */
    public void clear() {
        for (Node node: map) {
            this.remove((KeyType) node.key);
        }
        this.size = 0;
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
package edu.frostburg.cosc310.RyanBrisbane.PokedexProject;

/**
 * The HashMap class. Contains all the methods for creation and associated operations
 */
public class HashMap {

    int maxsize;
    public final int size = 1000;
    double maxload = 0.75;

    HashEntry[] hashtable;

    /**
     * This little guy isn't absolutely necessary, but I found it was easier to have an object to
     * reference when accessing an entry instead of just the data values. Maybe that's wrong and
     * I should just be storing the data in the array but, as they say, it works on my machine
     */
    class HashEntry {
        public String key;
        public String entry;

        /**
         * HashEntry constructor
         * @param k - key value
         * @param e - data
         */
        HashEntry(String k, String e){
            key = k;
            entry = e;
        }

        /**
         * @return entry's key value
         */
        String getKey(){
            return key;
        }

        /**
         * @return entry's data values
         */
        String getEntry(){
            return entry;
        }
    }

    /**
     * HashMap constructor. Initializes all values of the array to null, so they can be changed
     * later when something is added or easily iterated over when searching through
     */
    public HashMap(){
        hashtable = new HashEntry[size];
        for(int i = 0; i < size; i++){
            hashtable[i] = null;
        }
    }

    /**
     *
     * @return number of values currently stored in the table
     */
    public int getCount(){
        int count = 0;
        for(int i = 0; i < hashtable.length; i++){
            if(hashtable[i] != null){
                count++;
            }
        }
        return count;
    }

    /**
     *
     * @return current load factor
     */
    public double getLF(){
        return getCount() / size;
    }

    /**
     *
     * @return max load factor
     */
    public double getMLF(){
        return maxload;
    }

    /**
     *
     * @return max size based upon the max load factor
     */
    public int getMaxSize(){
        maxsize = (int)(size * maxload);
        return maxsize;
    }

    /**
     * Inserts a new entry into the table
     * @param key - key value of the entry
     * @param entry - data values for the entry
     * @return true if successful, false if not
     */
    public boolean insert(String key, String entry){
        int probe = 0;
        do {
            int hash = hash(key, probe);
            if(hashtable[hash] == null){
                hashtable[hash] = new HashEntry(key, entry);
                return true;
            }
            probe++;
        } while(probe < maxsize);
        System.out.println("Hash table load exceeded: cannot add new entry " + key);
        return false;
    }

    /**
     * Search through the table to determine the existence of a given value in the table
     * @param key - entry's key
     * @return true if the value is there, false if not
     */
    public boolean contains(String key) {
        return contains(key, 0);
    }

    /**
     * This method is overloaded for ease of use during recursive calls. Having to mess with the
     * probe is annoying
     * @param key - entry's key
     * @param probe - current probe value of the iteration
     * @return true if value is present in table, false if not
     */
    public boolean contains(String key, int probe) {
        int hash = hash(key, probe);
        if(probe == maxsize && !hashtable[hash].key.equals(key)){
            return false;
        }
        if(hashtable[hash].key.equals(key) && hashtable[hash] != null){
            return true;
        }
        return contains(key, probe + 1);
    }

    /**
     * Instead of just checking if it exists, we have a method that finds the value AND returns it!
     * @param key - entry's key
     * @param probe - probe of the current iteration
     * @return the value if it is within the table, null otherwise
     */
    public HashEntry search(String key, int probe){
        int hash = hash(key, probe);
        if(probe > maxsize && !hashtable[hash].key.equals(key)){
            return null;
        }
        if(hashtable[hash].key.equals(key) && hashtable[hash] != null){
            return hashtable[hash];
        }
        return search(key, probe + 1);
    }

    /**
     * removes the provided value from the table (given it's there in the first place)
     * @param key - entry's key
     * @return true if removal succeeds, false if not
     */
    public boolean remove(String key){
        int probe = 0;
        int hash = hash(key, probe);
        if(search(key, probe).key.equals(key)){
            hashtable[hash] = null;
            return true;
        }
        return false;
    }

    /**
     * The hash function. uses java's hashCode() function and includes the probe because of
     * linear probing requirements. Sometimes returns a negative value so I took the absolute value
     * of the resulting number
     * @param key - entry's key
     * @param probe - probe on the current iteration
     * @return the hash of the given key + probe combo
     */
    private int hash(String key, int probe){
        return Math.abs((key.hashCode() + probe) % size);
    }
}

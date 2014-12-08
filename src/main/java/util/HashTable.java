package util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Xing HU on 11/19/14.
 */
public class HashTable {

    private LinkedList<Entry<String, Double>>[] hashTable;
    private int tableSize;
    private int numOfEntries;

    /**
     * Private Entry class
     * @param <K>
     * @param <V>
     */
    private class Entry<K, V> {
        private K key;
        private V value;
        private int count;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
            this.count++;
        }

        public int getCount() {
            return this.count;
        }
    }

    /**
     * Default constructor (table size = 10)
     */
    public HashTable() {
        this.tableSize = 10;
        this.numOfEntries = 0;
        this.hashTable = new LinkedList[this.tableSize];

        for (int idx = 0; idx < this.tableSize; ++idx)
            this.hashTable[idx] = new LinkedList<>();
    }

    /**
     * Constructor to create a HashTable with desired size
     * @param tableSize         Size of the HashTable
     */
    public HashTable(int tableSize) {
        this.tableSize = tableSize;
        this.numOfEntries = 0;
        this.hashTable = new LinkedList[tableSize];

        for (int idx = 0; idx < tableSize; ++idx)
            this.hashTable[idx] = new LinkedList<>();
    }

    /**
     * Put a pair of key and value into HashTable
     * @param key               Input key
     * @param value             Input value
     */
    public void put(String key, Double value) {
        LinkedList<Entry<String, Double>> bucket = hashTable[getBucketIdx(key)];
        Entry<String, Double> entry = get(key);

        // create new one in case it does not exist
        if (entry == null) {
            entry = new Entry<>(key, value);
            bucket.append(entry);
        } else {
            entry.setKey(key);
            entry.setValue(value);
        }

        ++numOfEntries;
    }

    /**
     * Get the entry from HashTable via its key
     * @param key               Key of the entry
     * @return                  Entry if exist, null otherwise
     */
    public Entry<String, Double> get(String key) {
        LinkedList<Entry<String, Double>> entries = hashTable[getBucketIdx(key)];
        // search for exist entry
        for (int idx = 0; idx < entries.size(); ++idx ) {
            if (entries.readElemAt(idx).getKey().equals(key))
                return entries.readElemAt(idx);
        }

        return null;
    }

    /**
     * Delete the entry via its key. Do nothing if the entry does not exist
     * @param key               Key of the entry
     */
    public void delete(String key) {
        LinkedList<Entry<String, Double>> bucket = hashTable[getBucketIdx(key)];
        Entry<String, Double> entry = get(key);

        if (entry != null) {
            bucket.deleteElemAt(bucket.findElem(entry));
            --numOfEntries;
        }
    }

    /**
     * Check if HashTable has the entry
     * @param key               Key of the entry
     * @return                  True: entry exists; False: entry does not exist
     */
    public boolean containsKey(String key) {
        return (get(key) != null);
    }

    /**
     * Check if the HashTable is empty
     * @return                  True: is empty; False: not empty
     */
    public boolean isEmpty() {
        return (numOfEntries == 0);
    }

    /**
     * Return the number of elements in the HashTable
     * @return                  Number of elements
     */
    public int size() {
        return numOfEntries;
    }

    /**
     * Return all keys in the HashTable
     * @return                  All keys in the HashTable
     */
    public Iterable<String> keys() {
        List<String> keys = new ArrayList<String>();
        for (int i = 0; i < tableSize; ++i) {
            if (hashTable[i] != null)
                for (int j = 0; j < hashTable[i].size(); ++j)
                    keys.add(hashTable[i].readElemAt(j).getKey());
        }

        return keys;
    }

    public void hashing(String path) throws IOException {
        // process all the files
        File[] files = new File(path).listFiles();
        for (File file : files) {
            FileReader fr = new FileReader(file);
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String name = line.replaceAll("[0-9]+", "").trim();
                    String score = line.replaceAll("[a-zA-Z\\. ]+", "").trim();

                    if (containsKey(name))
                        put(name, (get(name).getValue() + Double.valueOf(score)));
                    else
                        put(name, Double.valueOf(score));
                }
            }
        }
    }

    public Double getAverage(String key) throws Exception{
        if (containsKey(key))
            return get(key).getValue().doubleValue() / get(key).getCount();
        else
            return 0.0;
    }

    public Double getSum(String key) {
        if (containsKey(key))
            return get(key).getValue();
        else
            return 0.0;
    }

    /**
     * Obtain the bucket index from the key
     * @param key               Input key
     * @return
     */
    private int getBucketIdx(String key) {
        return Math.abs(key.hashCode()) % tableSize;
    }

    public static void main(String[] args) throws IOException {
        HashTable ht = new HashTable(50);

        // process all the files
        File[] files = new File("src/main/resources/NFL_data/test/").listFiles();
        for (File file : files) {
            FileReader fr = new FileReader(file);
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String name = line.replaceAll("[0-9]+", "").trim();
                    String score = line.replaceAll("[a-zA-Z\\. ]+", "").trim();

                    if (ht.containsKey(name))
                        ht.put(name, (ht.get(name).getValue() + Double.valueOf(score)));
                    else
                        ht.put(name, Double.valueOf(score));
                }
            }
        }

        System.out.print("Please input name: ");
        // get input
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        if (ht.containsKey(name)) {
            System.out.println("Average score: "
                    + ht.get(name).getValue().doubleValue() / ht.get(name).getCount());
        } else {
            System.out.println("Couldn't find the player.ß");
        }
    }

    public HashMap<String, Double> crossValidation(String path) throws IOException {
        HashMap<String, Double> map = new HashMap<>();

        // process all the files
        File[] files = new File(path).listFiles();
        for (File file : files) {
            FileReader fr = new FileReader(file);
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String name = line.replaceAll("[0-9]+", "").trim();
                    String score = line.replaceAll("[a-zA-Z\\. ]+", "").trim();

                    if (map.containsKey(name))
                        map.put(name, (map.get(name) + Double.valueOf(score)));
                    else
                        map.put(name, Double.valueOf(score));
                }
            }
        }

        return map;
    }

}

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class HashTable<T>  {

    // USING SEPARATE CHAINING
    private ArrayList<LinkedList<Entry>> table;
    private int capacity;
    private int numElements;
    private final double loadFactor = 0.75;

    public HashTable() {
        this.capacity = 11;
        table = new ArrayList<>();
        for (int i = 0; i < capacity; i++){
            table.add(new LinkedList<>());
        }
        numElements = 0;
    }

    public HashTable(int capacity){
        if (capacity < 0){
            throw new IllegalArgumentException("Error");
        }
        table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++){
            table.add(new LinkedList<>());
        }
        this.capacity = capacity;
    }

    public int size(){
        checkLoadFactor();
        int count = 0;
        for (int i = 0; i < table.size(); i++){
            LinkedList<Entry> entry = table.get(i);
            // if entry is not null then there is elements inside the linked list
            if (entry != null){
                count = count + entry.size();
            }
        }
        numElements = count;
        return count;
    }

    public T get(String key){
        int hashCode = Math.abs(key.hashCode() % table.size());
        LinkedList<Entry> list = table.get(hashCode);
        for (int i = 0; i < list.size(); i++) {
            Entry entry = list.get(i);
            if (entry.getKey().equals(key)) {
                return (T) entry.getValue();
            }
        }
        // key does not exist, return null
        return null;
    }


    private void checkLoadFactor(){
        double theLoadingFactor = (double) numElements / capacity;

        // if greater than 0.9, 90% slots are used => hash table grows by 2x
        if (theLoadingFactor > loadFactor){
            rehash();
        }
    }

    public void put(String key, T value) {
        checkLoadFactor();
        int hashCode = Math.abs(key.hashCode() % table.size());
        LinkedList<Entry> buckets = table.get(hashCode);
        for (int i = 0; i < buckets.size(); i++){
            Entry entry = buckets.get(i);
            if (entry.getKey().equals(key)) {
                if (entry.getValue() instanceof Integer){
                    Integer oldValue = (Integer) entry.getValue();
                    Integer newValue = oldValue + (Integer) value;
                    entry.setValue((T) newValue);
                }
                else {
                    entry.setValue(value);
                    return;
                }
            }
        }
        Entry entryIn = new Entry(key, value);
        table.get(hashCode).add(entryIn);
        numElements++;
    }


    private void rehash(){
        int oldSize = capacity;
        capacity = oldSize * 2;
        ArrayList<LinkedList<Entry>> newTable = new ArrayList<>(capacity);

        for (int i = 0; i < capacity; i++){
            newTable.add(new LinkedList<>());
        }

        // transfer
        for (int i = 0; i < oldSize; i++){
            LinkedList<Entry> buckets = table.get(i);
            for (Entry entry: buckets) {
                int index = Math.abs(entry.getKey().hashCode() % capacity);
                LinkedList<Entry> newBucket = newTable.get(index);
                newBucket.add(entry);
            }
        }
        table = newTable;
    }

    public T remove(String key) throws NoSuchElementException {
        int hashCode = Math.abs(key.hashCode() % table.size());
        LinkedList<Entry> buckets = table.get(hashCode);
        for (int i = 0; i < buckets.size(); i++) {
            Entry entry = buckets.get(i);
            if (entry.getKey().equals(key)) {
                buckets.remove(i);
                numElements--;
                return (T) entry.getValue();
            }
        }
        throw new NoSuchElementException();
    }

    public boolean containKeys(String key) {
        int hashCode = Math.abs(key.hashCode() % capacity);
        LinkedList<Entry> buckets = table.get(hashCode);
        for (int i = 0; i < buckets.size(); i++) {
            Entry entry = buckets.get(i);
            if (entry.getKey().equals(key)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public ArrayList<Entry> getEntries() {
        ArrayList<Entry> entries = new ArrayList<>();
        for (LinkedList<Entry> list : table) {
            entries.addAll(list);
        }
        return entries;
    }



    public  class Entry<T> implements Comparable<Entry<Integer>> {
            private String key;
            private T value;
            private Entry next;

            public Entry(String key, T value) {
                this.key = key;
                this.value = value;
                this.next = null;
            }

            public void setValue(T value) {
                this.value = value;
            }

            public T getValue() {
                return value;
            }

            public String getKey() {
                return key;
            }

            public Entry getNext(){
                return next;
            }

            public void setNext(Entry next){
                this.next = next;
            }

        @Override
        public int compareTo(Entry<Integer> o) {
            if ((Integer) getValue() > o.getValue()){
                return 1;
            }
            if ((Integer) getValue() < o.getValue()){
                return -1;
            }
            else{
                return 0;
            }
        }
    }
}











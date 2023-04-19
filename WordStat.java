import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WordStat {

    HashTable<Integer> wordCount;
    HashTable<Integer> wordRank;
    private int numberOfWords = 0;

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public WordStat(String file) throws IOException {
        Tokenizer tk = new Tokenizer(file);
        wordCount = new HashTable<>();
        ArrayList<String> wordList = tk.getWordList();

        // Adding words to the hashtable
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i);
            wordCount.put(word, 1);
            numberOfWords++;
        }

        ArrayList<HashTable.Entry> newArrayList = (ArrayList<HashTable.Entry>) wordCount.getEntries();
        Collections.sort(newArrayList);

        ArrayList temp = new ArrayList();

        // REMOVING DUPLICATES
        for (int i = 0; i < newArrayList.size(); i++){
            HashTable.Entry current = newArrayList.get(i);
            HashTable.Entry prev = null;
            if (!temp.isEmpty()){
                prev = (HashTable.Entry) temp.get(temp.size() - 1);
            }
            if(temp.isEmpty() || !prev.getKey().equals(current.getKey())){
                temp.add(current);
            }
        }
        newArrayList = temp;
        Collections.reverse(newArrayList);

        HashTable<Integer> ranksTable = new HashTable<>();

        int rank = 1;
        for (HashTable.Entry entry : newArrayList) {
            ranksTable.put(entry.getKey(), rank++);
        }

        this.wordRank = ranksTable;
    }

    public int wordCount(String word){

        Integer count = wordCount.get(word);
        if (count == null){
            return 0;
        }
        return count;
    }



    public int wordRank(String word){
        System.out.println("word: " + word);
        Integer rank = wordRank.get(word);
        if (rank == null) {
            return 0;
        }
        System.out.println("rank: " + rank);
        return rank;
    }

    public String[] KMostCommonWords(int k) {

        if (k < 0){
            throw new IllegalArgumentException();
        }

        ArrayList<HashTable.Entry> entries = (ArrayList<HashTable.Entry>) wordCount.getEntries();

        String[] mostCommonWords = new String[k];
        ArrayList<String> newList = removeDuplicates(entries);

        for (int i = 0; i < k; i++){
            mostCommonWords[i] = newList.get(i);
        }

        return mostCommonWords;
    }

    public String[] KLeastCommonWords(int k){
        if (k < 0){
            throw new IllegalArgumentException();
        }

        ArrayList<HashTable.Entry> entries = (ArrayList<HashTable.Entry>) wordCount.getEntries();
        entries.sort((e1, e2) -> ((Integer) e2.getValue()).compareTo((Integer) e1.getValue()));

        Collections.reverse(entries);

        String[] leastCommonWords = new String[k];
        ArrayList<String> newList = removeDuplicates(entries);

        for (int i = 0; i < k; i++){
            leastCommonWords[i] = newList.get(i);
        }

        return leastCommonWords;
    }

    private ArrayList<String> removeDuplicates(ArrayList<HashTable.Entry> arraylist) {
        ArrayList<String> newList = new ArrayList<>();

        for (HashTable.Entry entry: arraylist){
            String key = entry.getKey();
            if(!newList.contains(key)){
                newList.add(entry.getKey());
            }
        }
        return newList;
    }


}










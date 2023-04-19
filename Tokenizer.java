import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Tokenizer {

    private ArrayList<String> wordList;

    public Tokenizer(String file) throws IOException {
        // Create a new file reader
        FileReader fileReader = new FileReader(file);
        // create a buffered reader
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> wordList = new ArrayList<>();

        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            String[] words = line.split("[ \\n\\t\\r]+");
            for (int i = 0; i < words.length; i++){
                String normalized = normalize(words[i]);
                if (!normalized.isEmpty()){
                    wordList.add(normalized);
                }
            }
        }
        this.wordList = wordList;
    }

    public Tokenizer(String[] text){
        ArrayList<String> wordList = new ArrayList<>();

        for (int i = 0; i < text.length; i++){
            String normalized = text[i];
            String resultingString = normalize(normalized);
            wordList.add(resultingString);
        }
        this.wordList = wordList;
    }

    // Method that normalizes words
    public String normalize(String text){

        StringBuilder sb = new StringBuilder();

        if (text == null){
            return null;
        }

        for (int i = 0; i < text.length(); i++){
            if (Character.isLetterOrDigit(text.charAt(i))){
                sb.append(Character.toLowerCase(text.charAt(i)));
            }
        }
        return sb.toString();
    }

    public ArrayList<String> getWordList(){
        return wordList;
    }
}

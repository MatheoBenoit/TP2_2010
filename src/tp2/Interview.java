package tp2;

import java.io.*;
import java.util.HashMap;

public final class Interview {

    /** TODO
     * This function returns if the two texts are similar based on if they have a similar entropy of the HashMap
     * @return boolean based on if the entropy is similar
     */
    public static Double compareEntropies(String filename1, String filename2) throws IOException {
        HashMap<Character, Integer> firstMap = getFrequencyHashTable(readFile(filename1));
        HashMap<Character, Integer> secondMap = getFrequencyHashTable(readFile(filename2));

        Double firstEntropy = calculateEntropy(firstMap);
        Double secondEntropy = calculateEntropy(secondMap);

        return  Math.abs(firstEntropy - secondEntropy);
    }

    /** TODO
     * This function returns the difference in frequencies of two HashMaps which corresponds
     * to the sum of the differences of frequencies for each letter.
     * @return the difference in frequencies of two HashMaps
     */
    public static Integer compareFrequencies(String filename1, String filename2) throws IOException{
        HashMap<Character, Integer> firstMap = getFrequencyHashTable(readFile(filename1));
        HashMap<Character, Integer> secondMap = getFrequencyHashTable(readFile(filename2));

        Integer sum = 0;
        Integer sum1 = 0;
        Integer sum2 = 0;

        for (Character key : firstMap.keySet()) {
            Integer result = firstMap.get(key);
            sum1 = (result != null) ? result : 0;
            result = secondMap.get(key);
            sum2 = (result != null) ? result : 0;
            sum += Math.abs(sum1 - sum2);
        }
        for (Character key : secondMap.keySet()) {
            if (firstMap.get(key) == null)
                sum += secondMap.get(key);
        }

        return sum;
    }

    /** TODO
     * @return This function returns the entropy of the HashMap
     */
    public static Double calculateEntropy(HashMap<Character, Integer> map){
        double entropy = 0.0;
        double length = 0.0;

        for (Integer data : map.values()) {
            length += data;
        }
        for (Integer data : map.values()) {
            entropy += ((data / length) * ( Math.log( 1.0 / (data / length )) / Math.log( 2 ))); //to have log with base of 2
        }
        return entropy;
    }

    /**
     * This function reads a text file {filename} and returns the appended string of all lines
     * in the text file
     */
    public static String readFile(String filename) throws IOException {
        String text = "";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String tmp;

        while ((tmp = br.readLine()) != null) {
            text += tmp;
        }
        return text;
    }

    /** TODO
     * This function takes a string as a parameter and creates and returns a HashTable
     * of character frequencies
     */
    public static HashMap<Character, Integer> getFrequencyHashTable(String text) {
        HashMap<Character, Integer> map = new HashMap<> (text.length());

        for (int i = 0; i < text.length(); i++) {
            char character = text.charAt(i);
            if (isAlphabetic(character)){
                Integer freq = map.get(character);
                if (freq != null) {
                    map.put(character, ++freq);
                } else {
                    map.put(character, 1);
                }
            }
        }
        return map;
    }

    /**
     * This function takes a character as a parameter and returns if it is a letter in the alphabet
     */
    public static Boolean isAlphabetic(Character c){
        return Character.isAlphabetic(c);
    }
}

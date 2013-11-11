package net.mangawire.p2p.content;

import java.util.Iterator;
import java.util.Set;
import net.mangawire.parser.OneManga;

/**
 *
 * @author Pride
 */
public class SpellChecker {

    public static int SpellCheck(String incorrectWord, String wordToCompare) {
        char[] word1 = incorrectWord.toLowerCase().toCharArray();
        char[] word2 = wordToCompare.toLowerCase().toCharArray();
        int[][] matrix = new int[word1.length + 1][word2.length + 1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i;
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int y = 1; y < matrix[i].length; y++) {
                char a = word1[i - 1];
                char b = word2[y - 1];
                if (a == b) {
                    matrix[i][y] = matrix[i - 1][y - 1];
                } else {
                    matrix[i][y] = Math.min(Math.min(matrix[i][y - 1], matrix[i - 1][y - 1]), Math.min(matrix[i - 1][y], matrix[i - 1][y - 1])) + 1;
                }
            }
        }
        return matrix[word1.length][word2.length];
    }

    public static void main(String[] args) {
        String word = null;
        String searchString="Nrto";
        //set edit distance to maximum edit distance
        int editDistance = 4;
        Set set = OneManga.getAllManagas().keySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            String temp = (String) i.next();
            if(temp.charAt(0)==searchString.charAt(0)){
            int tempEditDistance = SpellChecker.SpellCheck(searchString, temp);
            if (tempEditDistance < editDistance) {
                editDistance = tempEditDistance;
                word = temp;
            }
            }
        }
        System.out.println("Did you mean " + word + "?");
    }
}

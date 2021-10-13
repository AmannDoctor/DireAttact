import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewDictionaryAttack {
    // solves hash given by user
    public static void dictionary_hash(String inputHash) throws FileNotFoundException {

        BufferedReader dictionary;
        Map<String, String> rainbowTable = new HashMap<>();
        ArrayList<String> half = new ArrayList<>();

        // load dictionary into a hashmap
        try {
            dictionary = new BufferedReader(new FileReader("Dictionary/dictionary.txt"));

            String line = dictionary.readLine();
            // iterate through each line
            while(line != null){

                // if password found break;
                if(attackNoSalt(line, inputHash)){
                    break;
                }
                rainbowTable.put(line, "");
                line = dictionary.readLine();
            }
            dictionary.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // attack with no salted password, returns true if found and prints the password
    public static boolean attackNoSalt(String nextLine, String inputHash){
        String dict_hash = hash(nextLine);
        if(dict_hash.equals(inputHash)){
            System.out.println("password is " + nextLine);
            return true;
        }

        return false;
    }

    // turn word into a hash
    public static String hash(String word) {
        String hexString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(word.getBytes());

            hexString = bytesToHexString(digest);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString;
    }

    public static String bytesToHexString(byte[] bytes) {
        String hexString = new String();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString += hex;
        }

        return hexString;
    }

}

/**
 * Class for the Dictionary Attack Assignment of
 * ITEC 4320: Internet Security
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;
public class DictionaryAttack {




    // DO NOT MODIFY THE FOLLOWING TWO METHODS

    /**
     * Computes the SHA-256 hash value of a string
     *
     * @param word  a string
     * @return      the hexadecimal representation of the SHA-256 hash value of word
     */
    public static String hash(String word)  {
        String hexString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(word.getBytes());

            hexString = bytesToHexString(digest);

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString;
    }

    /**
     * Converts a byte array to its hexadecimal representation
     *
     * @param bytes  an array of bytes
     * @return     the hexadecimal representation of the byte array
     */
    public static String bytesToHexString(byte[] bytes) {
        String hexString = new String();

        for (int i = 0;i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString += hex;
        }

        return hexString;
    }
    /**
     * Converts a list of output cracked into a txt file
     *
     * @param output an array of passwords that have been cracked
     * @param outPath The file name and location of the saved password
     * @return   The txt file with passwords
     */
    public static void saveOutText(List<String> output, String outPath){
        try {
            File o = new File(outPath);
            if (o.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File does exists.");
            }
            FileWriter w=new FileWriter(outPath);
            int i=0;
            for(String line:output){
                w.write("User"+i+": "+line+"\n");
                i++;
            }
            w.close();
        } catch (IOException e) {
            System.out.println("There has been an issue with saving the "+outPath+" file.");
            e.printStackTrace();
        }
    }

    /**
     * Takes in the path of the txt file and makes a dictionary out of it
     *
     * @param path File name and location of the saved password from the dictonary
     * @return   List of passwords from the dictionary
     */
    public static List<String> getDict (String path) {
        List<String> dict =new ArrayList<>();

        try {
            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){

                dict.add(st);

            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return dict;

    }
    /**
     * Takes in the path of the hpw txt file and makes a dictionary out of it
     *
     * @param hpwpath File name and location of the txt file that holds the hashed files
     * @return   List of passwords from the dictionary
     */
    public static List<String> getHpwInput (String hpwpath) {
        List<String> HpwList =new LinkedList<String>();
        try {
            File file = new File(hpwpath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String hpwline;

            while ((hpwline = br.readLine()) != null){
                String[] hpwsplit=hpwline.split(":  ");
                HpwList.add(hpwsplit[1]);
               // System.out.println(hpwsplit[1]);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return HpwList;

    }

    /**
     * Takes in Dictionary List and converts it into rainbow table
     *
     * @param dict Array of passwords from the smalldict.txt file
     * @return   Map of both the password and its hashed form from the dictionary
     */
public static Map<String,String> getRainbow(List<String> dict){
        HashMap<String,String> rain =new HashMap<>();
    for(String word: dict){
        rain.put(hash(word),word);
    }

        return rain;
    }
    /**
     * Takes in the rainbow table and checks if the hashed password is in there
     *
     * @param rainbowTable Map of original passwords and their hashed database
     * @return   Either the original password or the method sends back 'Not Found'
     */    public static String attackRainbow(Map<String,String> rainbowTable, String hpw){
                return rainbowTable.getOrDefault(hpw,"Not Found");
    }

    /**
     * Takes in the rainbow table and creates a file from the known passwords that are encrypted
     *
     * @param rainbowTable Map of original passwords and their hashed database
     * @param outPath File names and locations of the vehicle that left
     * @param hpwPath file, name, and data that are coming in
     * @return   Either the original password or the method sends back 'Not Found'
     */


    public static void attackNoSalt(Map<String,String> rainbowTable, String hpwPath, String outPath){
        List<String> hpwList=getHpwInput(hpwPath);

List<String> attackNoSalt=new LinkedList<>();
        for (String line:hpwList) {
            attackNoSalt.add(attackRainbow(rainbowTable,line));

        }
        saveOutText(attackNoSalt,outPath);

    }/**
     * Takes in the dictonary and checks if hashed and salted matched anything in the donation array
     * @param dict original dictionary of the passwords that are unhashed
     * @param hpw A hashed password
     * @param salt the salt that will help code the hash
   * @return   Either the original password or the method sends back 'Not Found'
     */
    public static String attackNoRainbow (List<String> dict, String hpw, String salt){
        List<String> pwdWsalt=new LinkedList<>();
        for(String pwd:dict){
            pwdWsalt.add(pwd+salt);
        }

        for(String salted:pwdWsalt){
            if(hash(salted).equals(hpw)){
                return salted;
            }
        }

        /**
         * for(String salted:pwdWsalt){
         *             if(hash(s+salt).equals(hpw)){
         *                 return s;
         *             }
         *         }
         *
         */
        return "Not Found";
    }

    /**
     * Takes in the dictionary and crakes the passwords through salted methods
     *
     *  @param dict original dictionary of the passwords that are unhashed
      * @param outPath File names and locations of the vehicle that left
    * @param hpwPath file, name, and data that are coming in
     * @return   Either the original password sent bacor the method sends back 'Not Found'
     */




    public static void attackSalt(List<String> dict, String hpwPath, String outPath){



        Map<String, String> hpwMap=getSaltedMap(getHpwInput(hpwPath));
        List<String> outList= new LinkedList<>();
        
      /**  List<String> hpwList=getHpwInput(hpwPath);
        List<String> output=new LinkedList<>();
        for (String hpw:hpwList) {
            String[] hs = hpw.split("  ");
            output.add(attackNoRainbow(dict,hs[0],hs[1]));
        }
        saveOutText(output,outPath);*/


    }

    public static Map<String, String> getSaltedMap(List<String> hpw){
        Map<String, String> saltedMap = new HashMap<>();
        for(String salted:hpw){
            String[] hs = salted.split("  ");
            saltedMap.put(hs[0],hs[1]);
        }
        return saltedMap;

    }



    /**
     * You are encouraged to write a main method to test your code.
     * The main method will not be graded.
     */
    public static void main(String[] args) {
       // System.out.println(hash("cporjjkn"));
List<String> disc=getDict("smalldict.txt");
       Map<String,String> r=getRainbow(disc);

        //System.out.println(getRainbow(disc));

       attackNoSalt(r, "test1.txt","NSalt.txt");
        attackSalt(disc,"test2.txt","YSalt.txt");
        



    }

}

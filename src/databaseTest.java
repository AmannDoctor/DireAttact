import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class databaseTest {

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        // time start
        long startTime = System.nanoTime();

        getPassword("2921e3d38c18022f3924165ec5252862576c390aa11aef99e659db5024680880");
        //getPasswordFromTextFile("58272d1f2278f20b60f4ced466a1e68df1c2b1bc45e7f7688fc64ab34b053cf1");

        // time end
        long endTime = System.nanoTime();




        long duration = (endTime - startTime) / 1000000;
        System.out.println("The execution time in milliseconds: " + duration);
    }

    public void insertPassword(String pass) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");

        // create statement for database
        Statement statement = conn.createStatement();
        //ResultSet resultSet = statement.executeQuery("SELECT * from passwords");
        //ResultSet resultSet = statement.executeQuery("INSERT INTO passwords");
        Statement stmt = conn.createStatement();
    }

    // get password from database
    public static void getPassword(String hash) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");
        System.out.println("Successfully Connected to the database!");

        Statement statement = conn.createStatement();
        String hashQuery = "SELECT `Password`,`PasswordHash` FROM `passworddb`.`passwords` WHERE `PasswordHash` = '" + hash+"'";

        ResultSet results = statement.executeQuery(hashQuery);

        String found = "";
        if (results.next()) {
            found = results.getString(1);
        }

        // if the hash is not found in the databaase, insert the new hash into the database
        if(found.equals("")){
            System.out.println("Hash not found within the database \nGenerating Hashes...\n");

        }

        System.out.println("password is : " + found);
    }

    // generate hash


    // solves hash given by user
    public static void getPasswordFromTextFile(String inputHash) throws FileNotFoundException {

        BufferedReader dictionary;
        Map<String, String> rainbowTable = new HashMap<>();
        ArrayList<String> half = new ArrayList<>();

        // load dictionary into a hashmap
        try {
            dictionary = new BufferedReader(new FileReader("Dictionary/realhuman_phill.txt"));

            String line = dictionary.readLine();
            // iterate through each line
            while (line != null) {

                // if password found break;
                if (attackNoSalt(line, inputHash)) {
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

    public static boolean attackNoSalt(String nextLine, String inputHash) {
        String dict_hash = hash(nextLine);
        if (dict_hash.equals(inputHash)) {
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
        String hexString = "";

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

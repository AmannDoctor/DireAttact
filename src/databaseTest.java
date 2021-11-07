import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class databaseTest {

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        /* Sample Hashes
        // 2921e3d38c18022f3924165ec5252862576c390aa11aef99e659db5024680880
        // 58272d1f2278f20b60f4ced466a1e68df1c2b1bc45e7f7688fc64ab34b053cf1
         */

        // sample password check: cporjjkn

        commands();
    }

    public void insertPassword(String pass) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");

        // create statement for database
        Statement statement = conn.createStatement();
        //ResultSet resultSet = statement.executeQuery("SELECT * from passwords");
        //ResultSet resultSet = statement.executeQuery("INSERT INTO passwords");
    }

    // get password from database
    public static void getPassword(String hash) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");
        System.out.println("Successfully Connected to the database!");

        Statement statement = conn.createStatement();
        String hashQuery = "SELECT `Password`,`PasswordHash` FROM `passworddb`.`passwords` WHERE `PasswordHash` = '" + hash + "'";
        //SELECT id

        ResultSet results = statement.executeQuery(hashQuery);

        String found = "";
        // returns the searched hash in the database
        while (results.next()) {
            if (results.getString(2).equals(hash)) {
                found = results.getString(1);
            }
        }

        // if the hash is not found in the database, insert the new hash into the database
        if (found.equals("")) {
            System.out.println("Hash not found within the database \nGenerating Hashes...\n");
            insertHash();
        } else {
            System.out.println("password is : " + found);
            return;
        }
    }

    // check if password is in the database
    /*
    If password is not in the database it will add the missing password with it's Hash
     */
    public static void checkPass(String pass) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");
        System.out.println("Successfully Connected to the database!");

        Statement statement = conn.createStatement();
        String hashQuery = "SELECT * FROM `passworddb`.`passwords` WHERE `Password` = '" + pass + "'";

        final PreparedStatement ps = conn.prepareStatement(hashQuery);
        ResultSet resultSet = ps.executeQuery();

        String found = "";
        // returns the searched hash in the database
        if (resultSet.next()) {
            String entry = resultSet.getString(1);
            if (entry.equals(pass)) {
                System.out.println(pass + " is in the Database");
            }
        } else {
            System.out.println("Password is not in the database adding and hashing to the database");
            statement = conn.createStatement();

            // get all passwords in the database that are UNHASHED
            //('" + pass + "','" + hash(pass) + "')"
            String insertPass = "INSERT INTO `passworddb`.`passwords` (Password, PasswordHash)" +
                    "VALUES ('" + pass + "','" + hash(pass) + "')";

            PreparedStatement pst1 = conn.prepareStatement(insertPass);
            pst1.addBatch(insertPass);
            pst1.executeLargeBatch();
            System.out.println("New password added: " + pass);
        }
    }

    // generate hash
    public static void insertHash() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");
        Statement statement = conn.createStatement();

        // get all passwords in the database that are UNHASHED
        String unhashed = "SELECT `Password`,`PasswordHash` FROM `passworddb`.`passwords` WHERE `PasswordHash` ='" + "' LIMIT 100000";
        PreparedStatement pst1 = conn.prepareStatement(unhashed);
        ResultSet resultSet = statement.executeQuery(unhashed);
        conn.setAutoCommit(false);
        try {
            while (resultSet.next()) {
                String found = resultSet.getString(1);

                // hashing passwords
                String sql = "UPDATE `passworddb`.`passwords` SET `PasswordHash` = '" + hash(found) + "' WHERE `Password` = '" + found + "'";
                pst1.addBatch(sql);
            }
            pst1.executeLargeBatch();
        } catch (Exception ignored) {

        }


        conn.commit();
        System.out.println("added to database");
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) as total FROM `passworddb`.`passwords` WHERE `PasswordHash` != ''");

        while (rs.next()) {
            String count = rs.getString("total");
            System.out.println("row count: " + count);
        }


    }

    // solves hash given by user
    public static void getPasswordFromTextFile(String inputHash) throws FileNotFoundException {
        System.out.println("finding Hashed password please wait");
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

    // Random Password Generator
    public static void randomPass(int passLength) throws SQLException {

        if (passLength <= 0){
            System.out.println("The Length of the password has to be greater than 0");
            return;
        }


        char[] allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789^$*.[]{}()?-!@#%&,><:;|_~".toCharArray();
        Random rand = new Random();

        StringBuilder newPass = new StringBuilder();

        System.out.println("Generating random password with a length of " + passLength);
        for(;passLength>0; passLength-=1){
            newPass.append(allCharacters[rand.nextInt(allCharacters.length)]);
        }
        System.out.println("The newly generated password is: " + newPass);
        checkPass(newPass.toString());
    }

    public static void commands() throws SQLException, FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String hash;
        while (true) {
            System.out.println("Enter a command: [db][text][passwordCheck][exit]");
            switch (in.nextLine().toLowerCase()) {
                case "database":
                case "db":
                    System.out.println("Enter a sample Hash here");
                    hash = in.nextLine();
                    long startTime = System.nanoTime();
                    getPassword(hash);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000000;
                    System.out.println("The execution time in milliseconds: " + duration);
                    break;
                case "text":
                case "tex":
                    System.out.println("Enter a sample Hash here");
                    hash = in.nextLine();
                    long startTimee = System.nanoTime();
                    getPasswordFromTextFile(hash);
                    long endTimee = System.nanoTime();
                    System.out.println("The execution time in milliseconds: " + (endTimee - startTimee) / 1000000);
                    break;
                case "password":
                case "pwdc":
                case "passwordcheck":
                    System.out.println("Enter a password: ");
                    checkPass(in.nextLine());
                    break;
                case "x":
                case "exit":
                    return;
                case "rp":
                case "rand":
                    System.out.println("Enter the length of the randomly generated password");
                    randomPass(in.nextInt());
                    break;
                default:
                    System.out.println("Enter a valid command");
                    System.out.println("[database][text]");
            }
        }

    }
}

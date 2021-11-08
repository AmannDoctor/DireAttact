import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class databaseTest {

    public static void main(String[] args) throws SQLException, FileNotFoundException {

        /* Sample Hashes
        // 2921e3d38c18022f3924165ec5252862576c390aa11aef99e659db5024680880
        // 58272d1f2278f20b60f4ced466a1e68df1c2b1bc45e7f7688fc64ab34b053cf1
        // ^ 40 second one
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
            System.out.println("Hash not found within the database");
        } else {
            System.out.println("password is : " + found);
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
            System.out.println("Hash: " + hash(pass));
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
                    System.out.println(attackNoSalt(line, inputHash));
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

        if (passLength <= 0) {
            System.out.println("The Length of the password has to be greater than 0");
            return;
        }


        char[] allCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789^$*.[]{}()?-!@#%&,><:;|_~".toCharArray();
        Random rand = new Random();

        StringBuilder newPass = new StringBuilder();

        System.out.println("Generating random password with a length of " + passLength);
        for (; passLength > 0; passLength -= 1) {
            newPass.append(allCharacters[rand.nextInt(allCharacters.length)]);
        }
        System.out.println("The newly generated password is: " + newPass);
        checkPass(newPass.toString());
    }

    // password Mixer using passwords from database
    public static void mixDB() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");
        System.out.println("Successfully Connected to the database!");

        // JDBC SQL Commands
        Statement statement = conn.createStatement();
        String hashQuery = "SELECT `Password`,`PasswordHash` FROM `passworddb`.`passwords` ORDER BY RAND() LIMIT 2";
        final PreparedStatement ps = conn.prepareStatement(hashQuery);
        ResultSet resultSet = ps.executeQuery();

        ArrayList<String> passwordList = new ArrayList<>();
        while (resultSet.next()) {
            passwordList.add(resultSet.getString(1));
        }
        System.out.println(passwordList.get(0));
        System.out.println(passwordList.get(1));
        System.out.println("Combined password: " + passwordList.get(0) + passwordList.get(1));
        checkPass(passwordList.get(0) + passwordList.get(1));
        System.out.println("\nReversing and adding password:\n");
        checkPass(passwordList.get(1) + passwordList.get(0));
        System.out.println("\n");
    }

    // password mixer using Mac's method
    public static void mixWords() throws SQLException {
        char[] sc = "/!*@#$%^&*()\"{}_[]|\\?/<>,.".toCharArray();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] alphabetUpper = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
        String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};
        String[] schools = new String[]{"Brookwood", "Chattahoochee", "Kennesaw", "Tree-Hill", "Newport", "Springfield", "Rosewood", "Riverdale", "Otter Bay", "Seven Seas", "East", "Baxter"};
        String[] colors = {"Red", "Pink", "Orange", "Green", "Blue", "Yellow", "Black", "Brown", "Purple", "White", "Aqua", "Aquamarine", "Almond", "Amethyst", "Chestnut", "Copper", "Coffee", "Emerald", "Flame", "Gold", "Iris", "Mustard", "Mystic", "Navy blue", "Redwood", "Rose", "Rust", "Sunset", "Tan", "Taupe", "Volt", "Neon", "Lime"};
        String[] petName = new String[]{"spot", "oliver", "rocky", "zeus", "penny", "duke", "max", "ruby", "luna", "buddy", "Damian"};
        char[] num = "1234567890".toCharArray();

        // read values into a singular arraylist
        ArrayList<String> commonWordValues = new ArrayList<>();
        Collections.addAll(commonWordValues, countries);
        Collections.addAll(commonWordValues, schools);
        Collections.addAll(commonWordValues, colors);
        Collections.addAll(commonWordValues, petName);
        for (char c : sc) {
            commonWordValues.add(Character.toString(c));
        }
        for (char c : alphabet) {
            commonWordValues.add(Character.toString(c));
        }
        for (char c : alphabetUpper) {
            commonWordValues.add(Character.toString(c));
        }
        for (char c : num) {
            commonWordValues.add(Character.toString(c));
        }

        // randomly generated password
        String pass = "";
        Random rand = new Random();
        int random_integer = rand.nextInt(4 - 1) + 1;
        for (; random_integer > 0; random_integer--) {
            int index = (int) (Math.random() * commonWordValues.size());
            pass += commonWordValues.get(index);
        }

        System.out.println("Newly generated password is: " + pass);
        checkPass(pass);
    }

    // Mac's code
    public static void generatePasswordInfo() throws SQLException {
        char[] sc = "/!*@#$%^&*()\"{}_[]|\\?/<>,.".toCharArray();
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};
        String[] schools = new String[]{"Brookwood", "Chattahoochee", "Kennesaw", "Tree-Hill", "Newport", "Springfield", "Rosewood", "Riverdale", "Otter Bay", "Seven Seas", "East", "Baxter"};
        String[] colors = {"Red", "Pink", "Orange", "Green", "Blue", "Yellow", "Black", "Brown", "Purple", "White", "Aqua", "Aquamarine", "Almond", "Amethyst", "Chestnut", "Copper", "Coffee", "Emerald", "Flame", "Gold", "Iris", "Mustard", "Mystic", "Navy blue", "Redwood", "Rose", "Rust", "Sunset", "Tan", "Taupe", "Volt  "};
        String[] petname = new String[]{"spot", "oliver", "rocky", "zeus", "penny", "duke", "max", "ruby", "luna", "buddy", "Damian"};
        char[] num = "1234567890".toCharArray();
        String[] p = new String[]{};
        Object[] combined;
        Password pass = new Password();
        int[] info=new int[]{0,0,0,0,0};
        double[] total=new double[]{0,0,0,0,0,0};


        int size = 0;
        int avg = 0;
        int tol = 0;


        // SQL commands
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/passworddb", "root", "bilikayo12");
        System.out.println("Successfully Connected to the database!");

        // JDBC SQL Commands
        Statement statement = conn.createStatement();
        String hashQuery = "SELECT `Password`,`PasswordHash` FROM `passworddb`.`passwords` ORDER BY RAND() LIMIT 10000";
        final PreparedStatement ps = conn.prepareStatement(hashQuery);
        ResultSet resultSet = ps.executeQuery();
        ArrayList<String> passwordList = new ArrayList<>();

        String pa = "";
        // loop through each password in the database
        while (resultSet.next()) {
            pa = resultSet.getString(1);
            passwordList.add(pa);
            size = size + pa.length();
            tol = tol + 1;
            total[0] = total[0] + 1;
            for (char c : pa.toCharArray()) {
                total[1] = total[1] + 1;
                if (Character.isDigit(c)) {
                    total[2] = total[2] + 1;
                }
                for (char s : sc) {
                    if (s == c) {
                        total[3] = total[3] + 1;
                    }
                }
                if (Character.isUpperCase(c)) {
                    total[4] = total[4] + 1;
                }
                if (Character.isLowerCase(c)) {
                    total[5] = total[5] + 1;
                }
            }
        }
        int a = (size / tol);
        // System.out.println("Average Size: "+a);

        String ma = Collections.max(passwordList, Comparator.comparing(String::length));
        String mi = Collections.min(passwordList, Comparator.comparing(String::length));

        Random r = new Random();
        int randompasssize = mi.length() + r.nextInt(ma.length() - mi.length());
        String pstring = "";
        for (int i = 0; i < randompasssize; i++) {


            float random = 1 + r.nextFloat() * (99);

            if (random <= ((float) ((total[2] * 100) / total[1]))) {
                pstring = pstring + "" + num[r.nextInt(num.length)];

            } else if (random <= ((float) ((total[2] * 100) / total[1]) + ((float) ((total[3] * 100) / total[1])))) {
                pstring = pstring + "" + sc[r.nextInt(sc.length)];

            } else if (random <= ((float) ((total[2] * 100) / total[1]) + ((float) ((total[3] * 100) / total[1])) + ((float) ((total[4] * 100) / total[1])))) {
                String u = "" + alphabet[r.nextInt(alphabet.length)];
                pstring = pstring + u.toUpperCase();

            } else if (random <= ((float) ((total[2] * 100) / total[1]) + ((float) ((total[3] * 100) / total[1])) + ((float) ((total[4] * 100) / total[1])) + ((float) ((total[5] * 100) / total[1])))) {
                pstring = pstring + "" + alphabet[r.nextInt(alphabet.length)];

            }


        }

        // check password and put it into the database
        checkPass(pstring);
    }

    public static void commands() throws SQLException, FileNotFoundException {
        Scanner in = new Scanner(System.in);
        String hash;
        while (true) {
            System.out.println("\nEnter a command: [db][mixdb][genpass][text][mixword][passwordCheck][exit][rp]");
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
                case "pc":
                case "passwordcheck":
                    System.out.println("Enter a password: ");
                    checkPass(in.nextLine());
                    break;
                case "x":
                case "exit":
                    return;


                // password Generators
                case "rp":
                case "rand":
                    System.out.println("Enter the length of the randomly generated password");
                    randomPass(in.nextInt());
                    break;
                case "mixdb":
                    // generates a password by combining 2 random passwords in the base
                    mixDB();
                    break;
                case "mixword":
                case "mixw":
                    // generates password based on common phrases, such states and colors
                    mixWords();
                    break;
                case "genpass":
                case "gpass":
                    // mac's portion
                    generatePasswordInfo();
                    break;
                default:
                    System.out.println("Enter a valid command");
                    break;
            }
        }

    }
}

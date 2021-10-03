import jdk.swing.interop.SwingInterOpUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.util.*;


public class PasswordHashMethod {

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
    public static String MD5(String word)  {
        String hexString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(word.getBytes());

            hexString = bytesToHexString(digest);

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    public static String getSHA512(String word)  {
        String hexString = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] digest = md.digest(word.getBytes());
            hexString = bytesToHexString(digest);

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString;
    }
    public static String saltMethod(String pass){
        GenerateSalt salt=new GenerateSalt();
        String s=salt.nextString();
        String out=hash(pass+s);
        return out+" "+s;
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

    public static void main(String[] args) {
        String a="BobbyNoNose";

        System.out.println(a);
        System.out.println(hash(a)+"\n Length: "+hash(a).length());
        System.out.println(MD5(a)+"\n Length: "+MD5(a).length());
        System.out.println(getSHA512(a)+"\n Length: "+getSHA512(a).length());
        System.out.println(saltMethod(a));
    }
}
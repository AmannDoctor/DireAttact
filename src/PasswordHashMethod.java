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

    public PasswordHashMethod(){

    }
    public String hashingOut(int i,String o){
        String hashedtxt="";
        if(i==1){
            hashedtxt=hash(o);
        }
        else if(i==2){
           hashedtxt= MD5(o);
        }
        else if(i==3){
            hashedtxt=getSHA512(o);
        }
        else if(i==4){
            hashedtxt=saltMethod(o);
        }
        return hashedtxt;

    }
    public List<String> hashList(List<String> o, int i){
        List<String> out=new ArrayList<>();
        for(int b=0;b<10;b++){
            out.add(hashingOut(i,o.get(b)));

        }
        return out;
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
       String a="TestPasswordEXP";
        GenerateSalt s=new GenerateSalt();
       // String a=s.nextString();


        System.out.println(a);
        System.out.println(hash(a)+"\n Hash Length: "+hash(a).length());
        System.out.println(MD5(a)+"\n MD5 Length: "+MD5(a).length());
        System.out.println(getSHA512(a)+"\n SHA512 Length: "+getSHA512(a).length());
        System.out.println(saltMethod(a)+"\n Salted Length: "+saltMethod(a).length());
    }
}
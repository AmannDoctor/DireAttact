import java.nio.charset.Charset;
import java.util.Random;

public class Password {
    String password;
    char[] unique={'\'','\"','\\','!','?',';',']','[','{',':','}','>','<','.','@','#','$','^'};
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    public Password(){


    }
    public Password(String password){
        this.password=password;
    }
    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Password p=new Password();
            p.ran(10);

            System.out.println(p.getPassword());
        }
    }
    public void ran(int n)
    {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);

        String randomString
                = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();



        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {

            if(n>0){

                r.append(randomString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        password= r.toString();
    }

}
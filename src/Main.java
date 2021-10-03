import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner in = new Scanner(System.in);
        String userInput;

        List<String> dict = DictionaryAttack.getDict("smalldict.txt");
        Map<String, String> passwordDictionary = DictionaryAttack.getRainbow(dict);


        while (true) {
            System.out.println("What is your command: ");
            userInput = in.nextLine();

            if (userInput.equals("x")) {
                System.out.println("Exiting program");
                break;
            } else if (userInput.equals("help")) {
                System.out.println("[password] command will ask the user to enter a single password hash and attempt to crack it");
                // todo: Help command
                ;
            } else if (userInput.equals("password")) {
                // todo: user types a hash and the program will try to figure out what the password is
                System.out.println("Enter a hash: ");

                // test hash = 58272d1f2278f20b60f4ced466a1e68df1c2b1bc45e7f7688fc64ab34b053cf1
                // test hash with salt = c5da5dc7a284564bc49d998e2940c600ad57eaa48cea838e5e7fd5a6e0dcacd3 15f4d84d2c010a30

                userInput = in.nextLine();

                // attempts to crack a hash you give it using a rainbow table

                // space separating the hash AND the SALT

                // if the user input has a hash AND a SALT with a space separating both of them it will attempt to crack it with salt
                if (userInput.contains(" ")) {
                    // todo: fix salt, it will print the plain-text password BUT ALSO THE SALT ATTACHED to it

                    String[] userInputArray = userInput.split(" ", 2);
                    String attempt = DictionaryAttack.attackWithSalt(dict, userInputArray[0], userInputArray[1]);

                    // remove salt value from output
                    attempt = attempt.replace(userInputArray[1], "");
                    System.out.println("password attempt is: " + attempt);
                }
                // attack with no salt
                else {
                    String attempt_password = DictionaryAttack.attackRainbow(passwordDictionary, userInput);
                    System.out.println("the attempted password is " + attempt_password);
                }
            }

        }

    }
}

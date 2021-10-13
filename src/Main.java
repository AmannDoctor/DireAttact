import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // write your code here
        Scanner in = new Scanner(System.in);
        String userInput;

        List<String> dict = DictionaryAttack.getDict("smalldict.txt");
        Map<String, String> passwordDictionary = DictionaryAttack.getRainbow(dict);

        // user command loop
        while (true) {
            System.out.println("What is your command: ");
            userInput = in.nextLine();

            if (userInput.equals("x")) {
                System.out.println("Exiting program");
                break;
            } else if (userInput.equals("test")) {
                // todo: fully implement and remove
                System.out.println("running large dictionary attack test");
                long startTime = System.nanoTime();
                NewDictionaryAttack.dictionary_hash("58272d1f2278f20b60f4ced466a1e68df1c2b1bc45e7f7688fc64ab34b053cf1");
                long endTime = System.nanoTime();

                long duration = (endTime - startTime) / 1000000;
                System.out.println("The execution time in milliseconds: " + duration);
            } else if (userInput.equals("help")) {
                System.out.println("-----");
                System.out.println("[x] to exit the program");
                System.out.println("[mixup] to remix a password that you put in");
                System.out.println("[password] command will ask the user to enter a single password hash and attempt to crack it");
                // todo: Help command
                ;
            }
            // attempts to crack a hash you give it using a rainbow table
            else if (userInput.equals("password")) {
                System.out.println("Enter a hash: ");

                // test hash= 58272d1f2278f20b60f4ced466a1e68df1c2b1bc45e7f7688fc64ab34b053cf1
                // test hash with salt = c5da5dc7a284564bc49d998e2940c600ad57eaa48cea838e5e7fd5a6e0dcacd3 15f4d84d2c010a30

                userInput = in.nextLine();

                // space separating the hash AND the SALT

                /*
                 if the user input has a hash AND a SALT with a space separating both of them,
                 it will attempt to crack it with salt
                 */
                if (userInput.contains(" ")) {

                    String[] userInputArray = userInput.split(" ", 2);

                    // time the execution
                    long startTime = System.nanoTime();
                    String attempt = DictionaryAttack.attackWithSalt(dict, userInputArray[0], userInputArray[1]);
                    long endTime = System.nanoTime();

                    long duration = (endTime - startTime) / 1000000;
                    System.out.println("The execution time in milliseconds: " + duration);


                    // remove salt value from output
                    attempt = attempt.replace(userInputArray[1], "");
                    System.out.println("password attempt is: " + attempt);
                }
                // attack with no salt
                else {
                    String attempt_password = DictionaryAttack.attackRainbow(passwordDictionary, userInput);
                    System.out.println("the attempted password is " + attempt_password);
                }
            } else if (userInput.equals("mixup")) {
                System.out.println("Enter a password to mix up");
                userInput = in.next();

                System.out.println("-------");
                System.out.println("Current password: " + userInput);

                // todo: leet code


                // todo: Caesar cipher
            }
        }
    }
}

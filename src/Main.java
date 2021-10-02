import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner in = new Scanner(System.in);
        String userInput;

        List<String> disc = DictionaryAttack.getDict("smalldict.txt");
        Map<String, String> passwordDictionary = DictionaryAttack.getRainbow(disc);


        while (true) {
            System.out.println("What is your command: ");
            userInput = in.nextLine();

            if (userInput.equals("x")) {
                System.out.println("Exiting program");
                break;
            } else if (userInput.equals("help")) {
                // todo: Help command
                ;
            } else if (userInput.equals("password")) {
                // todo: user types a hash and the program will try to figure out what the password is
                System.out.println("Enter a hash: ");

                userInput = in.nextLine();

                // attempts to crack a hash you give it
                String attempt_password = DictionaryAttack.attackRainbow(passwordDictionary, userInput);
                System.out.println("the attempted password is " + attempt_password);
            }

        }

    }
}

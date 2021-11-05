import java.io.*;
import java.util.*;
public class GenerateRandomPerson {
    String[] fnlist =  new String[] { "Adam", "Alex", "Aaron", "Ben", "Carl", "Dan", "David", "Edward", "Fred", "Frank", "Isabella", "Hal", "Hank", "Sharon", "John", "Jack", "Joe", "Larry", "Alexa", "Matthew", "Mark", "Nathan", "Otto", "Paul", "Peter", "Roger", "Olivia", "Steve", "Thomas", "Tim", "Ava", "Victor", "Mia"};
    String[] lnlist = new String[] {"McKay", "Love-Rose", "Haworth", "Avidan", "Solo", "Hanson", "Lee", "Lawless", "Lawicki", "Mccord", "McCormack", "Miller", "Myers", "Nugent", "Ortiz", "Orwig", "Ory", "Paiser", "Pak", "Pettigrew", "Quinn", "Quizoz", "Ramachandran", "Resnick", "Sandy", "Trebil", "Trusela", "Trussel", "Turco", "Stormclock", "Nadiva", "Ulrich", "Upson", "Vader", "Vail", "Valente", "Van", "Ventotla", "Vogal", "Wagle", "Wagner", "Wakefield", "Weinstein", "Weiss", "Woo", "Yang", "Yates", "Yocum", "Zeaser", "Zeller", "Ziegler", "Bauer", "Chapman", "Davidson", "Davis", "Dinkins", "Doran", "Love", "Dugan", "Baggins", "Ferry", "Fletcher", "Fietzer", "Hylan", "Hydinger", "Illingsworth", "Ingram", "Irwin",  "Jenson", "Johnson", "Johnsen", "Jones", "LeForge", "Lewis", "Linde", "Martin", "McGinnis", "Roberts", "Rogers", "Root", "Schmitt", "Thompson", "Tiernan"};
    int age;
    String[] movies;

    public String[] getLnlist() {
        return lnlist;
    }
    public GenerateRandomPerson(){

    }
    public Person genranper(){

        Random r= new Random();
        int ran=r.nextInt(fnlist.length);
        int ra=r.nextInt(lnlist.length);
        Person p=new Person();
        p.setFn(fnlist[ran]);
        p.setLn(lnlist[ra]);
        p.setBm(r.nextInt(12)+1);
        p.setBd(r.nextInt(28)+1);
        p.setBy(r.nextInt(2000-1980+1)+1980);
        return p;
    }


    public static void main(String[] args) {
        GenerateRandomPerson grp=new GenerateRandomPerson();
        for (int i = 0; i < 10; i++) {
            System.out.println(grp.genranper().toString());
        }
        System.out.println(grp.genranper().toString());

    }

}

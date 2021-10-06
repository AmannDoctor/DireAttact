import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestMethods {

    /*public static Map<String,String> genUserTable(){
        HashMap<String, String> o=new HashMap<>();
        for (int i = 0; i < 9; i++) {
            if()

        }


    }*/
    public static void checkUserTable(HashMap<String,String> a,HashMap<String,String> b){
        for(Map.Entry<String,String> en:a.entrySet()){
            if(b.containsKey(en.getKey())){
             String k= b.get(en.getKey());
                System.out.println(en.getKey());
             if (k.equalsIgnoreCase(en.getValue())){
                System.out.println("Pass and User match");
            }
            else{
                System.out.println("User match no pass");
            }
        }
            else {
                System.out.println("No matches");
            }
        }
    }
    public static String checkDoubleEn(String h){
        PasswordHashMethod p=new PasswordHashMethod();
        return "Double hashed";
    }

    public static void main(String[] args) {
        FileMethods fm=new FileMethods();
        PasswordHashMethod phm=new PasswordHashMethod();
        List<String> d=fm.getDict("attackDictionary.txt");


        for (int i = 1; i <5 ; i++) {

            String lm="userTestData"+i+".txt";
            HashMap<String,String> ut=fm.getUserTable(lm);
            System.out.println("\n\n User Table #"+i+": \n\n"+ut.toString());




        }


    }
}
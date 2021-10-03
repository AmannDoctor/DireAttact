import java.util.HashMap;
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
        HashMap<String,String> UserMap=new HashMap<>();
        UserMap.put("U1","134");
        UserMap.put("U3","3423");
        UserMap.put("U6","44444");
        HashMap<String,String> UsT=new HashMap<>();
        UsT.put("U1","134");
        UsT.put("U3","352");
        UsT.put("U2","4545444");

        checkUserTable(UserMap,UsT);
    }
}
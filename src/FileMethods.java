import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class FileMethods {

    public static List<String> getTxtList(String hpwpath) {
        List<String> HpwList =new LinkedList<String>();
        try {
            File file = new File(hpwpath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String hpwline;

            while ((hpwline = br.readLine()) != null){
                String[] hpwsplit=hpwline.split(": ");
                HpwList.add(hpwsplit[1]);
                // System.out.println(hpwsplit[1]);
            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        return HpwList;

    }






    public HashMap<String,String> getUserTable (String p){
        HashMap<String, String> userTable=new HashMap<>();
        try {
            File file = new File(p);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String hpwline;

            while ((hpwline = br.readLine()) != null){
                String[] hpwsplit=hpwline.split(": ");


              userTable.put(hpwsplit[0],hpwsplit[1]);

            }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return userTable;
    }
    /**
     * Takes in the path of the txt file and makes a dictionary out of it
     *
     * @param path File name and location of the saved password from the dictonary
     * @return   List of passwords from the dictionary
     */
    public List<String> getDict (String path) {
        List<String> dict =new ArrayList<>();

        try {
            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

                while ((st = br.readLine()) != null) {

                    dict.add(st);
                    if(dict.size()==1202867){
                        break;
                    }

                }

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


        return dict;

    }
    /**
     * Converts a list of output cracked into a txt file
     *
     * @param output an array of passwords that have been cracked
     * @param outPath The file name and location of the saved password
     * @return   The txt file with passwords
     */
    public static void saveOutText(List<String> output, String outPath){
        try {
            File o = new File(outPath);
            if (o.createNewFile()) {
                System.out.println("File created.");
            } else {
                System.out.println("File does exists.");
            }
            FileWriter w=new FileWriter(outPath);
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i=0; i<10; i++) {
                list.add(i);
            }
            Collections.shuffle(list);


            for(int i=0;i<5;i++){

                w.write("User"+list.get(i)+": "+output.get(i)+"\n");

            }
            w.close();
        } catch (IOException e) {
            System.out.println("There has been an issue with saving the "+outPath+" file.");
            e.printStackTrace();
        }
    }
    public static void hashingTxt(List<String> u,List<String> o,String outpath){
        try {
            File ot = new File(outpath);
            if (ot.createNewFile()) {
                System.out.println("File created.");
            } else {

                System.out.println("File does exists.");

            }
            FileWriter w=new FileWriter(outpath);


            int i=0;
            for(String t:u){

                w.write(t+": "+o.get(i)+"\n");
                i++;
            }
            w.close();
        } catch (IOException e) {
            System.out.println("There has been an issue with saving the "+outpath+" file.");
            e.printStackTrace();
        }


    }


    public static List<String> userList(){
        List<String> u=new ArrayList<>();
        for(int i=0;i<20;i++){
            u.add("User "+i);
        }
        Collections.shuffle(u);
        List<String> o=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            o.add(u.get(i));

        }

        return o;
    }
    public static String com(List<String> a, List<String> b){

        Collection<String> similar = new HashSet<String>( a );
        Collection<String> different = new HashSet<String>();
        different.addAll( a );
        different.addAll( b );

        similar.retainAll( b );
        different.removeAll( similar );
        int i=different.size();
        int o=similar.size();

        String out= i+" are not in file, "+o+" are in the file";
        return out;
    }
    public static void main(String[] args) {
        FileMethods fm=new FileMethods();
        PasswordHashMethod phm=new PasswordHashMethod();
        List<String> read=fm.getDict("attackDictionary.txt");
       // List<String> or=getDict("smalldict.txt");
      //  System.out.println(com(read,or));
        for (int i = 1; i <5 ; i++) {

                    String lm="userTestData"+i+".txt";

                    hashingTxt(userList(), phm.hashList(read,i),lm);


        }
    }


}
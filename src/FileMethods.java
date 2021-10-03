import java.io.*;
import java.util.*;

public class FileMethods {
    public static List<String> getTxtList(String hpwpath) {
        List<String> HpwList =new LinkedList<String>();
        try {
            File file = new File(hpwpath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String hpwline;

            while ((hpwline = br.readLine()) != null){
                String[] hpwsplit=hpwline.split(":  ");
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
    public static HashMap<String,String> getUserTable (String p){
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
    public static List<String> getDict (String path) {
        List<String> dict =new ArrayList<>();

        try {
            File file = new File(path);

            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){

                dict.add(st);

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

    public static void main(String[] args) {
        HashMap<String,String> ut=getUserTable("smdicusun.txt");
        HashMap<String,String> et=getUserTable("smdictuson.txt");
        TestMethods.checkUserTable(ut,et);


    }


}
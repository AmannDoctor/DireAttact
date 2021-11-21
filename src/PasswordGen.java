import java.nio.charset.Charset;
import java.util.*;

public class PasswordGen extends Password{


    char[] sc="/!*@#$%^&*()\"{}_[]|\\?/<>,.".toCharArray();
    char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    String[] countries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe", "Palestine"};
    String[] schools = new String[]{"Brookwood","Chattahoochee","Kennesaw","Tree-Hill","Newport","Springfield","Rosewood","Riverdale","Otter Bay","Seven Seas","East","Baxter"};
    String[] colors={"Red","Pink","Orange","Green","Blue","Yellow","Black","Brown","Purple","White","Aqua","Aquamarine","Almond","Amethyst","Chestnut","Copper","Coffee","Emerald","Flame","Gold","Iris","Mustard","Mystic","Navy blue","Redwood","Rose","Rust","Sunset","Tan","Taupe","Volt  "};
    String[] petname=new String[]{"spot","oliver","rocky","zeus","penny","duke","max","ruby","luna","buddy","Damian"};
    char[] num="1234567890".toCharArray();
    String[] p= new String[]{};
    Object[] combined;
    Password pass=new Password();
    int size=0;
    int avg=0;
    int tol=0;


    int[] info=new int[]{0,0,0,0,0};
    double[] total=new double[]{0,0,0,0,0,0};



    public PasswordGen(){
        List l= new ArrayList(Arrays.asList(sc));
        l.addAll(Arrays.asList(alphabet));
        l.addAll(Arrays.asList(num));


        combined=l.toArray();

    }

    public void genPersonPass(Person person){
        String m="";
        String d="";

        if(person.getBm()<10){
            m="0"+person.getBm();
        }
        else{
            m=""+person.getBm();
        }
        if(person.getBd()<10){
            d="0"+person.getBd();
        }
        else{
            d=""+person.getBd();
        }

        pass.setPassword(""+person.getFn().toUpperCase().charAt(0)+person.getLn().toUpperCase().charAt(0)+m+d);

    }
    public PasswordGen(String securityAnswer1,String securityAnswer2,String securityAnswer3){
        pass.setPassword(securityAnswer1+securityAnswer2+securityAnswer3);
    }
    public void setValue(int pos,int val){
        info[pos]=val;
    }


    public Password getPass() {
        return pass;
    }


    public void generatePasswordInfo(){

        FileMethods fm=new FileMethods();
        List<String> dic= fm.getDict("realhuman_phill.txt");
        for (String pa:dic) {

            size=size+pa.length();
            tol=tol+1;
            total[0]=total[0]+1;
            for (char c:pa.toCharArray()) {
                total[1]=total[1]+1;
                if(Character.isDigit(c)){
                    total[2]=total[2]+1;
                }
                for (char s:sc) {
                    if(s==c){
                        total[3]=total[3]+1;
                    }
                }
                if(Character.isUpperCase(c)){
                    total[4]=total[4]+1;
                }
                if(Character.isLowerCase(c)){
                    total[5]=total[5]+1;
                }
            }


        }
        int a=(size/tol);
       // System.out.println("Average Size: "+a);

        String ma=Collections.max(dic, Comparator.comparing(String::length));
        String mi=Collections.min(dic, Comparator.comparing(String::length));

        Random r = new Random();
        int randompasssize=mi.length()+r.nextInt(ma.length()-mi.length());


     //   System.out.println("Across "+total[0]+" passwords, there are an average of "+(total[1]/total[0])+" characters, of which "+(float)((total[2]*100)/total[1])+"% are digital, "+(float)((total[3]*100)/total[1])+"% are special characters, "+(float)((total[4]*100)/total[1])+"% are uppercase, and "+(float)((total[5]*100)/total[1])+"% are lower case.");
        String pstring="";
        for (int i = 0; i < randompasssize; i++) {



            float random = 1 + r.nextFloat() * (99);

            if (random <= ((float) ((total[2] * 100) / total[1]))) {
                pstring=pstring+""+num[r.nextInt(num.length)];

            } else if (random <= ((float) ((total[2] * 100) / total[1]) + ((float) ((total[3] * 100) / total[1])))) {
                pstring=pstring+""+sc[r.nextInt(sc.length)];

            } else if (random <= ((float) ((total[2] * 100) / total[1]) + ((float) ((total[3] * 100) / total[1])) + ((float) ((total[4] * 100) / total[1])))) {
                String u=""+alphabet[r.nextInt(alphabet.length)];
                pstring=pstring+u.toUpperCase();

            } else if (random <= ((float) ((total[2] * 100) / total[1]) + ((float) ((total[3] * 100) / total[1])) + ((float) ((total[4] * 100) / total[1])) + ((float) ((total[5] * 100) / total[1])))) {
                pstring=pstring+""+alphabet[r.nextInt(alphabet.length)];

            }


        }
        //System.out.println(pstring);
        pass.setPassword(pstring);



    }
    public void randomizeString(){

        Random r=new Random();
        ArrayList<String> l=new ArrayList<>();
        l.add(countries[r.nextInt(countries.length)].replace(" ",""));
        l.add(colors[r.nextInt(colors.length)].replace(" ",""));
        l.add(schools[r.nextInt(schools.length)].replace(" ",""));
        l.add(petname[r.nextInt(petname.length)]);
        GenerateRandomPerson grp= new GenerateRandomPerson();
        l.add(grp.randomFirstName());
        l.add(grp.randomLastName());


        Collections.shuffle(l);
        String o=l.get(0)+""+(r.nextInt(10000));
        //System.out.println(o);
        pass.setPassword(o);






    }
    public int[] getInfo() {
        return info;
    }

    public void generateTxtFile(int iter){

        for (int i = 0; i < iter; i++) {
            if(i%3==0){
                randomizeString();
            }
            else if(i%2==0){
                generatePasswordInfo();
            }
            else{
                GenerateRandomPerson grp = new GenerateRandomPerson();
                genPersonPass(grp.genranper());
            }
            System.out.println(pass.getPassword());

        }


    }



    public static void main(String[] args) {
        PasswordGen pg=new PasswordGen();

            pg.generateTxtFile(15);






    }


}

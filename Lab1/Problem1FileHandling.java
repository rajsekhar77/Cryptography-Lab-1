package Cryptography.Lab1;

import java.io.*;

public class Problem1FileHandling {
    public static void main(String[] args) throws Exception{

            //file creation
            String fName = "Crypto.txt";
            File f = new File(fName);

            //writing file
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            System.out.println("Writing the file.....");
            bw.write("I'm Raja Sekhar from CSE-Y");
            System.out.println("Writing Successfully done");
            bw.close();

            //reading file
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            System.out.println("Reading the file.....");
            System.out.println(line);

            //ascii value of each char printing
            String asciivalue;
            int charcount=0;
            System.out.println("Ascii Values of each Char");
            for(int i=0;i<line.length();i++) {
                if (!(line.charAt(i) == ' ')) {
                    charcount++;
                    System.out.print((int) line.charAt(i)+" ");
                }
            }

            //char count printing
             System.out.println();
             System.out.println("Char Count of file : "+ charcount);

    }
}

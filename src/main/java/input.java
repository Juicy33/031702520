import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class input {

    public static void main(String[] args) throws IOException {
        ArrayList<String > str = new ArrayList<>();        BufferedReader br = new BufferedReader(new FileReader("1.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("2.txt"));
        String line = null;
        while ((line=br.readLine())!=null){
            str.add(line);
        }
        bw.write('[');
        int i=0;
        while (i<str.size()) {
            lv1 cadress = new lv1();
            cadress.address = str.get(i);
            cadress.main();
            bw.write(cadress.last);
            if (i >= str.size() - 1) {
            } else {
                bw.write(',');
                bw.newLine();
            }
            i++;
        }
        bw.write(']');
        br.close();
        bw.close();
    }
}
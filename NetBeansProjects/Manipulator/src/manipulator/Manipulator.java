package manipulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Manipulator {
    static String ReadFromFile(File file) throws FileNotFoundException, IOException{
        BufferedReader in = new BufferedReader(new FileReader(file));
        String S = "";
        String temp = in.readLine();
        while (temp != null) {
            S += temp;
            S += '\n';
            temp = in.readLine();
        }
        S += '\0';
        in.close();
        return S;
    }
    
    static void WriteToFile(File file, String s) throws FileNotFoundException, IOException{
        BufferedWriter out = new BufferedWriter( new FileWriter(file));
        out.write(s);
        out.close();
    }
    
    static File SelectFile() throws FileNotFoundException{
        File file;
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter fltr = new FileNameExtensionFilter("*.txt", "txt");
        fileChooser.setFileFilter(fltr);
        fileChooser.setDialogTitle("Select the 1st file");
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            String filepath = file.getAbsolutePath();
            System.out.println("The path of the selected file is: " + filepath);
            return file;
        }else throw new FileNotFoundException();
    }
    
    public static void main(String[] args) {
        try{
            String S1 = ReadFromFile(SelectFile());
            String S2 = ReadFromFile(SelectFile());
            String from;
            String to;
            char c;
            while ( (c = S2.charAt(0)) != '\0'){
                from = "";
                while (c != ' '){
                    from += c;
                    S2 = S2.substring(1);
                    c = S2.charAt(0);
                }
                S2 = S2.substring(1);
                c = S2.charAt(0);
                to = "";
                while (c != '\n'){
                    to += c;
                    S2 = S2.substring(1);
                    c = S2.charAt(0);
                }
                S2 = S2.substring(1);
                S1 = S1.replaceAll(from, to);
            }
            WriteToFile(SelectFile(),S1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Encrypt {
    
    static void Transform(String inputFileName, String outputFileName, int K)throws FileNotFoundException, IOException {
        int c;
        FileReader input = new FileReader(inputFileName);
        FileWriter output = new FileWriter(outputFileName);
        
        while(in.ready()){
            c = (char)(in.read() + K);
            out.write(c);
        }
    }
}
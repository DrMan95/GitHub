/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import javax.swing.JOptionPane; 
import javax.swing.JFileChooser;
 
public class Downloader {
    static int download(String address) {
        try {
            URL url = new URL(address);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            File file;
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a file");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                String filepath = file.getAbsolutePath();
                System.out.println("The path of the selected file is: " + filepath);
            }else {
                throw new FileNotFoundException();
            }
            OutputStream out = new FileOutputStream(file);
            int c = in.read();
            while (c != -1) {
                out.write(c);
                c = in.read();
            }
            in.close();
            out.close();
        } catch (Exception e) {
            System. out .println(e);
            return -1;
        }
        return 0;
    }
    public static void main(String[] a) {
        long startTime = System.nanoTime();
        String toDownload =     JOptionPane.showInputDialog ("Δώστε την διεύθυνση ","");
        System. out .println(toDownload);
        download (toDownload);
        long endTime = System.nanoTime();
        long timeInNanosecs = endTime-startTime;
        long timeInSecs = timeInNanosecs/1000000000;
        System.out.println("total time: "+timeInNanosecs+" nano seconds.");
        System.out.println("total time: "+timeInSecs+" seconds.");

    }
} 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package drawfwin2;
import javax.swing.JOptionPane;

/**
 *
 * @author csd3357
 */
public class DrawFwin2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int i,j;
        int l = Integer.parseInt(JOptionPane.showInputDialog(
 "Give me a number 4 - 30",4));
        while(l>3 && l<31){
            String F = "";
            for (i=0 ; i<l ; i++){
                if (i==0)   for (j=0 ; j<l ; j++)   F = F + (i+1);
                else if (i==(int)(l/2)) for (j=0 ; j<i+1 ; j++)   F = F + (i+1);
                else    F = F + (i+1);
                F = F + "\n";
            }
            JOptionPane.showMessageDialog(null,F,"/ff",
 JOptionPane.INFORMATION_MESSAGE);
            l = Integer.parseInt(JOptionPane.showInputDialog(
 "Give me a number ",4));
        }JOptionPane.showMessageDialog(null,
 "I said 4 - 30",
 "Error",
 JOptionPane.INFORMATION_MESSAGE);
    }
    
}

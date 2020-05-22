package drawfwin;

import javax.swing.JOptionPane;

public class DrawFwin {
    public static void main(String[] args) {
        int i,j;
        int l = Integer.parseInt(JOptionPane.showInputDialog("Give me a number 4 - 30",4));
        while(l>3 && l<31){
            String F = "";
            for (i=0 ; i<l ; i++){
                if (i==0)   for (j=0 ; j<l ; j++)   F = F + "*";
                else if (i==(int)(l/2)) for (j=0 ; j<i+1 ; j++)   F = F + "*";
                else    F = F + "*";
                F = F + "\n";
            }
            JOptionPane.showMessageDialog(null,F,"/ff",JOptionPane.INFORMATION_MESSAGE);
            l = Integer.parseInt(JOptionPane.showInputDialog("Give me a number ",4));
        }
	JOptionPane.showMessageDialog(null,"I said 4 - 30","Error",JOptionPane.INFORMATION_MESSAGE);
    }
}

package drawf;

import java.util.Scanner;

public class DrawF {

    public static void main(String[] args) {
        int l = Integer.parseInt(args[0]);
        int i,j;
        Scanner in = new Scanner(System.in);
        while(l>3 && l<31){
            String F = "";
            for (i=0 ; i<l ; i++){
                if (i==0)   for (j=0 ; j<l ; j++)   F = F + "*";
                else if (i==(int)(l/2)) for (j=0 ; j<i+1 ; j++)   F = F + "*";
                else    F = F + "*";
                F = F + "\n";
            }
            System.out.print(F+'\n');
            l = in.nextInt();
        }
    }

}
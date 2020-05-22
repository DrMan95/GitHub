
package α1ε;

import java.util.Scanner;

public class Α1Ε {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int i,j;
        int[] A = new int[5];
        int[] B = new int[5];
        for(i=0 ; i<5 ; i++){
                System.out.println("Δώσε μου ένα ακέραιο:");
                A[i] = in.nextInt();
        }
        System.out.println("Έδωσες τους αριθμούς:");
        for (i=0 ; i<5 ; i++){
            System.out.println(A[i]);
        }
        for(i=0 ; i<5 ; i++){
            if((A[i]%2) == 1)   A[i] = A[i]*A[i];
        }
        System.out.println("Τα νέα περιεχόμενα του πίνακα:");
        for(i=0 ; i<5 ; i++){
            System.out.println(A[i]);
        }
        for(i=0 ; i<5 ; i++){
            B[i] = A[4-i];
        }
        System.out.println("τα περιεχόμενα του πίνακα Β:");
        for(i=0 ; i<5 ; i++){
            for(j=0 ; j<=(i) ; j++)   System.out.print("<");
            System.out.print(B[i]);
            for(j=0 ; j<=(i) ; j++)   System.out.print(">");
            System.out.println();
        }
    }
}

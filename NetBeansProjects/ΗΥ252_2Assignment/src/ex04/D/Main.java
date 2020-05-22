package ex04.D;

import java.awt.EventQueue;
import javax.swing.JFrame;
/**
 *
 * @author yannakis
 * Main for A2.4
 */
public class Main {

    /**
     * Main class that initialize the VendingMachine class and the JFrame
     * @param args 
     */
    public static void main(String[] args) {
        final MyVendingMachineclass vm=new MyVendingMachineclass(); //your VendingMachine class
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                //Create our java graphic window
                Graphics gboard=new Graphics(vm);
                gboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gboard.setSize(600, 400);
                gboard.setVisible(true);

            }
         });
    } 
}

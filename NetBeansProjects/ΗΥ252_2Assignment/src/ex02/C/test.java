package ex02.C;

import ex02.B.*;
import java.util.Scanner;

public class test {
    public static void main(String[] tralala){
        int i;
        int pin,newpin;
        Scanner in = new Scanner(System.in);
        eWallet wallet = new eWallet(1000);
        wallet.addMoney(100);
        pin = in.nextInt();
        wallet.setPin(pin);
        for(i=0; i<5; i++){
            wallet.addMoney(250);
        }
        System.out.println("Give me your old pin.");
        pin = in.nextInt();
        System.out.println("Give me your new pin.");
        newpin = in.nextInt();
        wallet.changePin(newpin,pin);
        System.out.println("Give me your old pin.");
        pin = in.nextInt();
        System.out.println("Give me your new pin.");
        newpin = in.nextInt();
        wallet.changePin(newpin,pin);
        for(i=0 ; i<4 ; i++){
            wallet.buy(400);
        }
        wallet.seeMoney();
    }
}

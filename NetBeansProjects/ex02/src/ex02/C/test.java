package ex02.C;

import ex02.B.*;

public class test {
    public static void main(String[] tralala){
        int i;
        eWallet wallet = new eWallet(1000);
        wallet.addMoney(100);
        wallet.setPin(1234);
        for(i=0; i<5; i++){
            wallet.addMoney(250);
        }
        wallet.changePin(2222,1111);
        wallet.changePin(4321,1234);
        for(i=0 ; i<4 ; i++){
            wallet.buy(400);
        }
        wallet.seeMoney();
    }
}

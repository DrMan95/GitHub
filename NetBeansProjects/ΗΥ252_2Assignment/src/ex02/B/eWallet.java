package ex02.B;

import java.util.Scanner;

public class eWallet extends eWalletADT {
    private int pin;
    private final double maxSpace;
    private double money;
    
    public eWallet(double maxSpace){
        this.maxSpace = maxSpace;
        pin = 0;
        money = 0;
    }
    @Override
    public int getPin(){
        System.out.println("Give me yor password.");
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
    @Override
    public void setPin(int pin){
        if(!this.changePin(pin, 0)){
            System.out.println("Password already declared.");
        }
    }
    @Override
    public boolean unlock(int pin){
        if (this.pin == pin){
            return true;
        }
        return false;
    }
    @Override
    public boolean changePin(int newPin, int oldPin){
        if(this.pin == oldPin){
            this.pin = newPin;
            System.out.println("Password has been changed.");
            return true;
        }else{
            System.out.println("Incorect password.");
            return false;
        }
    }
    @Override
    public boolean addMoney(double add){
        if(this.pin == 0){
            System.out.println("No password have been declared.");
            System.out.println("Failed to add "+add+" money.");
            return false;
        }else{
            if(unlock(getPin())){
                if(this.maxSpace >= this.money+add){
                    this.money+=add;
                    System.out.println("Add succesed.");
                    return true;
                }else{
                    System.out.println("Not enough space in the wallet.");
                    return false;
                }
            }else{
                System.out.println("Incorect password.");
                return false;
            }
        }
    }
    /**
     * check the money that the wallet has at this point if the pin is correct
     * @return true and print the money that the wallet has at this point if 
     * the pin was correct, false otherwise
     */
    @Override
    public boolean seeMoney(){
        if(this.pin == 0){
            System.out.println("No password have been declared.");
            return false;
        }else{
            if(unlock(getPin())){
                System.out.println("You have "+this.money+" at your account.");
                return true;
            }else{System.out.println("Incorect password.");}
        }return false;
    }
    /**
     * remove money from the wallet if the pin is correct
     * @param cost the price the needs to be removed from the wallet
     * @return true if the wallet had this money and the pin was correct 
     * false otherwise
     */
    @Override
    public boolean buy(double cost){
        if(this.pin == 0){
            System.out.println("No password have been declared.");
            System.out.println("Failed to buy.");
            return false;
        }else{
            if(unlock(getPin())){
                if(this.money - cost < 0){
                    System.out.println("Not enough money. poor kid :( ");
                    return false;
                }else{
                    this.money -= cost;
                    System.out.println("Buy succesed.");
                    return true;
                }
            }else{
                System.out.println("Incorect password.");
                return false;
            }
        }
    }
}

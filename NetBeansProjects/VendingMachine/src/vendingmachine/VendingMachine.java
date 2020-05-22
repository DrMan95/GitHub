package vendingmachine;

import java.util.ArrayList;

public class VendingMachine implements VendingMachine_ADT{
    private ArrayList<Coin> bank;
    private Drink drink;
    private State state;
    /*    private ArrayList<cola> cola;
    private ArrayList<colaZero> colazero;
    private ArrayList<sprite> sprite;
    private ArrayList<zelita> zelita;
    private ArrayList<fanta> fanta;*/
    
    public VendingMachine(){
        this.bank = new ArrayList<>();
        this.state = State.IDLE;
        this.drink = null;
        /*        this.cola = new ArrayList<>();
        this.colazero = new ArrayList<>();
        this.sprite = new ArrayList<>();
        this.zelita = new ArrayList<>();
        this.fanta = new ArrayList<>();*/
    }
    
    @Override
    public boolean selectDrink(Drink d) {
        if(d.getStockNumber() > 0){
            this.drink = d;
            if(this.state == State.MoneyEntered){
                this.state = State.DrinkSelectedandMoneyEntered;
                this.updateDisplay();
                return true;
            }else{
                this.state = State.DrinkSelected;
                this.updateDisplay();
                return true;
            }
        }else {
            this.updateDisplay();
            return false;
        }
    }

    @Override
    public boolean insertCoin(Coin c) {
        this.bank.add(c);
        if(this.state == State.DrinkSelected){
            this.state = State.DrinkSelectedandMoneyEntered;
            this.updateDisplay();
            return true;
        }else{
            this.state = State.MoneyEntered;
            this.updateDisplay();
            return true;
        }
    }

    @Override
    public void updateDisplay() {
        int i;
        double sum=0;
        for (i=0; i<this.bank.size(); i++) {
            sum += this.bank.get(i).value;
        }
        System.out.println("===============================================================");
        System.out.println(this.state.toString());
        if(this.drink == null){
            System.out.println("You haven't choose a drink yet.");
        }else{
            System.out.println("You have choose: "+this.drink.toString());
            if(this.drink.getPrice()>sum){
                System.out.println("You need to insert "+(this.drink.getPrice()-sum)+"€ more.");
            }else System.out.println("You are ready to press \"ok\".");
            
            if(this.drink.getPrice()<sum){
                System.out.println("And I will return you "+(sum-this.drink.getPrice())+"€.");
            }
        }
        System.out.println("You have inserted: "+sum+"€");
        
        System.out.println("===============================================================");
    }

    @Override
    public ArrayList<Coin> returnMoney() {
        ArrayList<Coin> ret = new ArrayList<>();
        while(!this.bank.isEmpty()){
            ret.add(this.bank.remove(0));
        }
        return ret;
    }

    @Override
    public void cancel() {
        this.state = State.IDLE;
        this.drink = null;
        if(!this.bank.isEmpty())    this.returnMoney();
        this.updateDisplay();
    }

    @Override
    public boolean ok() throws IllegalArgumentException{
        int i;
        double sum = 0;
        if(!(this.state == State.DrinkSelectedandMoneyEntered)){
            this.updateDisplay();
            return false;
        }
        for (i=0; i<this.bank.size(); i++) {
            sum += this.bank.get(i).value;
        }
        if(sum < this.drink.getPrice()){
            this.updateDisplay();
            return false;
        }
        if(sum != this.drink.getPrice()) this.returnChange();
        this.deliverDrink();
        this.drink = null;
        this.state = State.IDLE;
        this.updateDisplay();
        return true;
    }

    @Override
    public State getState() {return this.state;}

    @Override
    public ArrayList<Coin> returnChange() throws IllegalArgumentException{
        double sum = 0;
        double c;
        int i;
        ArrayList<Coin> ret = new ArrayList<>();
        for (i=0; i<this.bank.size(); i++) {
            sum += this.bank.get(i).value;
        }
        c = sum - this.drink.getPrice();
        while(c > 0.5){
            if(c >= 2){
                ret.add(new Coin(2));
                c-=2;
            }else if(c >= 1){
                ret.add(new Coin(1));
                c-=1;
            }else if(c >= 0.5){
                ret.add(new Coin(0.5));
                c-=0.5;
            }else if(c >= 0.2){
                ret.add(new Coin(0.2));
                c-=0.2;
            }else if(c >= 0.1){
                ret.add(new Coin(0.1));
                c-=0.1;
            }else{
                ret.add(new Coin(0.05));
                c-=0.05;
            }
        }
        return ret;
    }

    @Override
    public Drink deliverDrink() {
        return this.drink.giveDrink();
    }
    
}

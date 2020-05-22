package ex04.AB;

public abstract class Drink {
    private double price;
    
    public Drink(double price){
        this.price = price;
    }
    
    public abstract int getStockNumber();
    abstract void upraiseStockNumber();
    abstract public Drink giveDrink();

    public double getPrice() {return this.price;}
    public void setPrice(double p){this.price = p;}
    
    public static Drink strToDrink(String str){
        if (str.equals("cocacola")) {return new cola();}
        if (str.equals("cocacolazero")){return new colaZero();}
        if (str.equals("fanta")) {return new fanta();}
        if (str.equals("zelita")) {return new zelita();}
        if (str.equals("sprite")) {return new sprite();}
        return null;
    }
    
    abstract public String getName();
    
    @Override
    abstract public String toString();
}

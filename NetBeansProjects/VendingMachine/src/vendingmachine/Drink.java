package vendingmachine;

public abstract class Drink {
    private double price;
    
    public Drink(double price){
        this.price = price;
    }
    
    abstract int getStockNumber();
    abstract void upraiseStockNumber();
    abstract public Drink giveDrink();

    public double getPrice() {return this.price;}
    public void setPrice(double p){this.price = p;}
    
    @Override
    abstract public String toString();
}

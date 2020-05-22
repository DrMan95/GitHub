package vendingmachine;

public class fanta extends Drink{
    private static int stockNumber=10;

    public fanta() {
        super(1.2);
    }

    @Override
    int getStockNumber() {return fanta.stockNumber;}
    
    @Override
    void upraiseStockNumber(){fanta.stockNumber++;}
    
    @Override
    public Drink giveDrink() {
        if(fanta.stockNumber==0) return null;
        fanta.stockNumber--;
        return this;
    }
    
    @Override
    public String toString(){
        return "Fanta, price:"+this.getPrice()+"€ and there are "+this.getStockNumber()+" bottles.";
    }
}
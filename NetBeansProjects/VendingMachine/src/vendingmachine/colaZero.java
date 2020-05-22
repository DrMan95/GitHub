package vendingmachine;

public class colaZero extends Drink{
    private static int stockNumber=10;
    public colaZero() {
        super(1.1);
    }

    @Override
    int getStockNumber() {return colaZero.stockNumber;}
    
    @Override
    public void upraiseStockNumber(){colaZero.stockNumber++;}

    @Override
    public Drink giveDrink() {
        if(colaZero.stockNumber==0) return null;
        colaZero.stockNumber--;
        return this;
    }
    
    @Override
    public String toString(){
        return "Coca-Cola zero, price:"+this.getPrice()+"€ and there are "+this.getStockNumber()+" bottles.";
    }
}

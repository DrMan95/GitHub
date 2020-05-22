package ex04.AB;

public class cola extends Drink{
    private static int stockNumber=10;
    public cola() {
        super(1.2);
    }

    @Override
    public int getStockNumber() {return cola.stockNumber;}

    
    @Override
    void upraiseStockNumber(){cola.stockNumber++;}
    
    @Override
    public Drink giveDrink() {
        if(cola.stockNumber==0) return null;
        cola.stockNumber--;
        return this;
    }
    
    @Override
    public String getName(){return "cocacola";}
    
    
    @Override
    public String toString(){
        return "Coca-Cola, price:"+this.getPrice()+"€ and there are "+this.getStockNumber()+" bottles.";
    }
}

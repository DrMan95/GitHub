package ex04.AB;

public class zelita extends Drink{
    private static int stockNumber=10;

    public zelita() {
        super(1.3);
    }

    @Override
    public int getStockNumber() {return zelita.stockNumber;}
    
    @Override
    void upraiseStockNumber(){zelita.stockNumber++;}

    @Override
    public Drink giveDrink() {
        if(zelita.stockNumber==0) return null;
        zelita.stockNumber--;
        return this;
    }
    
    @Override
    public String getName(){return "zelita";}
    
    @Override
    public String toString(){
        return "Zelita, price:"+this.getPrice()+"€ and there are "+this.getStockNumber()+" bottles.";
    }
}

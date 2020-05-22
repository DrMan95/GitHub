package vendingmachine;

public class sprite extends Drink{
    private static int stockNumber=10;
    public sprite() {
        super(1.2);
    }

    @Override
    int getStockNumber() {return sprite.stockNumber;}
    
    @Override
    void upraiseStockNumber(){sprite.stockNumber++;}

    @Override
    public Drink giveDrink() {
        if(sprite.stockNumber==0) return null;
        sprite.stockNumber--;
        return this;
    }
    
    @Override
    public String toString(){
        return "Sprite, price:"+this.getPrice()+"€ and there are "+this.getStockNumber()+" bottles.";
    }
}

package ex03;

public abstract class BagCarrier {
    private Bag bag;
    
    BagCarrier(){
        this.bag = null;
    }
    
    public Bag getBag(){return this.bag;}
    public boolean hasBag(){return this.bag != null;}
    public void setBag(Bag b){
        b.setHolder((Trainer) this);
        if((b.getHolder()) == this){
            this.bag = b;
        }
    }
    
    public void collectItem(Item i){
        if(this.bag != null){
            this.bag.addItem(i);
        }else{
            System.out.println("There's no bag.");
        }
    }
    
    public Medicine getMedicineItem(){
        if(this.bag != null){
            return this.bag.getMedicineItem();
        }
        return null;
    }
    
    public Berry getBerryItem(){
        if(this.bag != null){
            return this.bag.getBerryItem();
        }
        return null;
    }
    
    @Override
    public abstract String toString();
}

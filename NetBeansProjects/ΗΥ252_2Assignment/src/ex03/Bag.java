package ex03;

import java.util.ArrayList;

public class Bag {
    private ArrayList<Item> items;
    private int maxCapacity;
    private BagCarrier holder;
    
    public Bag(){
        super();
        this.items = new ArrayList<>();
        this.maxCapacity = 10;
        this.holder = null;
    }
    
    public void addItem(Item item){
        if(items.size()<this.maxCapacity)   items.add(item);
        else System.out.println("The Bag is full.");
    }
    
    public Medicine getMedicineItem() {
        int i;
        Item item = null;
        for(i=0; i<items.size(); i++){
            if(items.get(i) instanceof Medicine){
                item = items.remove(i);
                Medicine.setCount(Medicine.getCount()-1);
            }
        }
        return (Medicine)item;
    }
    
    public Berry getBerryItem() {
        int i;
        Item item = null;
        for(i=0; i<items.size(); i++){
            if(items.get(i) instanceof Berry){
                item = items.remove(i);
                Berry.setCount(Berry.getCount()-1);
            }
        }
        return (Berry)item;
    }
    
    public void setHolder(Trainer t){
        if(this.holder == null){
            this.holder = t;
        }else{
            System.out.println("There's already a holder for this bag.");
        }
    }
    
    public BagCarrier getHolder(){return this.holder;}
    
    @Override
    public String toString(){
        int i;
        String s = "";
        s += "Items "+this.items.size()+"/10:";
        for(i=0; i<this.items.size(); i++){
            s += "\n\t"+this.items.get(i).toString();
        }
        return s;
    }
}

package pokemon;

public abstract class Item {
    private final String name;
    
    public Item(String name){
        if(this instanceof Medicine)
            this.name = name + "_" + Medicine.getCount();
        else this.name = name + "_" + Berry.getCount();
    }
    
    public String getName(){return this.name;}
    
    abstract void carePokemon(Pokemon pokemon);
    
    @Override
    public abstract String toString();
}

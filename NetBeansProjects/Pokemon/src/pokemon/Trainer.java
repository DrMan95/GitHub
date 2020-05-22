package pokemon;

import java.util.ArrayList;

public class Trainer extends BagCarrier{
    private final String fname;
    private final String lname;
    private final ArrayList<Pokemon> caughtPokemons;
    /**
     * preconditions fname!=null && lname!= null
     * postconditions sets first name and last name to the trainer ang creates an
     * empty arraylist for the pokemons.
     * @param fname
     * @param lname 
     */
    public Trainer(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
        this.caughtPokemons = new ArrayList<>();
    }
    
    public void catchPokemon(Pokemon pokemon){this.caughtPokemons.add(pokemon);}
    
    public boolean isDefeated(){
        int i;
        for (i=0; i<this.caughtPokemons.size(); i++){
            if(!this.caughtPokemons.get(i).isDefeated())
                return false;
        }return true;
    }
    public Pokemon chooseNextPokemon(){
        int i;
        for (i=0; i<this.caughtPokemons.size(); i++){
            if(!this.caughtPokemons.get(i).isDefeated())
                return this.caughtPokemons.get(i);
        }return null;
    }
    
    public String getCallSign(){return ""+this.fname+" "+this.lname;}
    
    @Override
    public String toString(){
        int i;
        String s = "";
        s += this.getCallSign();
        s += "\n\tTrainer's bag has:\n";
        s += this.getBag().toString();
        s += "\n\tTrainer has caugth:";
        for(i=0; i<this.caughtPokemons.size(); i++){
            s += "\n\t\t"+this.caughtPokemons.get(i).toString();
        }
        s += "\n";
        return s;
    }
}

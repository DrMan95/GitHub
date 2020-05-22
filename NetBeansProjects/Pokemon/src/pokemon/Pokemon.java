package pokemon;

import java.util.ArrayList;

abstract public class Pokemon implements Playable{
    private final String name;
    private int attack;
    private int healthPwr;
    private final ArrayList<PokemonType> strongAgainst;
    private final ArrayList<PokemonType> weakAgainst;
    private Trainer trainer;
    
    public Pokemon (String name, int attack, int healthPwr, ArrayList<PokemonType> strongAgainst, ArrayList<PokemonType> weakAgainst){
        this.name = name;
        this.attack = attack;
        this.healthPwr = healthPwr;
        this.strongAgainst = new ArrayList<>();
        this.weakAgainst = new ArrayList<>();
        this.trainer = null;
    }
    
    public Pokemon (String name, ArrayList<PokemonType> strongAgainst, ArrayList<PokemonType> weakAgainst){
        this(name,5,15,strongAgainst,weakAgainst);
    }

    @Override
    public void attack(Pokemon adversary){
        int plin=0;
        if(this.equals(adversary)){return;}
        if(this.healthPwr<=0){return;}
        if(this.isStrongAgainst(adversary)) {plin = this.attack*2;}
        else if(this.isWeakAgainst(adversary))  {plin = this.attack/2;}
        else    {plin = this.attack;}
        adversary.healthPwr-=plin;
        if(adversary.healthPwr<=0)    adversary.healthPwr=0;
        System.out.println(""+this.name+" attacked "+adversary.name+"\n\t"+adversary.name+"'s health is now :"+adversary.healthPwr);
    }
    @Override
    public boolean isDefeated(){return this.healthPwr==0;}
    @Override
    public int getHealthCondition(){return this.healthPwr;}
    @Override
    public void setHealthCondition(int condition){this.healthPwr=condition;}
    @Override
    public String getCallSign(){return this.name;}
    
    
    public void addStrongAgainst(PokemonType type){this.strongAgainst.add(type);}
    public void addWeakAgainst(PokemonType type){this.weakAgainst.add(type);}
    
    public int getAttack(){return this.attack;}
    
    public void setAttack(int attack){this.attack = attack;}
    
    public boolean isStrongAgainst(Pokemon adversary){
        if(this instanceof FirePokemon){
            return adversary instanceof GrassPokemon;
        }else if(this instanceof WaterPokemon){
            return adversary instanceof FirePokemon || adversary instanceof RockPokemon;
        }else if(this instanceof GrassPokemon){
            return adversary instanceof RockPokemon || adversary instanceof WaterPokemon;
        }else{
            return adversary instanceof FirePokemon;
        }
    }
    public boolean isWeakAgainst(Pokemon adversary){
        if(this instanceof FirePokemon){
            return adversary instanceof RockPokemon || adversary instanceof WaterPokemon;
        }else if(this instanceof WaterPokemon){
            return adversary instanceof GrassPokemon;
        }else if(this instanceof GrassPokemon){
            return adversary instanceof FirePokemon;
        }else{
            return adversary instanceof GrassPokemon || adversary instanceof WaterPokemon;
        }
    }
    
    Trainer getTrainer(){return this.trainer;}
    
    public void setTrainer(Trainer trainer){
        this.trainer = trainer;
    }
    
    @Override
    public String toString(){
        int i;
        String s = "";
        s += this.name;
        s += "\n\tAttack: "+this.attack;
        s += "\n\tHealth: "+this.healthPwr;
        s += "\n\tPokemon is strong against:\n\t\t";
        for(i=0; i<this.strongAgainst.size(); i++){
            s += "<"+this.strongAgainst.get(i).toString()+">";
        }
        s += "\n\tPokemon is weak against:\n\t\t";
        for(i=0; i<this.weakAgainst.size(); i++){
            s += "<"+this.weakAgainst.get(i).toString()+">";
        }
        if(this.getTrainer() == null){
            s += "\n\tIt's a wild pokemon.";
        }else{
            s += "\n\tIt's trainer is: "+this.getTrainer().getCallSign();
        }
        s += "\n";
        return s;
    }
}

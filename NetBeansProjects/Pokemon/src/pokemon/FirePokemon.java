package pokemon;

public class FirePokemon extends Pokemon{ 
    
    public FirePokemon(String name, int attack, int health){
        super(name,attack,health,null,null);
        this.addStrongAgainst(PokemonType.grass);
        this.addWeakAgainst(PokemonType.rock);
        this.addWeakAgainst(PokemonType.water);
    }
    public FirePokemon (String name){
        this(name,5,15);
    }
    
}
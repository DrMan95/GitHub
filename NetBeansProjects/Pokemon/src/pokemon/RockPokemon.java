package pokemon;

public class RockPokemon extends Pokemon{
    public RockPokemon(String name, int attack, int health){
        super(name,attack,health,null,null);
        this.addStrongAgainst(PokemonType.grass);
        this.addWeakAgainst(PokemonType.rock);
        this.addWeakAgainst(PokemonType.water);
    }
    public RockPokemon (String name){
        this(name,5,15);
    }
}

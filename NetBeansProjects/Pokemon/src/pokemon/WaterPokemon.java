package pokemon;

public class WaterPokemon extends Pokemon{
    public WaterPokemon(String name, int attack, int health){
        super(name,attack,health,null,null);
        this.addStrongAgainst(PokemonType.grass);
        this.addWeakAgainst(PokemonType.rock);
        this.addWeakAgainst(PokemonType.water);
    }
    public WaterPokemon (String name){
        this(name,5,15);
    }
}

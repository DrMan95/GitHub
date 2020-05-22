package pokemon;

public class GrassPokemon extends Pokemon{
    public GrassPokemon(String name, int attack, int health){
        super(name,attack,health,null,null);
        this.addStrongAgainst(PokemonType.grass);
        this.addWeakAgainst(PokemonType.rock);
        this.addWeakAgainst(PokemonType.water);
    }
    public GrassPokemon (String name){
        this(name,5,15);
    }
}

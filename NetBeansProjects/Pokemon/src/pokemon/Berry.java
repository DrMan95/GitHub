package pokemon;

import java.util.Random;

public class Berry extends Item{
    private final int attackSkill;
    private static int count=0;
    
    public Berry(){
        super("Berry");
        Berry.count++;
        Random r = new Random();
        this.attackSkill = r.nextInt(3)+2;
    }
    public static void setCount(int c){Berry.count = c;}
    public static int getCount(){return Berry.count;}
    
    @Override
    public void carePokemon(Pokemon pokemon){
        int x = pokemon.getAttack();
        pokemon.setAttack(x + this.attackSkill);
    }
    
    @Override
    public String toString(){
        return this.getName()+" attack skill: "+this.attackSkill;
    } 
}

package pokemon;

import java.util.Random;

public class Medicine extends Item{
    private final int medicineSkill;
    private static int count=0;
    
    public Medicine(){
        super("Medicine");
        Medicine.count++;
        Random r = new Random();
        this.medicineSkill = r.nextInt(6)+4;
    }
    public static void setCount(int c){ Medicine.count = c;}
    public static int getCount(){return Medicine.count;}
    
    @Override
    public void carePokemon (Pokemon pokemon){
        int x = pokemon.getHealthCondition();
        pokemon.setHealthCondition(x+this.medicineSkill);
    }

    @Override
    public String toString(){
        return this.getName()+" medicine skill: "+this.medicineSkill;
    } 
}

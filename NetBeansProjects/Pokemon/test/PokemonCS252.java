
import pokemon.*;

/**
 * @author Giorgos Nikitakis <nikitak at csd.uoc.gr>
 */
public class PokemonCS252 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pokemon pokemon1 = new FirePokemon("Charmander", 8, 20);
        Pokemon pokemon2 = new WaterPokemon("Squirtle");
        Pokemon pokemon3 = new GrassPokemon("Bulbasaur", 3, 10);
        Pokemon pokemon4 = new RockPokemon("Golem");

        Medicine medicine1 = new Medicine();
        Medicine medicine2 = new Medicine();
        Medicine medicine3 = new Medicine();
        Berry berry1 = new Berry();
        Berry berry2 = new Berry();

        Bag bag1 = new Bag();
        Bag bag2 = new Bag();

        Trainer ash = new Trainer("Ash", "Ketchum");
        Trainer gary = new Trainer("Gary", "Oak");

        try {
            ash.setBag(bag1);
            gary.setBag(bag2);

            ash.catchPokemon(pokemon1);
            ash.catchPokemon(pokemon2);
            ash.collectItem(medicine1);
            ash.collectItem(medicine2);
            ash.collectItem(berry1);
            System.out.println(ash);

            gary.catchPokemon(pokemon3);
            gary.catchPokemon(pokemon4);
            gary.collectItem(medicine3);
            gary.collectItem(berry2);
            System.out.println(gary);

            Pokemon ashPokemon = null;
            Pokemon garyPokemon = null;

            while (!ash.isDefeated() && !gary.isDefeated()) {
                //Ash choose pokemon
                if (ashPokemon == null || ashPokemon.isDefeated()) {
                    if (ashPokemon != null) {
                        System.out.println(ash.getCallSign() + " " + ashPokemon.getCallSign() + " lost round.");
                    }
                    ashPokemon = ash.chooseNextPokemon();
                    System.out.println(ash.getCallSign() + ": i  choose you " + ashPokemon.getCallSign() + "\n");
                }

                //Gary choose pokemon
                if (garyPokemon == null || garyPokemon.isDefeated()) {
                    if (garyPokemon != null) {
                        System.out.println(gary.getCallSign() + " " + garyPokemon.getCallSign() + " lost round.");
                    }
                    garyPokemon = gary.chooseNextPokemon();
                    System.out.println(gary.getCallSign() + ": i  choose you " + garyPokemon.getCallSign() + "\n");
                }

                //Ash starts first!
                ashPokemon.attack(garyPokemon);
                if (!garyPokemon.isDefeated()) {
                    garyPokemon.attack(ashPokemon);
                }

                //Feed pokemon
                if (!ashPokemon.isDefeated()) {
                    Berry berry = ash.getBerryItem();
                    if (berry != null) {
                        berry.carePokemon(ashPokemon);
                        System.out.println(ash.getCallSign() + " give a berry to " + ashPokemon + "\n");
                    }
                }

                if (!garyPokemon.isDefeated()) {
                    Medicine medicine = gary.getMedicineItem();
                    if (medicine != null) {
                        medicine.carePokemon(garyPokemon);
                        System.out.println(gary.getCallSign() + " give a medicine to " + garyPokemon + "\n");
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            System.out.println(ash.isDefeated() ? gary.getCallSign() : ash.getCallSign());
            System.out.println("Won!");
        }

    }

}

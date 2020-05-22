package α2εβ;

public class Tester {
    public static void main(String[] lala){
        BasketballTeam b1 = new BasketballTeam("OSFP");
        BasketballTeam b2 = new BasketballTeam("PAO");
        
        b1.addPlayer(0, new Player("papanikolaoy",200));
        b2.addPlayer(0, new Player("zampoylis",180));
        b2.addPlayer(1, new Player("samaritakis",190));
        
        System.out.println(b1);
        System.out.println(b2);
    }
}

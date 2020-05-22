package ex04.AB;

public class Coin {
    public double value;
    
    /**
     * 
     * @param v=0.05 || v=0.1 || v=0.2 || v=0.5 || v=1 || v=2
     */
    public Coin(double v) throws IllegalArgumentException{
        if(v!=0.05 && v!=0.1 && v!=0.2 && v!=0.5 && v!=1 && v!=2){
            throw new IllegalArgumentException();
        }
        this.value = v;
    }
    
    
    public static Coin strToCoin(String str){
        if(str.equals("5c")){return new Coin(0.05);}
        if(str.equals("10c")){return new Coin(0.1);}
        if(str.equals("20c")){return new Coin(0.2);}
        if(str.equals("50c")){return new Coin(0.5);}
        if(str.equals("1e")){return new Coin(1);}
        if(str.equals("2e")){return new Coin(2);}
        return null;
    }
}

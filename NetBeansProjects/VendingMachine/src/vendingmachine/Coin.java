package vendingmachine;

public class Coin {
    double value;
    
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
}

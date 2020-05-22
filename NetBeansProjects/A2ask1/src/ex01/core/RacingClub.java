package ex01.core;
import ex01.Club;

public class RacingClub extends Club{
    public RacingClub(String name,int max){super(name,max);}
    
    @Override
    public int getNumberOfMembers(){return super.getNumberOfMembers();}
    
    public int getNumberOfΑdultMembers(){
        Person[] p = this.getMembers();
        int adults=0;
        for (int i=0 ; i<this.getNumberOfMembers() ; i++){if (p[i] instanceof Adult) adults++;}
        return adults;
    }
    
    @Override
    public String toString(){return super.toString();}
}

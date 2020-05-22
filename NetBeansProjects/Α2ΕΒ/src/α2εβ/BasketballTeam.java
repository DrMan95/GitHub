package α2εβ;

public class BasketballTeam {
    private String name;
    private Player[] players;
    
    public BasketballTeam(String name){
        this.name = name;
        this.players = new Player[5];
    }
    
    void setName(String name){this.name = name;}
    String getName(){return this.name;}
    
    void addPlayer(int thesi,Player p){
        if(thesi<0 || thesi>4) throw new IllegalArgumentException();
        this.players[thesi] = p;
    }
    
    @Override
    public String toString(){
        int i,players=0,sum=0;
        String s="";
        s += "Name: "+this.name+"\n";
        for(i=0; i<4; i++)  if(this.players[i]!=null)   players++;
        s += "\tTeam has "+players+" players\n";
        s += "\t\tPLAYERS:";
        for(i=0; i<4; i++){
            if(this.players[i] != null){
                sum+=this.players[i].getHeight();
                s += "\t\t"+this.players[i].getName()+"\n";
            }
        }
        s += "avarage team's height: "+(sum/players);
        return s;
    }
}

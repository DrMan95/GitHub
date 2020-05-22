package α2εβ;

public class Player {
    private String name;
    private int height;
    
    public Player(String name, int height){
        this.name = name;
        this.height = height;
    }
    
    void setName(String name){this.name = name;}
    String getName(){return this.name;}
    void setHeight(int height){this.height = height;}
    int getHeight(){return this.height;}
    
}

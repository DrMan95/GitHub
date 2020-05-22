package ex03;

public interface Playable {
    
    public void attack(Pokemon adversary);
    public boolean isDefeated();
    public int getHealthCondition();
    public void setHealthCondition(int condition);
    public String getCallSign();
    
}

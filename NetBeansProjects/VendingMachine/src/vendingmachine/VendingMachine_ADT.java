package vendingmachine;

import java.util.ArrayList;

public interface VendingMachine_ADT {
    /**
     * precondition: the drink must be valid
     * postcondition: pass to the next state
     * @param d
     * @return 
     */
    public boolean selectDrink(Drink d);
    /**
     * precondition: the coin must be valid
     * postcondition: pass to the next state
     * @param c
     * @return 
     */
    public boolean insertCoin(Coin c);
    /**
     * postcondition: correct prints to the display
     */
    public void updateDisplay();
    /**
     * postcondition: return the money that was inserted
     * @return the money back
     */
    public ArrayList<Coin> returnMoney();
    /**
     * postcondition: cancel the action and return to the 1st state
     */
    public void cancel();
    /**
     * postcondition: check if the money are enough to buy the drink
     * @return true if they are, false otherwise
     */
    public boolean ok();
    /**
     * postcondition: give the right state
     * @return the state that the VM is
     */
    public State getState();
    /**
     * postcondition: give back the right change 
     * @return the change
     */
    public ArrayList<Coin> returnChange();
    /**
     * postcondition: give the drink he choose
     * @return the drink
     */
    public Drink deliverDrink();
}
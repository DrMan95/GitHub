package ex04.D;

import ex04.AB.Coin;
import ex04.AB.Drink;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * @author yannakis
 * Listener class for A2.4
 * Responsible for the listeners actions
 */
public class MyActionListener implements MouseListener {
    private final MyVendingMachineclass vm;//your class 
    private JLabel jlabDisplay;//the display
    volatile private String state;//IDLE, COINS INSERTED, DRINK SELECTED, BOTH, NONE 
    volatile private boolean isrunning; // a thread is running

    /**
     * The constructor
     * Initialize the state from your class
     * Change the initialization according to your needs
     * @param vm your vending machine class
     * @param jlabDisplay the display jlabel, so we can change it
     */
    public MyActionListener(MyVendingMachineclass vm, JLabel jlabDisplay){
        super();
        this.vm=vm;
        this.jlabDisplay=jlabDisplay;
        state=vm.getState().toString();
        isrunning=false;
    }
   
    /**
     * The Listener Controller
     * You can change this function to suit your needs (maybe keep which 
     * coin/drink was selected, add more states?)
     * @param e the button that was used
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //Check which button is clicked
        JButton b=(JButton)e.getSource();
        switch (checkInputButton(b.getText())) {
            case "Coins":
                state="MoneyEntered";
                newThread(b.getText());
                break;
            case "Drinks":
                state="DrinkSelected";
                newThread(b.getText());
                break;
            case "OK":
                state="ok";
                newThread(b.getText());
                break;
            case "Cancel":
                state="cancel";
                newThread(b.getText());
                break;
            default:
                state="NONE";
                System.err.println("Should never reach this state");
                break;
        }
    }
    
    /**
     * gets the button text and returns the abstract state
     * @param str 
     * @return the state
     */
    private String checkInputButton(String str){
        String caseName=str;
        if(str.equals("cocacola") || str.equals("cocacolazero") || str.equals("fanta")
                || str.equals("zelita") || str.equals("sprite")){
            caseName= "Drinks";
        }else if(str.equals("5c") || str.equals("10c") || str.equals("20c")
                || str.equals("50c") || str.equals("1e") || str.equals("2e")){
            caseName="Coins";
        }
        return caseName;
    }
    
    /**
     * @return false if another thread is running 
     */
    private synchronized boolean checkThread(){
        return isrunning ? false : (isrunning=!isrunning);
    }
    
    /**
     * Here we handle the listener actions with new threads
     * In each action you are required to write your code accordingly to the instructions
     */
    private void newThread(String str) {
        if(checkThread()){
            new Thread(){
                @Override
                public void run() {
                    try{
                        switch (state){
                            case "MoneyEntered":
                                vm.insertCoin(Coin.strToCoin(str));
                                vm.returnString("Coin Insterted");
                                jlabDisplay.setText(fixTab(state+"\t\t"+vm.getMoney()+
                                        " E,\t\t"+vm.getDrink()));
                                break;
                            case "DrinkSelected":
                                vm.selectDrink(Drink.strToDrink(str));
                                vm.returnString("Drink Selected");
                                jlabDisplay.setText(fixTab(state+"\t\t"+vm.getMoney()+
                                        " E,\t\t"+vm.getDrink()));
                                break;
                            case "ok":
                                if(vm.ok()){
                                    vm.returnString("Ok ");
                                    //success example
                                    jlabDisplay.setText("Delivering Drink...  Change......");
                                    //sleep(1000)
                                    //IDLE
                                    jlabDisplay.setText(fixTab(state+"\t\t"+vm.getMoney()+
                                        " E,\t\t"+vm.getDrink()));
                                }
                                break;
                            case "cancel":
                                vm.cancel();
                                vm.returnString("Canceled");
                                // example
                                jlabDisplay.setText("Returning Money.....");
                                jlabDisplay.setText(fixTab(state+"\t\t"+vm.getMoney()+
                                        " E,\t\t"+vm.getDrink()));
                                //sleep(1000)
                                //IDLE
                                break;
                            default:
                                throw new Exception ("ERROR, state has not been initialised ");
                        }
                    }catch(Exception ex){ // change to your exception or modify to prevent out of order actions
                        infoBox("Wrong Order " +ex ,"ERROR");
                    }
                    isrunning=false;
                }
            }.start();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}

    public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
    private String fixTab(String str){
        return str.replaceAll("\t", "      ");
    }
}

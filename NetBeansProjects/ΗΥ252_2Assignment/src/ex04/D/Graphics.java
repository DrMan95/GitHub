package ex04.D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author yannakis
 * Graphics class for HY252 A2.4
 * Responsible for creating the elements of the java Window(jbuttons,jlabel, etc..)
 */
public class Graphics extends JFrame{
    private String imgPrefix= "src/ex04/D/resources/"; //images location
    private final JPanel panelActions; 
    private final JPanel panelScreen; 
    
    /**
     * Initializes the graphic window with elements
     * @param vm your VendingMachine Class
     */
    public Graphics(MyVendingMachineclass vm){
        super("HY252 A2.4");
        //Here we create the basic container of our window
        panelActions=new JPanel(new GridLayout(3,7));
        panelActions.setBackground(Color.WHITE );
        //Here we create the display container of our window
        panelScreen=new JPanel(new GridLayout(1,1));
        panelScreen.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        //init display
        String strDisplay="IDLE,"+"\t\t"+"0E,"+"\t\t"+"DRINK NOT SELECTED";
        JLabel jlabDisplay=new myJLabel(strDisplay.replaceAll("\t", "      "));
        jlabDisplay.setName("display");
        panelScreen.add(jlabDisplay);
        //Our listener to be called when we click on buttons
        MyActionListener ml=new MyActionListener(vm,jlabDisplay);
        //Here we create the elements of the action JPanel (jlabels,jbuttons)
        JLabel jlabDrink=new myJLabel("Select Drink:");
        panelActions.add(jlabDrink);
        //create drink buttons and add listeners
        for (int i = 1; i <= 5; i++) {
            JButton jbutDrink=new myJButton(getImageName(true,i),true);
            jbutDrink.setIcon(getImage(true,i));
            jbutDrink.addMouseListener(ml);
            panelActions.add(jbutDrink);  
        }panelActions.add(new myJLabel());
        JLabel jlabc=new myJLabel("Insert Coin:");
        panelActions.add(jlabc);
        //create coin buttons and add listeners
        for (int i = 1; i <= 6; i++) {
            JButton jbutCoin=new myJButton(getImageName(false,i),true);
            jbutCoin.setIcon(getImage(false,i));
            jbutCoin.addMouseListener(ml);
            panelActions.add(jbutCoin);  
        }
        JLabel jlabact=new myJLabel("Action:");
        panelActions.add(jlabact);
        for (int i = 0; i < 4; i++) {
            panelActions.add(new myJLabel());    //insert empty labels
        }
        JButton gbutok=new myJButton("OK",false);
        gbutok.addMouseListener(ml);
        panelActions.add(gbutok);  
        JButton gbutcanc=new myJButton("Cancel",false);
        gbutcanc.addMouseListener(ml);
        panelActions.add(gbutcanc);  
         
        //Here we create a jpanel container so we can separate display with actions 
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        //add jpanels to the jpanel container
        container.add(panelScreen);
        container.add(Box.createRigidArea(new Dimension(0,5)));
        container.add(panelActions);
        //add jpanel container to jframe 
        add(container);
    }
    
    /**
     * Retrieves the images
     * @param type true:type drink, false: type coin
     * @param num retrieves the item (type drink or coin)
     * @return the image icon of the item
     */
    private ImageIcon getImage(boolean  type, int num){
        ImageIcon img=getImageScaled(imgPrefix+getImageName(type,num)+".jpg");
        return img;
    }
    
    /**
     * rescales image
     * @param image image location
     * @return 
     */
    private ImageIcon getImageScaled(String image){
        Image newimg = new ImageIcon(image).getImage().getScaledInstance(
                90, 90,  java.awt.Image.SCALE_SMOOTH ) ;  
        return new ImageIcon( newimg );
    }
    
    /**
     * returns the image name  
     * @param type true:type drink, false: type coin
     * @param num retrieves the item (type drink or coin)
     * @return 
     */
    public String getImageName(boolean type,int num){
        String name="";
        if(type){
            switch(num){
                case 1:
                    name="cocacola";
                    break;
                case 2:
                    name="cocacolazero";
                    break;
                case 3:
                    name="fanta";
                    break;
                case 4:
                    name="sprite";
                    break;
                case 5:
                    name="zelita";
                    break;
            }
        }else {
            switch(num){
                case 1:
                    name="5c";
                    break;
                case 2:
                    name="10c";
                    break;
                case 3:
                    name="20c";
                    break;
                case 4:
                    name="50c";
                    break;
                case 5:
                    name="1e";
                    break;
                 case 6:
                    name="2e";
                    break;
            }
        }
        return name;
    }
}

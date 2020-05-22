package ex04.D;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author yannakis
 * A custom JLabel so we can change its border
 */
public class myJLabel extends JLabel{
    /**
     * constructor
     * @param name jlabel name 
     */
    public myJLabel(String name){
        super(name);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }
    public myJLabel(){
        super();
    }
}

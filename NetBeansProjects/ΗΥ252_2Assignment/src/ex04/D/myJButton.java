package ex04.D;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author yannakis
 * A custom JButton so we can change its color
 */
public class myJButton extends JButton{
    /**
     * constructor
     * @param name the name of the jButton
     * @param type color selection
     */
    public myJButton(String name,boolean type){
        super(name);
        if(type){
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder());
            setFocusPainted(true);
        }else{
            setBackground(Color.LIGHT_GRAY);
        }
    }
}

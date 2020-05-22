/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pingpong;

import javax.swing.JOptionPane;
import org.jfugue.*;

/**
 *
 * @author csd3357
 */
public class PingPong{
    public static void main(String[] args){
        Ping.main(null);
    }
}


class Ping{
        static void ping(int k){
            Utilities.sound(k);
            JOptionPane.showMessageDialog(null,k,"Ping",
            JOptionPane.INFORMATION_MESSAGE);
            if(k>1) Pong.pong(k-1);
        }
        static void main(String[] args){
            ping(10);
        }
    }

class Pong{
        static void pong(int k){
            Utilities.sound(k);
            JOptionPane.showMessageDialog(null,k,"Pong",
            JOptionPane.INFORMATION_MESSAGE);
            if(k>1) Ping.ping(k-1);
        }
        static void main(String[] args){
            pong(10);
        }
    }

class Utilities{
    static void sound(int k){
        Player p = new Player();
        p.play("["+(k*12)+"] ["+(k*12+2)+"] ["+(k*12+5)+"]");
    }
}
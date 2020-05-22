/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicmaker;

import org.jfugue.*;

/**
 *
 * @author csd3357
 */
public class MusicMaker {

    static int[] concat(int[] a, int[] b){
        int x = a.length + b.length;
        int[] arr = new int[x];
        int i;
        for (i=0 ; i<x ; i++){
            if(i<a.length){
                arr[i] = a[i];
            }else{
                arr[i] = b[i-a.length];
            }
        }
        return arr;
    }
    
    static int[] expandHappy(int[] input){
        int[] arr = new int[input.length*3];
        int i,j=-1;
        for (i=0 ; i<input.length ; i++){
            arr[++j] = input[i];
            arr[++j] = input[i] + 4;
            arr[++j] = input[i] + 7;
        }
        return arr;
    }
    
    static int[] expandSad(int[] input){
        int[] arr = new int[input.length*3];
        int i,j=-1;
        for (i=0 ; i<input.length ; i++){
            arr[++j] = input[i];
            arr[++j] = input[i] + 3;
            arr[++j] = input[i] + 7;
        }
        return arr;
    }
    
    static void playArray(int[] a){
        String S = "";
        int i;
        for (i=0 ; i<a.length ; i++){
            S = S + "[" + a[i] + "] ";
        }
        
        
        System.out.println(S);
        
        Player p = new Player();
        p.play(S);
    }
    
    
    public static void main(String[] args) {
        int seed1[] = {80, 80};
        int seed2[] = {80, 92, 104};
        int seed3[] = {70,70,74,77,77};
      
        playArray(seed1);
        playArray(expandHappy(seed1));
        playArray(expandSad(seed1));
        playArray(expandHappy(seed2));
        playArray(concat(seed1,concat(expandHappy(seed3),expandSad(seed2))));
    }

    
}

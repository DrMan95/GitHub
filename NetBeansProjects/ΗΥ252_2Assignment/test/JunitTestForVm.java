/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ex04.AB.Coin;
import ex04.AB.cola;
import ex04.AB.VendingMachine;
import ex04.AB.State;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author csd3357
 */
public class JunitTestForVm {

    @Test
    public void test_VendingMachine(){
        System.out.println("test_VendingMachine");
        VendingMachine vm = new VendingMachine();
        assertEquals(vm.getState(),State.IDLE);
    }
    
    @Test
    public void test_selectDrink(){
        System.out.println("test_selectDrink");
        VendingMachine vm = new VendingMachine();
        vm.selectDrink(new cola());
        assertEquals(vm.getState(),State.DrinkSelected);
    }
    
    @Test
    public void test_insertCoin(){
        System.out.println("test_insertCoin");
        VendingMachine vm = new VendingMachine();
        vm.insertCoin(new Coin(2));
        assertEquals(vm.getState(),State.MoneyEntered);
    }
    
    @Test
    public void test_both(){
        System.out.println("test_both");
        VendingMachine vm = new VendingMachine();
        vm.selectDrink(new cola());
        vm.insertCoin(new Coin(2));
        assertEquals(vm.getState(),State.DrinkSelectedandMoneyEntered);
    }
    
    @Test
    public void test_cancel(){
        System.out.println("test_cancel");
        VendingMachine vm = new VendingMachine();
        vm.selectDrink(new cola());
        vm.insertCoin(new Coin(2));
        vm.cancel();
        assertEquals(vm.getState(),State.IDLE);
    }
    
    @Test
    public void test_ok(){
        System.out.println("test_ok");
        VendingMachine vm = new VendingMachine();
        vm.selectDrink(new cola());
        vm.insertCoin(new Coin(2));
        vm.ok();
        assertEquals(vm.getState(),State.IDLE);
    }
}

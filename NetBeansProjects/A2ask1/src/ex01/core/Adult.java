package ex01.core;

import ex01.core.Gender;
import ex01.core.Person;

public class Adult extends Person{
    private String drivingLicenceld;
    
    public Adult(String fname, String lname, Gender gender, String pid, String drvLcnc){
        super(fname,lname,gender,pid);
        this.drivingLicenceld = drvLcnc;
    }
    
    public void setDrivingLicenceld(String drvLcnc){this.drivingLicenceld = drvLcnc;}
    public String getDrivingLicenceld(){return this.drivingLicenceld;}
    
    @Override
    public String toString(){
        return ""+super.toString()+" "+this.drivingLicenceld;
    }
}
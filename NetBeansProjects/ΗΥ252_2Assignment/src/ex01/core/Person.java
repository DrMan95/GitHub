package ex01.core;

public class Person{
    private String FName;
    private String LName;
    private Gender Gender;
    private String pid;
    
    public Person(String fname, String lname, Gender gender, String pid){
        this.FName = fname;
        this.LName = lname;
        this.Gender = gender;
        this.pid = pid;
    }
    
    public void setFname(String fname){this.FName = fname;}
    public void setLname(String lname){this.LName = lname;}
    public void setGender(Gender gender){this.Gender = gender;}
    public void setPid(String pid){this.pid = pid;}
    public String getFname(){return this.FName;}
    public String getLname(){return this.LName;}
    public Gender getGender(){return this.Gender;}
    public String getPid(){return this.pid;}
    

    @Override
    public String toString(){
        return ""+this.FName+" "+this.LName+" "+this.Gender+" "+this.pid;
    }
}

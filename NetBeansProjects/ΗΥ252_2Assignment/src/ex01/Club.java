package ex01;

import ex01.core.Gender;
import ex01.core.Adult;
import ex01.core.Person;

public class Club{
    private String Name;
    private Person[] members;
    private int numberOfMembers;
    
    public Club(String name, int max){
        this.Name = name;
        members = new Person[max];
        this.numberOfMembers=0;
    }
    public void setName(String name){this.Name = name;}
    public String getName(){return this.Name;}
    public Person[] getMembers(){return members;}
    public int getNumberOfMembers(){return this.numberOfMembers;}
    
    public boolean addMember(Person p){
        int i;
        for(i=0 ; i<members.length ; i++){
            if(members[i]!= null)   continue;
            if (p instanceof Adult){
                members[i] = new Adult(p.getFname(),p.getLname(),p.getGender(),p.getPid(),((Adult)p).getDrivingLicenceld());
            }else{
                members[i] = new Person(p.getFname(),p.getLname(),p.getGender(),p.getPid());
            }
            this.numberOfMembers++;
            return true;
        }
        return false;
    }
    public boolean deleteMember(Person p){
        int i;
        boolean done=false;
        Person q;
        for (i=0 ; i<this.numberOfMembers ; i++) {
            if(members[i].getPid().equals(p.getPid()) ){
                members[i]=null;
                this.numberOfMembers--;
                done = true;
                break;
            }
        }
        for (++i ; i<=this.numberOfMembers ; i++){
            if(members[i] instanceof Adult){
                q = new Adult(members[i].getFname(),members[i].getLname(),members[i].getGender(),members[i].getPid(),((Adult)members[i]).getDrivingLicenceld());
            }else{
                q = new Person(members[i].getFname(),members[i].getLname(),members[i].getGender(),members[i].getPid());
            }
            members[i-1] = q;
            members[i] = null;
        }
        return done;
    }
    
    public double getPercentOf(Gender g){
        int i,count=0;
        for (i=0 ; i<this.numberOfMembers ; i++) {
            if(this.members[i].getGender().equals(g)) count++;
        }
        return ((double)count/i)*100;
    }
    
    @Override
    public String toString(){
        String s = "";
        s += "Name: "+this.Name+"\n";
        s += "\t"+this.numberOfMembers+" people\n";
        s += "\t\t"+this.getPercentOf(Gender.Male)+"% of Males\n";
        s += "\t\t"+this.getPercentOf(Gender.Female)+"% of Females\n";
        for (int i=0 ; i<this.numberOfMembers; i++) {
            s += "\t\t\t"+this.members[i].toString()+"\n";
        }
        return s;
    }
}
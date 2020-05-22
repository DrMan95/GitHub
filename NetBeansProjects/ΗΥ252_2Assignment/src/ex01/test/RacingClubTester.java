package ex01.test;

import ex01.core.*;
import ex01.Club;
import static ex01.core.Gender.*;
import java.util.Random;


public class RacingClubTester {
    public static void main(String[] args){
        Club cp1 = new RacingClub("cp1",3);
        Club cp2 = new RacingClub("cp2",100);
        Person p1 = new Person("Παιδί","Ανήλικο",Male,"001");
        Person p2 = new Adult("Πατέρας","Ενήλικας",Male,"010","101");
        Person p3 = new Adult("Μητέρα","Ενήλικας",Female,"011","110");
        cp1.addMember(p1);
        cp1.addMember(p2);
        cp1.addMember(p3);
        Gender g;
        Random r = new Random();
        for (int i=1 ; i<=100; i++) {
            if( r.nextInt(10) < 5 ) g = Female;
            else g = Male;
            cp2.addMember(new Person("όνομα"+i,"επώνυμο"+i,g,"τ"+i));
        }
        cp2.deleteMember(new Person("","",Male,"τ20"));
        cp2.deleteMember(new Person("","",Male,"τ40"));
        System.out.println(cp1);
        System.out.println(cp2);
    }
}

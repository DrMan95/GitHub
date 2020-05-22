import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Universe {
    Coordinate center;
    double m;
    Coordinate center_mass;
    ArrayList<Planet> planets;
    int p;

    Universe(Coordinate c) {
        this.center = c;
        this.m = 0;
        this.p = 0;
    }

    public void delete(){
        this.center_mass = null;
        this.planets = null;
    }

    public void _init_planets_(ArrayList<Planet> a) {
        this.planets = a;
        this.p = this.planets.size();
        calculate_center_mass();
    }

    public void fill_Planets(ArrayList<Planet> before, int pos, Coordinate old_center) {
        this.planets = new ArrayList<>();
        for (int i = 0; i < before.size(); i++) {
            if (before.get(i).c.where_is(old_center) == pos) {
                this.planets.add(before.get(i));
                before.get(i).c.R = center.R;
                this.p++;
            }
        }
        calculate_center_mass();
    }

    private void calculate_center_mass() {
        double xm = 0, ym = 0;
        for (int i = 0; i < this.planets.size(); i++) {
            xm += this.planets.get(i).c.x * this.planets.get(i).m;
            ym += this.planets.get(i).c.y * this.planets.get(i).m;
            this.m += this.planets.get(i).m;
        }
        this.center_mass = this.p == 0 ? null : new Coordinate(xm / this.m, ym / this.m, this.center.R);
    }

    public boolean contains(Planet p) {
        return this.planets.contains(p);
    }

    public boolean has_0_or_1_Planet_() {
        return p < 2;
    }

    public void print_planets() {
        FileWriter myWriter;
        try {
            myWriter = new FileWriter("output.csv");
            myWriter.write(this.p + "\n" + this.center.R + "\n");
            for (int i = 0; i < this.planets.size(); i++) {
                myWriter.write(this.planets.get(i).to_string() + "\n");
            }
            myWriter.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
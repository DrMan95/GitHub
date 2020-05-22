public class Planet{
    String name;
    double m;
    Coordinate c;
    Velocity v;
    double Fx , Fy;
    double Ax , Ay;

    Planet(String _name, double _m, Coordinate _c, Velocity _v){
        this.name = _name;
        this.m = _m;
        this.c = _c;
        this.v = _v;
        Fx = 0.0;
        Fy = 0.0;
        Ax = 0.0;
        Ay = 0.0;
    }

    void _init_F(){
        Fx = 0.0;
        Fy = 0.0;
        Ax = 0.0;
        Ay = 0.0;
    }

    void calculate_A(){
        Ax = Fx / m;
        Ay = Fy / m;
    }

    public String to_string(){
        return this.c.x + " " + this.c.y + " " + this.v.x + " " + this.v.y + " " + this.m + " " + this.name;
    }
}
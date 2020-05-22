public class Velocity{
    double x;
    double y;

    Velocity(double _x, double _y){
        this.x = _x;
        this.y = _y;
    }

    String to_string(){
        return "(" + this.x + "," + this.y + ")";
    }
}
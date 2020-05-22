public class Coordinate{
    double x;
    double y;
    double R;

    Coordinate(double _x, double _y, double _R){
        this.x = _x;
        this.y = _y;
        this.R = _R;
    }

    public double distance_from(Coordinate that){
        double r = Math.sqrt( Math.pow(that.x - x , 2.0) + Math.pow(that.y - y , 2.0) );
        return r;
    }

    public int where_is(Coordinate that){
        if(this.x >= that.x && this.y >= that.y){
            return 2;
        }
        if(this.x >= that.x && this.y < that.y){
            return 4;
        }
        if(this.x < that.x && this.y >= that.y){
            return 1;
        }
        if(this.x < that.x && this.y < that.y){
            return 3;
        }
        return 0;
    }

    String to_string(){
        return "(" + this.x + "," + this.y + "|" + this.R + ")";
    }
}
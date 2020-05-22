class Coordinates{
    public:
        double R;
        double x;
        double y;
        Coordinates(){}
        Coordinates(double r, double x , double y): R(r), x(x), y(y){}

        double distance_from(Coordinates that){
            double r = sqrt( power(that.x - x) + power(that.y - y) );
            return r;
        }

        bool is_in(Coordinates box_center){
            return !( box_center.x + box_center.R < x ||
                box_center.x - box_center.R > x ||
                box_center.y + box_center.R < y ||
                box_center.y - box_center.R > y
            );
        }

        string to_string(){
            return std::to_string(x) + " " + std::to_string(y) + " " + std::to_string(R);
        }
};
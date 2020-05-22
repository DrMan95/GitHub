class Velocity{
    public:
        double x;
        double y;

        Velocity(double x , double y): x(x), y(y){}

        string to_string(){
            return std::to_string(x) + " " + std::to_string(y);
        }
};
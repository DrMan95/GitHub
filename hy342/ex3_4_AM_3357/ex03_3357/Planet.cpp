class Planet {
    public:
        static size_t counter;
        size_t id;
        string name;
        double m;
        Coordinates c;
        Velocity v;
        double Fx , Fy;
        double Ax , Ay;

        Planet (string name, double m, Coordinates c, Velocity v): name(name), m(m), c(c), v(v) {
            counter++;
            id = counter;
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

        string to_string(){
            string toRet = "";
            toRet += name;
            toRet += '|';
            toRet += std::to_string(m);
            toRet += '|';
            toRet += c.to_string();
            toRet += '|';
            toRet += v.to_string();
            toRet += '|';
            toRet += std::to_string(Fx) + "," + std::to_string(Fy);
            toRet += '|';
            toRet += std::to_string(Ax) + "," + std::to_string(Ay);
            return toRet;
        }
};
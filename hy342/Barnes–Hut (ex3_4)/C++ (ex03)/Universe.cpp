class Universe{
    public:
        Coordinates center;
        vector<Planet*> planets;
        int p;
        Coordinates center_mass;
        double m;

        Universe() {}

        void _init_(Coordinates c){
            m = 0;
            center = c;
            p = 0;
        }
        
        void _init_planets_(vector<Planet*> all){
            planets.clear();
            for(int i=0; i<all.size(); i++){
                planets.push_back(all[i]);
                m += planets[i]->m;
                p++;
            }
            center_mass = find_center_mass();
        }

        void fill_universe(vector<Planet*> all , int w , Coordinates old_center){
            typedef spin_mutex mutex;
            mutex mtx;
            tbb::parallel_for( tbb::blocked_range<int>(0,all.size()),[&](tbb::blocked_range<int> r){
                for(int i=r.begin(); i<r.end(); i++){
                    if( where_is(all[i]->c , old_center) == w ){
                        all[i]->c.R = center.R;
                        mutex::scoped_lock lock;
                        lock.acquire(mtx);
                        planets.push_back(all[i]);
                        m += all[i]->m;
                        p++;
                        lock.release();
                    }
                }
            });
            center_mass = find_center_mass();
        }

        bool contains(Planet* e){
            return std::count(planets.begin() , planets.end() , e) != 0;
        }

        Coordinates find_center_mass(){
            double x , y;
            double xm=0 , ym=0;
            double m=0;

            for(int i=0; i<p; i++){
                xm += (planets[i]->c.x * planets[i]->m);
                ym += (planets[i]->c.y * planets[i]->m);
                m  += planets[i]->m;
            }
            x = xm/m;
            y = ym/m;

            return Coordinates(center.R , x ,y);
        }

        bool _0_or_1_(){
            return p < 2;
        }
};
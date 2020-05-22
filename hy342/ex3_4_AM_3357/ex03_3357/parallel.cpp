#include "Universe.h"

#include <tbb/tbb.h>
#include <tbb/parallel_for.h>
#include <tbb/parallel_reduce.h>
#include <tbb/blocked_range.h>
using namespace tbb;


                                                                                                    //           -+             |           ++
barnes_node* newNode(Universe u){                                                                   //                          |
    barnes_node* n = new barnes_node();                                                             //                          |
    n->universe = u;                                                                                //                  1       |       2
    n->c1 = NULL;                                                                                   //                          |
    n->c2 = NULL;                                                                                   //                          |
    n->c3 = NULL;                                                                                   //          ----------------0----------------
    n->c4 = NULL;                                                                                   //                          |
    return n;                                                                                       //                  3       |       4
}                                                                                                   //                          |
                                                                                                    //                          |
                                                                                                    //           --             |            +-
string tab_for_print_barnes = "";
void print_Barnes_hut(barnes_node* head){
    if(head == NULL)    return;
    cout << tab_for_print_barnes;
    cout << head->to_string() + "\n";
    tab_for_print_barnes += " ";
    print_Barnes_hut(head->c1);
    print_Barnes_hut(head->c2);
    print_Barnes_hut(head->c3);
    print_Barnes_hut(head->c4);
    tab_for_print_barnes = tab_for_print_barnes.substr(1);
    return;
}

int where_is(Coordinates that, Coordinates from_that){                                              
    if(that.x >= from_that.x && that.y >= from_that.y){
        return 2;
    }
    if(that.x >= from_that.x && that.y < from_that.y){
        return 4;
    }
    if(that.x < from_that.x && that.y >= from_that.y){
        return 1;
    }
    if(that.x < from_that.x && that.y < from_that.y){
        return 3;
    }
    return 0;
}

Universe make_universe(double R , double x0, double y0, Coordinates old_center, vector<Planet*> all){
    Coordinates new_center(R, x0, y0);
    Universe new_universe;
    new_universe._init_(new_center);
    new_universe.fill_universe(all , where_is(new_center , old_center) , old_center);
    return new_universe;
}

barnes_node* Barnes_Hut(barnes_node* n){
    if(n->universe._0_or_1_()) return n;
    n->c1 = Barnes_Hut(
                newNode(
                    make_universe(  n->universe.center.R/2,
                                    n->universe.center.x-(n->universe.center.R/2),
                                    n->universe.center.y+(n->universe.center.R/2),
                                    n->universe.center,
                                    n->universe.planets
                            )
                        )
                    );
    n->c2 = Barnes_Hut(
                newNode(
                    make_universe(  n->universe.center.R/2,
                                    n->universe.center.x+(n->universe.center.R/2),
                                    n->universe.center.y+(n->universe.center.R/2),
                                    n->universe.center,
                                    n->universe.planets
                            )
                        )
                    );
    n->c3 = Barnes_Hut(
                newNode(
                    make_universe(  n->universe.center.R/2,
                                    n->universe.center.x-(n->universe.center.R/2),
                                    n->universe.center.y-(n->universe.center.R/2),
                                    n->universe.center,
                                    n->universe.planets
                            )
                        )
                    );
    n->c4 = Barnes_Hut(
                newNode(
                    make_universe(  n->universe.center.R/2,
                                    n->universe.center.x+(n->universe.center.R/2),
                                    n->universe.center.y-(n->universe.center.R/2),
                                    n->universe.center,
                                    n->universe.planets
                            )
                        )
                    );

    return n;
}

double F(double m1, double m2, double r){
    if(r == 0) return 0;
    return ( G * (m1*m2) )/(r*r);
}

double Fi(double F, double i1, double i2, double r){
    if(r == 0) return 0;
    double fi = F * ((i2-i1)/r);
    if(isnan(fi)) return 0;
    return fi;
}

void calculate_F(Planet* e , barnes_node* node){
    if(node == nullptr) return;
    if(node->universe.p == 0) return;
    if(node->universe.p == 1 && e == node->universe.planets[0]){
        return;
    }
    double r;
    if(node->universe.p == 1){
        r = e->c.distance_from(node->universe.planets[0]->c);
        double f = F(e->m , node->universe.planets[0]->m , r);
        e->Fx += Fi( f , e->c.x , node->universe.planets[0]->c.x , r );
        e->Fy += Fi( f , e->c.y , node->universe.planets[0]->c.y , r );
        return;
    }
    r = e->c.distance_from(node->universe.center_mass);
    if(r == 0) return;
    if(r > 2*e->c.R && !node->universe.contains(e)){
        double f = F(e->m , node->universe.m , r);
        e->Fx += Fi( f , e->c.x , node->universe.center_mass.x , r );
        e->Fy += Fi( f , e->c.y , node->universe.center_mass.y , r );
    }else{
        calculate_F(e,node->c1);
        calculate_F(e,node->c2);
        calculate_F(e,node->c3);
        calculate_F(e,node->c4);
    }
    return;
}

void calculate_all_F(barnes_node* root){
    tbb::parallel_for( tbb::blocked_range<int>(0,root->universe.p),[&](tbb::blocked_range<int> r){
        for(int i=r.begin(); i<r.end(); i++){
            root->universe.planets[i]->_init_F();
            calculate_F(root->universe.planets[i] , root);
            root->universe.planets[i]->calculate_A();
        }
    });
}

void next_sec(Universe u){
    tbb::parallel_for( tbb::blocked_range<int>(0,u.p),[&](tbb::blocked_range<int> r){
        for(int i=r.begin(); i<r.end(); i++){
            u.planets[i]->v.x = u.planets[i]->v.x + (DT * u.planets[i]->Ax);
            u.planets[i]->v.y = u.planets[i]->v.y + (DT * u.planets[i]->Ay);
            
            u.planets[i]->c.x = u.planets[i]->c.x + (DT * u.planets[i]->v.x);
            u.planets[i]->c.y = u.planets[i]->c.y + (DT * u.planets[i]->v.y);
        }
    });
}

int main(int argc, char *argv[]){
    ifstream inputFile(argv[1]);
    string line;
    int N;
    double R;
    vector<Planet*> planets;
    vector<string> temp;
    getline (inputFile, line);
    N = stoi(line);
    getline (inputFile, line);
    R = stof(line);
    for(int i=0; i<N; i++){
        getline (inputFile, line);
        temp = explode(line, ' ');
        planets.push_back( new Planet( temp[5],
                                    stof(temp[4]),
                                    Coordinates( R, stof(temp[0]), stof(temp[1]) ),
                                    Velocity( stof(temp[2]), stof(temp[3]) ) 
                                ) 
                        );
    }
    inputFile.close();
    Universe U;
    barnes_node* root;
    int gens = atoi(argv[2]);
    task_scheduler_init scheduler(atoi(argv[3]));
    Coordinates center0 = Coordinates( R , 0 , 0);
    for(int i=0 ; i<gens ; i++){
        U._init_(center0);
        U._init_planets_(planets);
        root = Barnes_Hut( newNode(U) );
        calculate_all_F(root);
        next_sec(U);
        freeBH(root);
    }
    //print_Barnes_hut(root);

    remove("output.csv");
    ofstream myfile;
    myfile.open ("output.csv");
    myfile << N << endl << R << endl;
    for(int i=0; i<N ; i++){
        myfile << planets[i]->c.x << " " << planets[i]->c.y << " " << planets[i]->v.x << " " << planets[i]->v.y << " " << planets[i]->m << " " << planets[i]->name << endl;
    }
    myfile.close();
    return 0;
}
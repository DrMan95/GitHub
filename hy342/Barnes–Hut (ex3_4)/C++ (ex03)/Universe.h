#include <cmath>
#include <fstream>
#include <vector>
#include <string>
#include <iostream>
#include <algorithm>

using namespace std;

#define G 6.67e-11
#define DT 1

#include <tbb/tbb.h>
#include <tbb/parallel_for.h>
#include <tbb/parallel_reduce.h>
#include <tbb/blocked_range.h>
using namespace tbb;

long double power (long double s);
#include "Coordinates.cpp"
int where_is(Coordinates that, Coordinates from_that);
#include "Velocity.cpp"
#include "Planet.cpp"
size_t Planet::counter = 0;
bool operator==(Planet a , Planet b){
    return a.id == b.id;
}
#include "Universe.cpp"

typedef struct barnes_node  { 
    Universe universe;
    struct barnes_node *c1;
    struct barnes_node *c2;
    struct barnes_node *c3;
    struct barnes_node *c4;

    string to_string(){
        return "[ "+universe.center.to_string() + " , "+ std::to_string(universe.p) +" ]";
    }
} barnes_node; 

void freeBH(barnes_node* b){
    if(b == NULL)   return;
    if(b->c1 != NULL){
        freeBH(b->c1);
        freeBH(b->c2);
        freeBH(b->c3);
        freeBH(b->c4);
    }
    free(b);
}

const vector<string> explode(const string& s, const char& c){
	string buff{""};
	vector<string> v;
	
	for(auto n:s)
	{
		if(n != c) buff+=n; else
		if(n == c && buff != "") { v.push_back(buff); buff = ""; }
	}
	if(buff != "") v.push_back(buff);
	
	return v;
}

long double power (long double a){
    return a*a;
}


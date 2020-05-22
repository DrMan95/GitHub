#define SIZE maxSize
#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <pthread.h>

long maxSize = 0;

pthread_t thread1;
pthread_t thread2;
pthread_t thread3;

typedef struct point{
    int id;
    struct point* next;
} point;

point* createP(long id){
    point* n = (point*)malloc(sizeof(point));
    n->id = id;
    n->next = NULL;
    return n;
}

point* connectP(point* n , point* l){
    if(l == NULL) return n;
    if(l->id < n->id){
        l->next = connectP(n, l->next);
        return l;
    }else{
        n->next = l;
        return n;
    }
}

typedef struct node{
    long nodeID;
    point * points;
    struct node* next;
    long outdegree;
    long indegree;
    long degree;
} node;

node* createN(long id){
    node* n = (node*)malloc(sizeof(node));
    n->nodeID = id;
    n->points = NULL;
    n->next = NULL;
    n->outdegree = 0;
    n->indegree = 0;
    n->degree = 0;
    if( id > maxSize)   maxSize = id;
    return n;
}

int contains(long id , node* listHead){
    node* p = listHead;
    while(p != NULL){
        if(p->nodeID == id) return 1;
        p = p->next;
    }
    return 0;
}

node* insertN(node* n , node* l){
    if(l == NULL) return n;
    if(l->nodeID < n->nodeID){
        l->next = insertN(n, l->next);
        return l;
    }else{
        n->next = l;
        return n;
    }
}

node* findN(long id , node* listHead){
    node* p = listHead;
    while(p != NULL){
        if(p->nodeID == id) return p;
        p = p->next;
    }
}

// node* put(node* n , node* listHead){
//     if(listHead == NULL){
//         listHead = n;
//         return n;
//     }
//     node* p = listHead;
//     node* prev = NULL;
//     while(p != NULL){
//         if(p->nodeID < n->nodeID){
//             prev = p;
//             p = p->next;
//         }else{
//             if(prev == NULL){           //first node!!
//                 n->next = listHead;
//                 listHead = n;
//             }else{                      //mid node!!
//                 prev->next = n;
//                 n->next = p;
//             }
//             return listHead;
//         }
//     }
//     prev->next = n;
//     return listHead;
// }

node* updateN(node* src, node* dst){
    point * p = createP(dst->nodeID);
    src->points = connectP(p, src->points);
    src->outdegree++;
    return src;
}

node* createUniq_updateList(long src, long dst, node* listHead){
    node* s;
    if(!contains(src , listHead)){
        s = createN(src);
        listHead = insertN(s , listHead);
    }else{
        s = findN(src, listHead);
    }
    node* d;
    if(!contains(dst , listHead)){
        d = createN(dst);
        listHead = insertN(d , listHead);
    }else{
        d = findN(dst, listHead);
    }
    s = updateN(s, d);
    return listHead;
}

void* writeOutDegreeToCSV(void* listHead){
    int i;
    remove("outdegree.csv");
    FILE* fp = fopen("outdegree.csv", "a");
    node* p = (node*)listHead;
    long out[SIZE];
    for(i=0 ; i<SIZE ; i++){
        out[i] = 0;
    }
    while(p != NULL){
        out[p->outdegree]++;
        p = p->next;
    }
    for(i=0 ; i<SIZE ; i++){
        if(out[i] == 0) continue;
        fprintf(fp , "%ld , %ld\n",i , out[i]);
    }
    fclose(fp);
	return;
}


void* writeInDegreeToCSV(void* listHead){
    node* p = (node*)listHead;
    int i;
    remove("indegree.csv");
    FILE* fp = fopen("indegree.csv", "a");
    long in[SIZE];
    for(i=0 ; i<SIZE ; i++){
        in[i] = 0;
    }
    while(p != NULL){
        in[p->indegree]++;
        p = p->next;
    }
    for(i=0 ; i<SIZE ; i++){
        if(in[i] == 0) continue;
        fprintf(fp , "%ld , %ld\n",i , in[i]);
    }
    fclose(fp);
	return;
}

long countInDegreeFromNode(int i , point* pl){
    point* p = pl;
    long c = 0;
    if(p == NULL) return 0;
    while(p != NULL){
        if(p->id == i)  c++;
        p = p->next;
    }
    return c;
}

long countInDegreeFromList(int i , node* listHead){
    node* p = listHead;
    long in = 0;
    while(p != NULL){
        in += countInDegreeFromNode(i , p->points);
        p = p->next;
    }
    return in;
}

node* countInDegrees(node* listHead){
    node* p = listHead;
    while(p != NULL){
        p->indegree = countInDegreeFromList(p->nodeID , listHead);
        p = p->next;
    }
    return listHead;
}

void* writeDegreeToCSV(void* listHead){
    node* p = (node*)listHead;
    int i;
    remove("degree.csv");
    FILE* fp = fopen("degree.csv", "a");
    long d[SIZE];
    for(i=0 ; i<SIZE ; i++){
        d[i] = 0;
    }
    while(p != NULL){
        d[p->degree]++;
        p = p->next;
    }
    for(i=0 ; i<SIZE ; i++){
        if(d[i] == 0) continue;
        fprintf(fp , "%ld , %ld\n",i , d[i]);
    }

    fclose(fp);
	return;
}

node* countDegrees(node* listHead){
    node* p = listHead;
    while(p != NULL){
        p->degree = p->indegree + p->outdegree;
        p = p->next;
    }
    return listHead;
}

int main(int argc, char *argv[])
{
    clock_t begin = clock();
    FILE * fp;
    char * line = NULL;
    size_t len = 0;
    size_t lineLength;

    long src, dst;
    fp = fopen(argv[1], "r");
    if (fp == NULL)
        exit(EXIT_FAILURE);
    node* listHead = NULL;
    node* n;
    while ((lineLength = getline(&line, &len, fp)) != -1) {
        if(line[0] == '#'){
            continue;
        }
        sscanf(line, "%ld%ld",&src,&dst);
        listHead = createUniq_updateList(src, dst, listHead);
    }
    fclose( fp );
//    writeOutDegreeToCSV(listHead);
    pthread_create(&thread1, NULL, writeOutDegreeToCSV, listHead);

    printf("OUT  COMPLETED!\n");

    listHead = countInDegrees(listHead);
//    writeInDegreeToCSV(listHead);
    pthread_create(&thread2, NULL, writeInDegreeToCSV, listHead);
    printf("IN  COMPLETED!\n");

    listHead = countDegrees(listHead);
//    writeDegreeToCSV(listHead);
    pthread_create(&thread3, NULL, writeDegreeToCSV, listHead);

    if (line)
        free(line);
    printf("SUCCEDED!\n");
    clock_t end = clock();
    float time_spent = (float)(end - begin) / CLOCKS_PER_SEC;
    printf("Total time: %f\n" , time_spent);
    exit(EXIT_SUCCESS);
}

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <omp.h>
#define OLD 0
#define NEW 1
#define NUM_OF_THREADS threads
int rows;
int cols;
char*** table;

int threads;

void commitTable(){
    int i , j;
    #pragma omp parallel for num_threads(NUM_OF_THREADS) collapse(2)
    for(i=0 ; i<rows ; i++){
        for(j=0 ; j<cols ; j++){
            if(table[i][j][NEW] == '1'){
                table[i][j][OLD] = '*';
            }else if(table[i][j][NEW] == '0'){
                table[i][j][OLD] = ' ';
            }else{
                printf("ERROR: at commitTable!!!\n");
            }
            table[i][j][NEW] = ' ';
        }
    }
}

void displayTable(char* file){
    int i,j;
    remove(file);
    FILE* fp = fopen(file, "a");
    fprintf(fp,"%d %d\n",rows,cols);
    for(i=0 ; i<rows ; i++){
        fprintf(fp,"|");
        for(j=0 ; j<cols ; j++){
            fprintf(fp , "%c|",table[i][j][OLD]);
        }
        fprintf(fp,"\n");
    }
}

int countStars(char* str){
    int i , c=0;
    for(i=0 ; i<strlen(str) ; i++){
        if(str[i] == '*'){
            c++;
        }
    }
    return c;
}

void nextGenFor(int i , int j){
    int neighbors = 0;
    int left = 1;
    int right = 1;
    int up = 1;
    int down = 1;

    if(i == 0 && j == 0){
        if('*' ==table[i][j+right][OLD] ){
            neighbors++;
        }
        if('*' ==table[i+down][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j][OLD] ){
            neighbors++;
        }

    }else if(i == rows-1 && j == 0){
        if('*' ==table[i-up][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j+right][OLD]){
            neighbors++;
        }
    }else if(i == 0 && j == cols-1){
        if('*' ==table[i][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j][OLD]){
            neighbors++;
        }
    }else if(i == rows-1 && j == cols-1){
        if('*' ==table[i-up][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j-left][OLD]){
            neighbors++;
        }
    }else if(i == 0){
        if('*' ==table[i][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j+right][OLD]){
            neighbors++;
        }
    }else if(i == rows-1){
        if('*' ==table[i-up][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j+right][OLD]){
            neighbors++;
        }
    }else if(j == 0){
        if('*' ==table[i-up][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j+right][OLD]){
            neighbors++;
        }
    }else if(j == cols-1){
        if('*' ==table[i-up][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j][OLD]){
            neighbors++;
        }
    }else{
        if('*' ==table[i-up][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i-up][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i][j+right][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j-left][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j][OLD]){
            neighbors++;
        }
        if('*' ==table[i+down][j+right][OLD]){
            neighbors++;
        }
    }

    if(table[i][j][OLD] == '*'){
        if(neighbors == 2 || neighbors == 3){
            table[i][j][NEW] = '1';
        }else{
            table[i][j][NEW] = '0';
        }

    }else if(table[i][j][OLD] == ' '){
        if(neighbors == 3){
            table[i][j][NEW] = '1';
        }else{
            table[i][j][NEW] = '0';
        }

    }else{
        printf("ERROR: at nextGen!!!!\n");
    }
}

int main(int argc, char *argv[]){
    FILE * fp;
    size_t len = 0;
    size_t lineLength;
    char* line;
    char c;
    int i, j, g, m=0;
    threads = atoi(argv[4]);
    fp = fopen(argv[1], "r");
    int gen = atoi(argv[2]);
    if (fp == NULL)
        exit(EXIT_FAILURE);
    lineLength = getline(&line, &len, fp);
    sscanf(line, "%d%d",&rows,&cols);
    table = (char***)malloc(rows * sizeof(char**));
    for(i=0 ; i<rows ; i++){
        table[i] = (char**)malloc(cols * sizeof(char*));
        for(j=0 ; j<cols ; j++){
            table[i][j] = (char*)malloc(2 * sizeof(char));
        }
    }
    i = j = 0;
    while (fscanf(fp,"%c",&c) != EOF) {
        if(c == '|'){
            continue;
        }else if(c == '*'){
            table[i][j][OLD] = ' ';
            table[i][j++][NEW] = '1';
        }else if(c == ' '){
            table[i][j][OLD] = ' ';
            table[i][j++][NEW] = '0';
        }else if(c == '\n'){
            i++;
            j=0;
        }else{
            printf("ERROR\n");
            exit(EXIT_FAILURE);
        }
    }
    fclose( fp );

    for(g=0 ; g<gen ; g++){
        commitTable();
        #pragma omp parallel for num_threads(NUM_OF_THREADS) collapse(2)
        for( i=0 ; i<rows ; i++){
            for( j=0 ; j<cols ; j++){
                nextGenFor(i , j);
            }
        }
    }
    #pragma omp barrier
    #pragma omp single
    {
        displayTable(argv[3]);
    }
    #pragma omp barrier
    return 1;
}

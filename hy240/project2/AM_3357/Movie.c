
/*****************************************************
 * @file   MyMovie.c                                 *
 * @author Alexandra Barka <barka@csd.uoc.gr>        *
 *                                                   *
 * @brief Implementation for Movie.h 				 *
 * Project: Spring 2016						         *
 *****************************************************/
#include "Movie.h"
#include <stdlib.h>

movieCategory_t *categoryArray[5];  /* The categories array (pinakas kathgoriwn)*/
movie_t *new_releases;    		   /* New releases simply-linked binary tree*/
user_t **user_hashtable_p;		   /* The users hashtable. This is an array of chains (pinakas katakermatismoy xrhstwn)*/
int hashtable_size = 100;				   /* The size of the users hashtable, parsed from the command line (>0)*/
unsigned int max_users;					   /* The maximum number of registrations (users)*/
unsigned int max_id;   			   /* The maximum account ID */
unsigned int primes_g[160];
int a,b;


int H(unsigned int uid){
    a = max_users%952 + 1;
    b = max_id%953;
    return ((a*uid + b) % 953) % hashtable_size;
}

/**
 * @brief Creates a new user.
 * Creates a new user with userID as its identification.
 *
 * @param userID The new user's identification
 *
 * @return 0 on success
 *         1 on failure
 */

 int register_user(unsigned int userID){
    user_t *p, *head;
    int key;
    p = malloc(sizeof(user_t));
    p->history = NULL;
    p->userID = userID;
    key = H(userID);
    if (user_hashtable_p[key] == NULL){
        user_hashtable_p[key] = p;
        printf("R %u\nChain %d of Users:\n",userID,key);
        printf("\t%u\nDONE\n",user_hashtable_p[key]->userID);
        return 1;
    }
    head = user_hashtable_p[key];
    printf("R %u\nChain %d of Users:\n",userID,key);
    while (head != NULL){
        if (head->userID > userID){
            p->next = head;
            head = p;
            printf("\t%u\n",head->userID);
            break;
        }
        if (head->next == NULL){
            head->next = p;
            printf("\t%u\n",head->userID);
            break;
        }
        printf("\t%u\n",head->userID);
        head = head->next;
    }
    head = head->next;
    while(head != NULL){
        printf("\t%u\n",head->userID);
        head = head->next;
    }
    printf("DONE\n");
    return 1;
 }

/**
 * @brief Deletes a user.
 * Deletes a user with userID from the system, along with users' history tree.
 *
 * @param userID The new user's identification
 *
 * @return 0 on success
 *         1 on failure
 */

 int unregister_user(unsigned int userID){
     user_t *p,*prev;
     int key;
     key = H(userID);
     p = user_hashtable_p[key];
     prev = user_hashtable_p[key];
     while(p != NULL){
        if(p->userID == userID) break;
        prev = p;
        p = p->next;
     }
     if (p == NULL) return 0;
     p->history = NULL;
     prev = p->next;
     free(p);
	 return 1;
 }

movie_t* insert_movie(movie_t *tree , movie_t* p , movie_t *flag){
    movie_t *head;
    head = tree;
    if (tree == NULL)	return p;
    if (tree->movieID < p->movieID){
        if (tree->rightChild == flag){
            tree->rightChild = p;
            return head;
        }
        tree = tree->rightChild;
    }else{
        if (tree->leftChild == flag){
            tree->leftChild = p;
            return head;
        }
        tree = tree->leftChild;
    }
    insert_movie(tree,p,flag);
    return head;
}

 void print_new(movie_t* p){
    if (p == NULL) return;
    if (p->movieID == -1) return;
    print_new(p->leftChild);
    printf(" <%d>", p->movieID);
    print_new(p->rightChild);
    return;
 }

 /**
 * @brief Add new movie to new release binary tree.
 * Create a node movie and insert it in 'new release' binary tree.
 *
 * @param movieID The new movie identifier
 * @param category The category of the movie
 * @param year The year movie released
 *
 * @return 0 on success
 *         1 on failure
 */

 int add_new_movie(unsigned int movieID, int category, int year){
     movie_t *p;
     p = malloc(sizeof(movie_t));
     p->category = category;
     p->movieID = movieID;
     p->year = year;
     p->sumScore = 0;
     p->watchedCounter = 0;
     p->medianScore = 0;
     p->leftChild = NULL;
     p->rightChild = NULL;
     new_releases = insert_movie(new_releases,p,NULL);
     printf("New Releases:");
     print_new(new_releases);
     printf("\nDONE\n");
     return 1;
 }


 void C(movie_t *tree){
    movie_t *p;
    if (tree == NULL) return;
    C(tree->leftChild);
    tree->leftChild = NULL;
    C(tree->rightChild);
    tree->rightChild = NULL;
    p = malloc(sizeof(movie_t));
    p->category = tree->category;
    p->medianScore = tree->medianScore;
    p->movieID = tree->movieID;
    p->sumScore = tree->sumScore;
    p->watchedCounter = tree->watchedCounter;
    p->year = tree->year;
    p->leftChild = categoryArray[p->category]->sentinel;
    p->rightChild = categoryArray[p->category]->sentinel;
    categoryArray[p->category]->movie = insert_movie(categoryArray[p->category]->movie , p , categoryArray[p->category]->sentinel);
    categoryArray[p->category]->movieCounter++;
    free(tree);
    return;
 }

 /**
 * @brief Categorize the movies from new release binary tree to the array of categories.
 *
 * @return 0 on success
 *         1 on failure
 */

 int categorize_movies(void){
     static int ii = 0;
     int i;
     if (ii == 0){
        for (i=0;i<5;i++){
            categoryArray[i] = malloc(sizeof(movieCategory_t));
	    categoryArray[i]->movieCounter  = 0;
            categoryArray[i]->movie = NULL;
            categoryArray[i]->sentinel = malloc(sizeof(movie_t));
            categoryArray[i]->sentinel->movieID = -1;
        }
	ii++;
     }
     C(new_releases);
     new_releases = NULL;
     print_movies();
     return 1;
 }

 /**
 * @brief User rates the movie with identification movieID with score
 *
 * @param userID The identifier of the user
 * @param movieID The identifier of the movie
 * @param score The score that user rates the movie with id movieID
 *
 * @return 0 on success
 *         1 on failure
 */

userMovie_t* insert_fp(userMovie_t* tree , userMovie_t* u){
    userMovie_t *p , *head, *prev;
    head = tree;
    if (tree == NULL)   return u;
    while(tree != NULL){
	prev = tree;
        if (u->movieID < tree->movieID) tree = tree->leftChild;
        else tree = tree->rightChild;
    }
    p = malloc(sizeof(userMovie_t));
    p->category = prev->category;
    p->movieID = prev->movieID;
    p->score = prev->score;
    u->parent = prev;
    p->parent = prev;
    prev->category = -1;
    prev->score = -1;
    if (u->movieID < prev->movieID){
        prev->leftChild = u;
        prev->rightChild = p;
        prev->movieID = u->movieID;
    }else{
        prev->leftChild = p;
        prev->rightChild = u;
    }
    return head;
}

void Di3eMoyToFiloSoy(userMovie_t* tree){
    if (tree == NULL)   return;
    Di3eMoyToFiloSoy(tree->leftChild);
    if (tree->leftChild == NULL)    printf("\t<%d, %d>\n",tree->movieID,tree->score);
    Di3eMoyToFiloSoy(tree->rightChild);
    return;
}
 /**
 * @brief User rates the movie with identification movieID with score
 *
 * @param userID The identifier of the user
 * @param movieID The identifier of the movie
 * @param score The score that user rates the movie with id movieID
 *
 * @return 0 on success
 *         1 on failure
 */

 int rate_movie(unsigned int userID, unsigned int movieID, int score){
     userMovie_t *u;
     user_t *q;
     movie_t *p;
     int i,f=0;
     for (i=0;i<5;i++){
        p = categoryArray[i]->movie;
        while (p != categoryArray[i]->sentinel){
            if (p->movieID == movieID) {
                f = 1;
                break;
            }else if (p->movieID < movieID){
                p = p->rightChild;
            }else{
                p = p->leftChild;
            }
        }
        if (f == 1) break;
     }
     if (f == 0) return 0;
     p->watchedCounter++;
     p->sumScore += score;
     p->medianScore = (float)p->sumScore / p->watchedCounter;
     u = malloc(sizeof(userMovie_t));
     u->category = p->category;
     u->movieID = p->movieID;
     u->score = score;
     u->leftChild = NULL;
     u->rightChild = NULL;
     q = user_hashtable_p[H(userID)];
     while (q != NULL){
        if (q->userID == userID) break;
        q = q->next;
     }
     if(q == NULL) return 0;
     q->history = insert_fp(q->history , u);
     Di3eMoyToFiloSoy(q->history);
     return 1;
 }

/**
 * @brief Identify the best rating score movie and cluster all the movies of a category.
 *
 * @param category The category of the movie
 *
 * @return 0 on success
 *         1 on failure
 */


 movie_t** fill(movie_t** t , movie_t* tree, int category){
     static in;
     movie_t **head;
     head = t;
     if(t[0] == NULL) in = 0;
     if (tree == categoryArray[category]->sentinel) return head;
     t = fill(t , tree->leftChild, category);
     while(t[in] != NULL){
	if (in < categoryArray[category]->movieCounter-1 )  in++;
	else return head;
     }
     t[in] = tree;
     t = fill(t , tree->rightChild, category);
     return head;
 }

int findMax(movie_t** temp, int n){
     int i, max=0;
     for (i=1;i<n;i++){
        if (temp[max]->medianScore < temp[i]->medianScore)
            max = i;
     }
     return max;
 }

 void swap(movie_t** a, movie_t** b){
     movie_t* temp;
     temp = *a;
     *a = *b;
     *b = temp;
     return;
 }

 movie_t** sort(movie_t** temp, int n){
     int  i , key;
     for (i=(n-1);i>=0;i--){
        key = findMax(temp,i);
        swap(&temp[key], &temp[i]);
     }
     return temp;
 }

 void print(movie_t** temp, int n){
     int i=n-1;
     printf("High Rated Movies [score>7]\n");
     while(temp[i]->medianScore > 7){
        printf("\t<%d> <%f>\n", temp[i]->movieID, temp[i]->medianScore);
        i--;
     }
     printf("Medium Rated Movies [7>=score>=5]\n");
     while(temp[i]->medianScore > 4){
        printf("\t<%d> <%f>\n", temp[i]->movieID, temp[i]->medianScore);
        i--;
     }
     printf("Low Rated Movies [score<5]\n");
     while(i >= 0){
        printf("\t<%d> <%f>\n", temp[i]->movieID, temp[i]->medianScore);
        i--;
     }
     printf("DONE\n");
     return;
 }
/**
 * @brief Identify the best rating score movie and cluster all the movies of a category.
 *
 * @param category The category of the movie
 *
 * @return 0 on success
 *         1 on failure
 */

 int cluster_movies(int category){
     movie_t **temp;
     int i;
     temp = (movie_t**)malloc(categoryArray[category]->movieCounter*sizeof(movie_t*));
     for (i=0 ; i< categoryArray[category]->movieCounter ; i++){
            temp[i] = (movie_t*)malloc(sizeof(movie_t));
	    temp[i] = NULL;
     }
     temp = fill(temp, categoryArray[category]->movie, category);
     temp = sort(temp,categoryArray[category]->movieCounter);
     print(temp,categoryArray[category]->movieCounter);
     for (i=0 ; i< categoryArray[category]->movieCounter ; i++){
	free(temp[i]);
     }
     free(temp);
	 return 1;
 }

/**
 * @brief Find the median score that user rates movies.
 *
 * @param userID The identifier of the user
 *
 * @return 0 on success
 *         1 on failure
 */


 userMovie_t* FindNextLeaf(userMovie_t* u){
    if (u == NULL) return NULL;
    if (u->parent == NULL) return NULL;
    if(u == u->parent->leftChild) {
        u = u->parent->rightChild;
        while (u->leftChild != NULL) u = u->leftChild;
        return u;
    }else{
        while (u->parent != NULL){
            if (u == u->parent->leftChild){
                u = u->parent->rightChild;
                break;
            }
            u = u->parent;
        }
        if (u->parent == NULL)  return NULL;
        while (u->leftChild != NULL) u = u->leftChild;
        return u;
    }
    return NULL;
 }

 double FindSum(userMovie_t *u){
     int sum=0 , count=0;
     double avg=0;
     while (u != NULL){
        sum += u->score;
        count++;
        avg =(double) sum / count;
        u = FindNextLeaf(u);
     }
     return avg;
 }
/**
 * @brief Find the median score that user rates movies.
 *
 * @param userID The identifier of the user
 *
 * @return 0 on success
 *         1 on failure
 */

 int user_stats(unsigned int userID){
     user_t *q;
     userMovie_t *u;
     q = user_hashtable_p[H(userID)];
     while (q != NULL){
        if (q->userID == userID) break;
        q = q->next;
     }
     if(q == NULL) return 0;
     u = q->history;
     if (u == NULL) return 0;
     while ( u->leftChild != NULL)  u = u->leftChild;
     printf(" Mscore: <%f>\nDONE\n",FindSum(u));
	 return 1;
 }

/**
 * @brief Search for a movie with identification movieID in a specific category.
 *
 * @param movieID The identifier of the movie
 * @param category The category of the movie
 *
 * @return 0 on success
 *         1 on failure
 */

 int search_movie(unsigned int movieID, int category){
     movie_t *p;
     p = categoryArray[category]->movie;
     while (p != NULL){
        if (p->movieID == movieID)  break;
        else if (p->movieID > movieID) p = p->leftChild;
        else p = p->rightChild;
     }
     if (p == NULL) return 0;
     printf("\t<%d> <%f>\nDONE\n", p->year, p->medianScore);
     return 1;
 }

 /**
 * @brief Prints the movies in movies categories array.
 * @return 0 on success
 *         1 on failure
 */

 int print_movies(void){
     int i;
     printf("Movie Category Array:\n");
     for (i=0;i<5;i++){
        printf("\t<Category %d>:  ",i+1);
        print_new(categoryArray[i]->movie);
        printf("\n");
     }
     printf("DONE\n");
	 return 1;
 }

  /**
 * @brief Prints the users hashtable.
 * @return 0 on success
 *         1 on failure
 */

 int print_users(void){
     user_t *u;
     userMovie_t *m;
     int i;
     for (i=0;i<hashtable_size;i++){
        u = user_hashtable_p[i];
        printf("Chain <%d> of Users:\n",i+1);
        while(u != NULL){
            printf("   <%d>\n\tHistory Tree:\n",u->userID);
            m = u->history;
            Di3eMoyToFiloSoy(m);
            u = u->next;
        }
     }
     return 1;
 }

void Di3eMoyToFiloSoy2(userMovie_t* tree){
    if (tree == NULL)   return;
    Di3eMoyToFiloSoy2(tree->leftChild);
    if (tree->leftChild == NULL)    printf("<%d> ", tree->movieID);
    Di3eMoyToFiloSoy2(tree->rightChild);
    return;
}
 /**
 * @brief Prints all the system information.
 * @return 0 on success
 *         1 on failure
 */
int print_world(void){
     user_t *u;
     userMovie_t *m;
     int i;
     for (i=0;i<hashtable_size;i++){
        u = user_hashtable_p[i];
        printf("Chain <%d> of Users:\n",i+1);
        while(u != NULL){
            printf("\t<%d>\n\t History Tree:  ",u->userID);
            m = u->history;
            Di3eMoyToFiloSoy2(m);
	    printf("\n");
            u = u->next;
        }
     }
     printf("\n");
     print_movies();
     printf("\nNew releases Tree:\n\t<new_releases>:  ");
     print_new(new_releases);
     printf("\nDONE\n");
     return 1;
 }


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>

#include "moviedb.h"

#define BUFFER_SIZE 1024  /**< Maximum length of a line in input file */

/* Uncomment the following line to enable debugging prints
 * or comment to disable it */
#define DEBUG

#ifdef  DEBUG
#define DPRINT(...) fprintf(stderr, __VA_ARGS__);
#else  /* DEBUG*/
#define DPRINT(...)
#endif  /* DEBUG */

struct movie * Movie_categories[M]={NULL,NULL,NULL,NULL,NULL,NULL};
struct user * users_list;
struct user * users_sentinel;

int register_user(int uid) {
	static int i=0;
	struct user *p;
	if (i==0){
		p = (struct user*)malloc(sizeof(struct user));
		p->uid = -1;
		p->history = NULL;
		p->favorites = NULL;
		users_list = p;
		i++;
	}
	p = (struct user*)malloc(sizeof(struct user));
	if (p ==  NULL) return 0;
	p->uid = uid;
	p->favorites = NULL;
	p->history = NULL;
	p->next = users_list;
	users_list = p;
	printf("R %d\n\n\tUsers =",uid);
	for(p = users_list ; p ; p = p->next){
		printf(" %d ",p->uid);
	}
	printf("\n\nDONE\n");
	return 1;
}

int unregister_user(int uid) {
    struct user *p = users_list;
    struct user *prev = users_list;
    struct user_movie *r , *t;
    users_sentinel->uid = uid;
    if (users_list->uid == uid){
        for (r = users_list->favorites ; r ; r = r->next)   if ( !(r->next) )   break;
        t = r;
        for (r = r->prev ; r != users_list->favorites ; r = t){
            t = r->prev;
            free(r->next);
            r->next = NULL;
        }
        free(users_list->favorites);

       for (r = users_list->favorites ; r ; r = r->next)   if ( !(r->next) )   break;
        t = r;
        for (r = r->prev ; r != users_list->history ; r = t){
            t = r->prev;
            free(r->next);
            r->next = NULL;
        }
        free(users_list->history);

        users_list = users_list->next;
        free(p);
        return 1;
    }
    p = p->next;
    while ( (p != users_sentinel)  &&  (p->uid != uid) )  {
            prev = prev->next;
            p = p ->next;
    }
    if  (p != users_sentinel) return 0;
    for (r = users_list->favorites ; r->next ; r = r->next);
    t = r;
    for (r = r->prev ; r != users_list->favorites ; r = t){
        t = r->prev;
        free(r->next);
        r->next = NULL;
    }
    free(users_list->favorites);
    for (r = users_list->history ; r->next ; r = r->next);
    t = r;
    for (r = r->prev ; r != users_list->favorites ; r = t){
        t = r->prev;
        free(r->next);
        r->next = NULL;
    }
    free(users_list->history);
    prev->next = p->next;
    free(p);
	return 1;
}

int add_new_movie(int mid, int category, int year) {
    struct movie *p;
    struct movie *prev, *head, *q;
    p = (struct movie*)malloc(sizeof(struct movie));
    if (p==NULL) return 0;
    p->mid = mid;
    p->category = category;
    p->year = year;
    q = Movie_categories[5];
    if ( !(q) ){
	p->next = NULL;
        Movie_categories[5] = p;
        return 1;
    }
    if (Movie_categories[5]->mid > mid){
        p->next = Movie_categories[5];
        Movie_categories[5] = p;
        return 1;
    }
    head = Movie_categories[5];
    prev = Movie_categories[5];
    for (q = Movie_categories[5]->next; q ; q = q->next ){
        if (q->mid > mid){
            p->next = q;
            prev->next = p;
	    Movie_categories[5] = head;
	    return 1;
        }
	prev = prev->next;
    }
    p->next = NULL;
    prev->next = p;
    Movie_categories[5] = head;
    return 1;
}

int categorize_movies() {

    int i;
    struct movie *ptr[5], *p, *f, *prev, *head;
    for (i=0;i<5;i++)  ptr[i] = Movie_categories[i];
    while (Movie_categories[5]){
        f = Movie_categories[5];
        p = (struct movie*)malloc(sizeof(struct movie));
        p->category = Movie_categories[5]->category;
        p->mid = Movie_categories[5]->mid;
        p->year = Movie_categories[5]->year;
        if (!(Movie_categories[p->category])) {

            Movie_categories[p->category] = p;
            p->next = NULL;
            ptr[p->category] = p;
            Movie_categories[5] = Movie_categories[5]->next;
            free(f);
            continue;
        }
        prev = NULL;
        while (ptr[p->category]){
            if (ptr[p->category]->mid < p->mid){
                if(!(ptr[p->category]->next)){
                    p->next = NULL;
                    ptr[p->category]->next = p;
                    ptr[p->category] = ptr[p->category]->next;
                    break;
                }
                prev = ptr[p->category];
                printf("%d",prev->mid);
                ptr[p->category] = ptr[p->category]->next;
                continue;
            }
            if(!(prev)){
                p->next = ptr[p->category];
                ptr[p->category] = p;
                ptr[p->category] = ptr[p->category]->next;
                break;
            }
            p->next = ptr[p->category];
            prev->next = p;
            ptr[p->category] = ptr[p->category]->next;
            break;
        }
        Movie_categories[5] = Movie_categories[5]->next;
        free(f);
    }



    printf("C\n\n\tMOVIES:\n\n");
    for (i=0 ; i<6 ; i++){
        printf("\t%d:",i);
	head = Movie_categories[i];
        for (p = Movie_categories[i] ; p ; p = p->next){
            printf("\t%d",p->mid);
        }
	Movie_categories[i] = head;
        printf("\n\n");
    }
    printf("DONE\n");
	return 1;
}

int rate_movie(int uid, int mid, int score) {
    int i;
    struct user *u;
    struct movie *p;
    struct user_movie *t ,*q;
    for (i=0 ; i<6 ; i++){
        for (p=Movie_categories[i] ; p ; p = p->next){
            if (p->mid == mid) break;
        }
        if (!(p))  continue;
        if (p->mid == mid)   break;
    }
    if (!(p))  return 0;
    q = (struct user_movie*)malloc(sizeof(struct user_movie));
    q->mid = mid;
    q->score = score;
    q->category  = p->category;
    for (u = users_list ; u ; u = u->next)   if (u->uid == uid)    break;
    if (!(u))  return 0;
    if (u->uid != uid)  return 0;
    if (!(u->history)) {
        q->prev = NULL;
        q->next = NULL;
        u->history = q;
	printf("G %d %d %d\n\n",uid,mid,score);
	printf("\tHISTORY: %d, %d\n\nDONE\n",mid,score);
        return 1;
    }
    for (t = u->history ; t ; t = t->next){
        if (t->score >= score){
	    if (!(t->next)){
		q->prev = t;
		q->next = NULL;
		t->next = q;
		break;
	    }
            continue;
	}
	if(!(t->prev)){
		q->next = t;
		q->prev = NULL;
		t->prev = q;
		u->history = q;
		break;
	}
        q->next = t;
        q->prev = t->prev;
	t->prev->next = q;
	t->prev = q;
	break;
   }
	t = u->history;
	printf("G %d %d %d\n\n\tHISTORY:\t",uid,mid,score);
	while (t){
		printf("%d, %d  ",t->mid,t->score);
		t=t->next;
	}
	printf("\n\nDONE\n");
        return 1;
}

int identify_favorite_movies() {
    struct user *p;
    struct user_movie *q, *t, *r;
    printf("F\n\n");
    p = users_list;
    for(p;p;p = p->next){
        printf("\tFAVORITES:");
        for (r = users_list->favorites ; r ; r = r->next)   if ( !(r->next) )   break;
        t = r;
        if (t){
            for (r = r->prev ; (r != users_list->favorites && r) ; r = t){
                t = r->prev;
                free(r->next);
            }
            free(users_list->favorites->next);
            free(users_list->favorites);
        }
        for (q = p->history ; q ; q = q->next)    if ((q->score < 7) || q->next)   break;
        
	if (q->score < 7)   q = q->prev;
        for (q  ; q !=  p->history ; q = q->prev){
            t = (struct user_movie*)malloc(sizeof(struct user_movie));
            t->category = q->category;
            t->mid = q->mid;
            t->score = q->score;
            t->prev = NULL;
            if ( !(p->favorites) ) {
                t->next = NULL;
                p->favorites = t;
                continue;
            }
            t->next = p->favorites;
            p->favorites->prev = t;
            p->favorites = t;
        }
        for (q = p->favorites ; q ; q = q->next){
            printf("  %d, %d",q->mid , q->score);
        }
        printf("\n");
    }
    printf("DONE\n");
    return 1;
}
int suggest_movie(int uid, int mid) {
    struct user *p;
    struct user_movie *q , *t;
    for (p = users_list ; p ; p = p->next){
        if (p->uid == uid)  break;
    }
    if (!(p)) return 0;
    printf("S\t%d\t%d\n\n",uid,mid);
    for (q = p->favorites ; q ; q = q->next){
        if (q->mid != mid)  continue;
        printf("\tPrimary movie:\t%d\t%d\t%d\n\n",mid,q->score,q->category);
        for (t = p->favorites ; t ; t = t->next){
            if ( (t->category != q->category) || (t->score <= q->score) )  continue;
            printf("\tSuggested movie:\t%d\t%d\t%d\n\nDONE\n",t->mid,t->score,t->category);
            return 1;
        }
    }
    return 0;
}

int search_movie(int mid) {
    struct movie *p;
    int i;
    for (i=0 ; i<6 ; i++){
        for (p=Movie_categories[i] ; p ; p = p->next){
            if (p->mid == mid){
                printf("I\t%d\n\n\tCategory %d, Year %d\n\nDONE\n",p->mid,p->category,p->year);
                return 1;
            }
        }
    }
	return 0;
}

int print_movies(void) {
    struct movie *p;
    int i;
    if (Movie_categories[0]==NULL && Movie_categories[1]==NULL &&
         Movie_categories[2]==NULL && Movie_categories[3]==NULL &&
         Movie_categories[4]==NULL && Movie_categories[5]==NULL) { return 0;}
    printf("M\n\nMOVIES:\n\n");
    for (i=0 ; i<6 ; i++){
        printf("\tCategory %d :",i);
        for  (p=Movie_categories[i] ; p ; p = p->next ){
            printf("\t%d",p->mid);
        }
        printf("\n");
    }
    printf("DONE\n");
	return 1;
}

int print_users(void) {
    struct user *p;
    struct user_movie *q;
    if (users_list == users_sentinel) return 0;
    printf("P\n\n\tUSERS:\n\n");
    for (p = users_list; p == users_sentinel; p = p->next){
        printf("\t%d\n",p->uid);
        printf("\t\tHISTORY:\t");
        for (q = p->history; q; q = q->next){
            printf("%d\t",q->mid);
        }
        printf("\n\n\t\tFAVORITES:\t");
        for (q = p->favorites; q; q = q->next){
            printf("%d\t",q->mid);
        }
        printf("\n\n");
    }
    printf("DONE\n");
	return 1;
}





/**
 * @brief The main function
 *
 * @param argc Number of arguments
 * @param argv Argument vector
 *
 * @return 0 on success
 *         1 on failure
 */

int main(int argc, char** argv)
{
	FILE *fin = NULL;
	char buff[BUFFER_SIZE], event;

	/* Check command buff arguments */
	if ( argc != 2 ) {
		fprintf(stderr, "Usage: %s <input_file> \n", argv[0]);
		return EXIT_FAILURE;
	}

	/* Open input file */
	if (( fin = fopen(argv[1], "r") ) == NULL ) {
		fprintf(stderr, "\n Could not open file: %s\n", argv[1]);
		perror("Opening test file\n");
		return EXIT_FAILURE;
	}

	/* Read input file buff-by-buff and handle the events */
	while ( fgets(buff, BUFFER_SIZE, fin) ) {

		DPRINT("\n>>> Event: %s", buff);

		switch(buff[0]) {

		/* Comment */
		case '#':
			break;

		/* Big Bang
		 * B */
		case 'R':
		{
			int uid;
			sscanf(buff, "%c %d", &event, &uid);

			if ( register_user(uid) ) {
				DPRINT("R succeeded\n");
			} else {
				fprintf(stderr, "R failed\n");
			}

			break;
		}

		/* Create a new Galaxy
		 * G <gid> */
		case 'U':
		{
			int uid;
			sscanf(buff, "%c %d", &event, &uid);
			DPRINT("%c %d\n", event, uid);

			if ( unregister_user(uid) ) {
				DPRINT("%c %d succeeded\n", event, uid);
			} else {
				fprintf(stderr, "%c %d failed\n", event, uid);
			}

			break;
		}

		/* Create a new Star
		 * S <sid> <gid> */
		case 'A':
		{
			int mid;
			int category;
			int year;
			sscanf(buff, "%c %d %d %d", &event, &mid, &category, &year);
			DPRINT("%c %d %d %d\n", event, mid, category, year);

			if ( add_new_movie(mid, category, year) ) {
				DPRINT("%c %d %d %d succeeded\n", event, mid, category, year);
			} else {
				fprintf(stderr, "%c %d %d %d failed\n", event, mid, category, year);
			}

			break;
		}

		/* Create a new Planet
		 * P <pid> <distance> <sid> */
		case 'C':
		{
			DPRINT("%c\n", event);

			if ( categorize_movies() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}

		/* Delete a solar system
		 * D <sid> <distance> */
		case 'G':
		{
			int uid;
			int mid;
			int score;
			sscanf(buff, "%c %d %d %d", &event, &uid, &mid, &score);
			DPRINT("%c %d %d %d\n", event, uid, mid, score);

			if ( rate_movie(uid, mid, score) ) {
				DPRINT("%c %d %d %d succeeded\n", event, uid, mid, score);
			} else {
				fprintf(stderr, "%c %d %d %d failed\n", event, uid, mid, score);
			}

			break;
		}

		/* Trigger an orphan planet crash with a planet
		 * O <oid> <pid> */
		case 'F':
		{
			DPRINT("%c\n", event);

			if ( identify_favorite_movies() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}

		/* Trigger an orphan planet cluster split
		 * C <cid1> <cid2> <cid3> */
		case 'S':
		{
			int uid;
			int mid;
			sscanf(buff, "%c %d %d\n", &event, &uid, &mid);
			DPRINT("%c %d %d\n", event, uid, mid);

			if ( suggest_movie(uid, mid) ) {
				DPRINT("%c %d %d succeeded\n", event, uid, mid);
			} else {
				fprintf(stderr, "%c %d %d failed\n", event, uid, mid);
			}

			break;
		}

		/* Trigger the merge of two galaxies
		 * M <gid1> <gid2> */
		case 'I':
		{
			int mid;
			sscanf(buff, "%c %d", &event, &mid);
			DPRINT("%c %d\n", event, mid);

			if ( search_movie(mid) ) {
				DPRINT("%c %d succeeded\n", event, mid);
			} else {
				fprintf(stderr, "%c %d failed\n", event, mid);
			}

			break;
		}

		/* Lookup a planet
		 * L <pid> */
		case 'M':
		{
			DPRINT("%c\n", event);

			if ( print_movies() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}

		/* Find planets in range
		 * F <pid> <distance> */
		case 'P':
		{
			DPRINT("%c\n", event);

			if ( print_users() ) {
				DPRINT("%c succeeded\n", event);
			} else {
				fprintf(stderr, "%c failed\n", event);
			}

			break;
		}

		/* Empty line */
		case '\n':
			break;

		/* Ignore everything else */
		default:
			DPRINT("Ignoring buff: %s \n", buff);

			break;
		}
	}

	return (EXIT_SUCCESS);
}


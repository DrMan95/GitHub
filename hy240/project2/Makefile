all: pro

pro: Movie.o main.o
	gcc Movie.o main.o -o pro

Movie.o: Movie.c Movie.h
	gcc -c Movie.c

main.o: main.c Movie.h
	gcc -c main.c


clear:
	rm *.o

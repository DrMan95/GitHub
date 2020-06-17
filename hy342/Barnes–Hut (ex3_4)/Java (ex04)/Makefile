JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		Barnes_hut.java \
        Universe.java \
        Planet.java \
        Coordinate.java \
        Velocity.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
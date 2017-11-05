JCC = javac
JFLAGS = -g

default: treesearch.class

treesearch.class: treesearch.java
	$(JCC) $(JFLAGS) treesearch.java

clean:
	$(RM) *.class

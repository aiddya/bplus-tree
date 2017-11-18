JCC = javac
JFLAGS = -g
PKGDIR = com/iddya/trees

default: KeyValue.class\
	BPTNode.class\
	BPlusTree.class\
	treesearch.class

KeyValue.class: $(PKGDIR)/KeyValue.java
	$(JCC) $(JFLAGS) $(PKGDIR)/KeyValue.java

BPTNode.class: $(PKGDIR)/BPTNode.java
	$(JCC) $(JFLAGS) $(PKGDIR)/BPTNode.java

BPlusTree.class: $(PKGDIR)/BPlusTree.java
	$(JCC) $(JFLAGS) $(PKGDIR)/BPlusTree.java

treesearch.class: treesearch.java
	$(JCC) $(JFLAGS) treesearch.java

clean:
	$(RM) *.class
	$(RM) $(PKGDIR)/*.class

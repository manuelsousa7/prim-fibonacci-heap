JFLAGS = -g:none -nowarn -d ./out -sourcepath ./src

JC = javac

.SUFFIXES: .java .class

.java.class:
	@-$(JC) $(JFLAGS) $*.java

CLASSES = \
./src/ads/neeraj2608/types/common/Edge.java \
./src/ads/neeraj2608/types/common/Graph.java \
./src/ads/neeraj2608/mst/simplescheme/SimpleSchemeMSTGenerator.java \
./src/ads/neeraj2608/mst/fheapscheme/FHeapSchemeMSTGenerator.java \
./src/ads/neeraj2608/mst/common/MSTGenerator.java \
./src/ads/neeraj2608/mst/common/GraphGenerator.java \
./src/ads/neeraj2608/mst/common/MSTGeneratorInterface.java \
./src/ads/neeraj2608/types/fheapscheme/FHeapNode.java \
./src/ads/neeraj2608/types/fheapscheme/FHeap.java \
./src/mst.java \

default: clean info compile run

clean:
	@echo "Removing old class files..."
	@rm -fr out

info:
	@echo "Compiling source..."
	@mkdir out

compile: $(CLASSES:.java=.class)

run:
	@java -cp ./out mst

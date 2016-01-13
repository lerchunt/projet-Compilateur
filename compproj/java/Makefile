
SRC= Lexer.java Parser.java 
SRC_FILES = $(filter-out TestGenerateS.java, $(wildcard *.java))

all: ${SRC} 
	javac -cp java-cup-11b-runtime.jar:. ${SRC_FILES}

Lexer.java: Lexer.flex
	jflex/bin/jflex Lexer.flex 

Parser.java: Parser.cup
	java -jar java-cup-11b.jar -nonterms -expect 150 -interface -parser Parser Parser.cup

clean:
	rm -rf *.class rm sym.java Lexer.java Parser.java *~ tests/TestsOk/*.s tests/TestsOk/*.o tests/TestsOk/*.arm tests/TestsOk/*~ tests/TestsErreurs/*.s tests/TestsErreurs/*.o tests/TestsErreurs/*.arm tests/TestsErreurs/*~

test:
	./test.sh ${ARGS}

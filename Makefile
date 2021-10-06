SRCS=$(shell find src/main/java -name \*.java)
CLZDIR=target/classes
clilib-1.jar:
	mkdir -p $(CLZDIR)
	javac -d $(CLZDIR) $(SRCS)
	jar cf $@ -C target/classes com


.PHONY: all clean

all:
	mvn compile package
	cat stub.sh target/webappserver.jar > webappserver
	chmod 755 webappserver

clean:
	mvn clean
	$(RM) webappserver

all:
	javac -cp .:jsch-0.1.52.jar:org.eclipse.jgit-3.7.1.201504261725-r.jar:slf4j-api-1.5.8.jar:slf4j-simple-1.5.8.jar CloneRepos.java
	java -Xmx2g -cp .:jsch-0.1.52.jar:org.eclipse.jgit-3.7.1.201504261725-r.jar:slf4j-api-1.5.8.jar:slf4j-simple-1.5.8.jar CloneRepos

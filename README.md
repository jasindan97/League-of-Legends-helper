# assignment2fall2019-jasindan97
assignment2fall2019-jasindan97 created by GitHub Classroom

# Jasindan Rasalingam - 100584595

# Distributed Assignment 2: Java RMI - Client/Server application
# RITO GAMES ESPORTS LEAGUE OF LEGENDS CLIENT - NOT associated with RIOT GAMES

# How to run: Using Eclipse 
1. Both folders Client and Server should be ran as separate projects. (I used ecplipse)
2. Copy jsoup-1.12.1.jar into Server's src, then right click on the jsoup-1.12.1.jar file and build path
3. Client doesn't need any external libraries added to it.

#. Can now run Application:
1. Open new cmd window (has to be Windows), navigate into Server src file and run : rmiregistry
2. open separate cmd window for running Client and Server.
3. on the Server window, navigate to src folder in Server project folder, then run: 
    1. javac -cp jsoup-1.12.1.jar *.java
    2. java -cp jsoup-1.12.1.jar;. -Djava.security.policy=policy.txt FileServer
4.  on the Client window, navigate to src folder in Client project folder, then run:
    1. javac *.java
    2. java FileClient localhost
    
5. You will know you are connected when the Applictions menu bar pops up on the client window.
  

# CS6650 23 Spring Project2
## RPC Key Store
Server stores key value pair in a hashmap. Client can put/get/delete key value pair from the store.

### Build and run
#### Approach 1: Jar
A jar package is generated in target/ project3-1.0-SNAPSHOT.jar
- Change the version of runtime java as <b>JDK 15</b>
- open terminal and navigate to folder target/

Run the cmd to start server
```
java -cp project3-1.0-SNAPSHOT.jar KeyStoreServer
```

Open another terminal window, run the cmd to start client
```
java -cp project3-1.0-SNAPSHOT.jar KeyStoreClient
```


#### Approach 2: IntelliJ
- Use IntelliJ to create maven project from existing sources based on the code.
  Java SDK version is 15.
- Run server. Run the main function in src/main/java KeyStoreServer
- Run client. Run the main function in src/main/java KeyStoreClient
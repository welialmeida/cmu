# CMU
Testing Mobile Concepts with Android - Java Server

interface flow is complete, currently changing to api 5.0
server implementation and interface implementation are independent
client flow and and information display will be tested before integrating using server
server will be tested before being used in mobile App

to test server:
	cd server/serverHopOnCMU
	mvn install; 
	mvn exec:java;
after turning on server, to turn on client:
	cd server/server/clientDummyTest
	mvn install
	mvn exec:java

at the moment in every command must be sent a public key:
this is for the security part
	choices are in keys folder:
		type client1 or client2 where command line asks u too

this will change so key is only sent at login

server does not yet use thread/client
server keeps account information in filesystem Persistence account
no durability.
all methods implemented, except Post Quiz Answer and Read Quiz Results not yet implemented
no software tests yet
publicKey for signature confirmation given at signUp at the moment -
should we assume previously shared publicKey??
private - public keys used to sign information to protect against man in the middle.
nonce used to protect against replay attack
integrity in message transmission
no confidentiality
no message causality
sessionId does not expire - should it work like this??

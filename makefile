all : clean exec

exec : compil
	java -cp .:JDA-4.1.1_101-withDependencies.jar:json-simple-1.1.1.jar BotDidiDUT.BotDidiDUT TOKEN_BOT

compil : Weather.class
	javac -cp .:JDA-4.1.1_101-withDependencies.jar:json-simple-1.1.1.jar BotDidiDUT/BotDidiDUT.java;

Weather.class :
	javac -cp .:JDA-4.1.1_101-withDependencies.jar:json-simple-1.1.1.jar BotDidiDUT/Weather.java;

clean :
	rm -f BotDidiDUT/*.class

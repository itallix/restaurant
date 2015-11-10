# Restaurant

Database already contains two accounts and one restaurant. 
They are created when application started (see [AutoDevDataPopulator.java](https://github.com/itallix/restaurant/blob/master/src/main/java/org/itallix/restaurant/services/AutoDevDataPopulator.java), [TestFixtures.java](https://github.com/itallix/restaurant/blob/master/src/main/java/org/itallix/restaurant/TestFixtures.java)).

#### Login:

* curl -H "Content-Type: application/json" -X POST -d '{"username":"user", "password":"password"}' http://localhost:8080/auth/login

###### JSESSIONID should be provided with every rest call via curl param: --cookie "JSESSIONID=BLABLABLA".

#### For admin accounts following rests are available:

* curl -i -H "Content-Type: application/json" -X POST -d 'jamies' http://localhost:8080/restaurant/create
* curl -i -H "Content-Type: application/json" -X POST -d '{"restaurantName":"Jamies", "menu": {"pasta":"5.5", "new york":"15"}}' http://localhost:8080/restaurant/menu

#### For user accounts one rest is available:

* curl -i -H "Content-Type: application/json" -X POST -d 'Jamies' http://localhost:8080/vote

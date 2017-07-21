# EMBL-EBI Programming task

###Instalation

Clone repository, go to root directory and execute the Maven pom.xml using the follows command:
```shell
mvn install
```
If Build was successfully, a **EMBL0.1.jar** should be created into **target/ ** directory.

###Start Embedded Web Service

To start the web service execute the *jar* file:
```shell
java -jar EMBL01.jar
```

> Note: The service start at port 8080.

###Usage

The web service uses **POST ** request at local address http://localhost:8080/sort , sending a *JSON* like that:

```javascript
{
	"text" : "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1"
}
```

####Requesting the web service

Using *curl* you can request the web service:

```shell
curl -H "Content-Type: application/json"
	-X POST
	-d '{"text" : "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1"}'
	http://localhost:8080/sort
```

The response should be:

```javascript
{
	"text":"A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003,SRR211001"
}
```


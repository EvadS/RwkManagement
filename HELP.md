# Getting Started

### Reference Documentation
For further reference, please consider the following sections:


curl -X POST "http://localhost:8080/register" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"login\": \"admin@mail.com\", \"password\": \"123456\"}"


curl -X POST "http://localhost:8080/auth" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"login\": \"admin@mail.com\", \"password\": \"123456\"}"
# Create 
--------------- 
http://localhost:8080/searcher
------- 
{
  "addressRequest": {
    "addressLine1": "addressLine1",
     "addressLine2" : "Lenin street",
    "city": "city 8",
    "country": "country",
    "state": "state",
  
    "zipCode": "zipCode"
  },
  "contactInfos": [
    {
      "messengerAddress": "5555",
      "messengerType": "ICQ"
    },
    {
      "messengerAddress": "123456",
      "messengerType": "SKYPE"
    }

    
  ],
  "email": "232323@mail.com",
  "firstName": "first name 111112",
  "lastName": "last name 2",
  "reviewDate": 0,
  "skillScoreRequestList": [
    {
      "score": 1,
      "skillName": "Cpp"
    },
      {
      "score": 1,
      "skillName": "Java"
    }
    ,
      {
      "score": 1,
      "skillName": "SQL"
    }
  ]
}

--------------------------------

## Environments 

start with maven plugin

```bash
    mvn spring-boot:run -Drun.profiles=dev
```
start as a java application
```bash
    java -jar -Dspring.profiles.active=dev target/rw-management-0.0.1-SNAPSHOT.jar
```


при удалении соискателя нужно удалить все значения скилов по уровням

create user 
http://localhost:8000/api/users
{
	"username":"evad2",
	"password":"123456"
}


http://localhost:8000/api/login

{
	"username":"evad2",
	"password":"123456"
}

#Docker 
build the docker image 
```bash
docker build -t spring-boot-rwr .
```
 You can now see the list of all the docker images on your system using the following command 
 ```bash
    docker image ls
```
 Running the docker image
 
 ```bash
 docker run -p 5000:8000 spring-boot-rwr
```
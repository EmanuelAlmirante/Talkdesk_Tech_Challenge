# Talkdesk Tech Challenge

The main objective of this challenge is to implement a service to manage a specific resource: Calls. The Call resource represents a phone call between two numbers with the following attributes:

- Caller Number: the phone number of the caller.
- Callee Number: the phone number of the callee.
- Start Timestamp: start timestamp of the call.
- End Timestamp: end timestamp of the call.
- Type: Inbound or Outbound

This challenge should have two components: a Web Service and a Client.

#### Web Service:

This Web Service should be able to manage and persist the Call resource, providing the following operations:

- Create Calls (one or more).
- Delete Call.
- Get all Calls using pagination and be able to filter by Type.
- Get statistics (the response to this operation should have the values aggregate by day, returning all days with calls):
    - Total call duration by type.
    - Total number of calls.
    - Number of calls by Caller Number.
    - Number of calls by Callee Number.
    - Total call cost using the following rules:
        - Outbound calls cost 0.05 per minute after the first 5 minutes. The first 5 minutes cost 0.10.
        - Inbound calls are free.

To persist the calls you should use any database that you feel comfortable with.

#### Client:

The Client should allow the programmer to use all the operations of the Web Service without having to handle the connection by himself.


### Notes:

For the Call attributes _Start Timestamp_ and _End Timestamp_ I assumed that the timestamp would be in epoch time. Regarding the statistics, the total call duration for each call type is in seconds. I also assumed that the total call cost would refer to the sum of the cost of all the calls made that day and not the cost for each individual call. 

I decided to use an H2 in-memory database as a way to facilitate the development and the deployment of the application. 

### Tech Stack:

- Java 11
- Spring Boot
- Hibernate
- Swagger
- JUnit
- Mockito
- Postman


### Setup:

- Clone/extract project to a folder
- Run the application with:
  - _mvn clean install_
  - _mvn spring-boot:run_
- Test the application with:
  - _mvn test_ -> run all tests
  - _mvn -Dtest=TestClass test_ -> run a single test class
  - _mvn -Dtest=TestClass1,TestClass2 test_ -> run multiple test classes
- Package the application with _mvn package_
- Test using Postman and the file in the folder _postman_collections_

#### With Docker:

- Install Docker on your machine
- Launch Docker
- Run the command _sudo systemctl status docker_ to confirm Docker is running
- Open terminal in the project folder
- Run the command _sudo docker build -t [NAME_OF_IMAGE] ._ to create the Docker image. Replace _NAME_OF_IMAGE_ with a name for that image like, for example, _uphill-solution_
- Run the command _sudo docker run -p 8080:8080 [NAME_OF_IMAGE]_ to launch the application
- Test using Postman and the file in the folder _postman_collections_


### Endpoints:

The documentation of this API can be found at _http://localhost:8080/swagger-ui.html/_ (Note: you need to initialize the application to access this link).

The API endpoints are also documented below, as well as some examples requests made to the API.

* Create calls:

     **Create Calls (one or more)**

      POST talkdesk/api/call/create-calls
      
     URL: 
     
      http://localhost:8080/talkdesk/api/call/create-calls
      
     Response Status:
     
      200 OK
      
     Body:
     
      [
        {
            "callerNumber": 123456789,
            "calleeNumber": 987654321,
            "callStartTimestamp": 1599909010,
            "callEndTimestamp": 1599942944,
            "callType": "Inbound"
        },
        {
            "callerNumber": 987654321,
            "calleeNumber": 123456789,
            "callStartTimestamp": 1599909010,
            "callEndTimestamp": 1599942944,
            "callType": "Outbound"
        },
         {
            "callerNumber": 987654321,
            "calleeNumber": 123456789,
            "callStartTimestamp": 1599961728,
            "callEndTimestamp": 1599965728,
            "callType": "Outbound"
        }
      ]

     Return:
     
      [
        {
            "id": 1,
            "callerNumber": 123456789,
            "calleeNumber": 987654321,
            "callStartTimestamp": 1599909010,
            "callEndTimestamp": 1599942944,
            "callType": "Inbound"
        },
        {
            "id": 2,
            "callerNumber": 987654321,
            "calleeNumber": 123456789,
            "callStartTimestamp": 1599909010,
            "callEndTimestamp": 1599942944,
            "callType": "Outbound"
        },
        {
            "id": 3,
            "callerNumber": 987654321,
            "calleeNumber": 123456789,
            "callStartTimestamp": 1599961728,
            "callEndTimestamp": 1599965728,
            "callType": "Outbound"
        }
      ]


* Delete call by id:

     **Delete Call**

      DELETE talkdesk/api/call/delete-call/{callId}
      
     URL: 
     
      http://localhost:8080/talkdesk/api/call/delete-call/1
      
     Response Status:
     
      204 NO CONTENT
      
     Body:
     
      Empty

     Return:
     
      Empty


* Get calls:

     **Get all Calls using pagination and be able to filter by Type**

      GET talkdesk/api/call/get-calls?page={pageNumber}&type={callType}
      
     URL: 
     
      http://localhost:8080/talkdesk/api/call/get-calls?page=1&type=Inbound
      
     Response Status:
     
      200 OK
      
     Body:
     
      Empty

     Return:
     
      [
        {
            "id": 1,
            "callerNumber": 123456789,
            "calleeNumber": 987654321,
            "callStartTimestamp": 1599909010,
            "callEndTimestamp": 1599942944,
            "callType": "Inbound"
        }
      ]
      

* Get calls statistics:

     **Get statistics (the response to this operation should have the values aggregate by day, returning all days with calls)**

      GET talkdesk/api/call/statistics-calls
      
     URL: 
     
      http://localhost:8080/talkdesk/api/call/statistics-calls
      
     Response Status:
     
      200 OK
      
     Body:
     
      Empty

     Return:
     
      [
        {
            "day": "2020-09-12",
            "totalCallsDurationInbound": 33934,
            "totalCallsDurationOutbound": 33934,
            "totalNumberOfCalls": 2,
            "totalCallsCost": 28.55,
            "totalNumberOfCallsByCalleeNumber": [
                {
                    "calleeNumber": 123456789,
                    "totalNumberOfCalls": 1
                },
                {
                    "calleeNumber": 987654321,
                    "totalNumberOfCalls": 1
                }
            ],
            "totalNumberOfCallsByCallerNumber": [
                {
                    "callerNumber": 123456789,
                    "totalNumberOfCalls": 1
                },
                {
                    "callerNumber": 987654321,
                    "totalNumberOfCalls": 1
                }
            ]
        },
        {
            "day": "2020-09-13",
            "totalCallsDurationInbound": 0,
            "totalCallsDurationOutbound": 4000,
            "totalNumberOfCalls": 1,
            "totalCallsCost": 3.6,
            "totalNumberOfCallsByCalleeNumber": [
                {
                    "calleeNumber": 123456789,
                    "totalNumberOfCalls": 1
                }
            ],
            "totalNumberOfCallsByCallerNumber": [
                {
                    "callerNumber": 987654321,
                    "totalNumberOfCalls": 1
                }
            ]
        }
      ]

This API uses a surge pricing design, which is implemented in SurgePriceUtil class. It tracks & stores the hit timestamps of each item in a queue. This queue is inside the HitCounter inner class, which is the value of Hashmap, where Key=Item. HitCounter inner class stores the Queue mentioned above & the intial price of each item.

The design incorporates the following three features:
	1. If any item is viewed more than 10 times within an hour, the price will be surged by 10%. The surge continues by 10% for every subsequent 10 views after that.
	2. If the item is not viewed for atleast 10 times in the last one hour, the price is setback to the intial Price regardless of how many times the price has increased in the last one hour.
	3. If at any time in the last one hour there is less than 10 views, we keep the current price.
This is to consider the demand & supply tradeoff.
	
Its not always possible to buy an item. If an item is not present in the inventory the API would throw an error, as handled in the {hostname}: {port}/api/gilded-rose/buyInventory
Also, in the real application, the count of a particular item has to be maintained and the buy order should be validated against the availability.

Validation of username & password is done against DB as part of authentiaction mechanism(/authenitcate) api & on successful authenitcation JWT tokens are provided.
From there on, the user doesn't need username & password, and could use this JWT for as long as it is valid. 
JWT tokens are extracted to find out the username & it is validated. This way, unauthorized users are prevented from acessing the app.

Unit testing & integration testing for the application was performed together for each module using Mockito.
	1. Controller
	2. Service	
	3. Security 
These are the services which are the core part of the application. So any change happening in future would mostly break the written test cases, thereby 
informing the devleopers who work or debug this code.

The following would be included if this was an actual business application:
1. Surge Design would be maintained as a seperate microservice & this microserivce would be called in an asynchronous way so that the subsequent requests are not blocked, or delayed. Similarly, service registry,  health check & so on would be implemented for all the microservices, thereby tapping the potential of micrservices.
2. The DB primary ids would have kept hidden while sending request for GET &  response back for Buy operation. An internal logic would be utilized to calculate the ID as and when necessary for buy operation & hide IDs for GET operations(like JsonIgnore). Or else would have opted a combination of primary key & foreign key to perisit the data. For now, i have kept ID exposed, to make it simple & to avoid ambuguity as product can have same name, description or price. 
3. High availability & backup recovery would be implemented when the product grows.
4. More metadata of each transaction would be saved and tracked, which can be used for data anaysis & improving the product.
5. Another optimal pricing startegy.available existing strategy in market would be used to meet the tradeoff between demand and supply, rather than using a fixed rate of 10% surge. 
-------------------------------------------------------------------------------------------------------------------------------------
To run the application first time/via Jar file:
1. Remove data.sql(Because this have queries to insert users  into the USER table in DB, which is authenticated via /authenticate & also have delete existing entries & adding some items into ITEM table  )
2. Thi is required if application is run via jar. Run maven install from IDE(or other way via command prompt with required comments) & exceute the jar in command prompt(java -jar gilded-rose-1.0-SNAPSHOT.jar).
3. Run the contents in data.sql via H2 console(http://localhost:{port}/api/gilded-rose/h2/login.jsp) & execute the commands. This is primarly to get the user added in the USER table in DB  & this can be authenticated using /authenticate api. Along with this some values for ITEM table can also get added via this
4. The conitnue further testing

- API Endpoints:</br>
- Context-path: /api/gilded-rose</br>
- Local Swagger URL : http://{local_server_host}:{local_server_port}/api/gilded-rose/swagger-ui/index.html?url=/api/gilded-rose/api-docs</br>
- Get JWT token from /authentication on validating credentials against DB users & use this JWT token for further access. (For postman, either via passing Header-> Authorization: Bearer <JWT Token> or Using Authorization:-> choose Type: Bearer Token. Token:<JWT Token>. In swagger -> click Authorize & put client key as JWT Token )<br>
1. For Authentication & getting JWT access token</br>
Mehtod: POST</br>
URL: http://{local_server_host}:{local_server_port}/api/gilded-rose/authenticate</br>
		
		Request:
		{
		    "username":"miw",
		    "password":"miw"
		}
		
		Response:
		{
		    "jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaXciLCJleHAiOjE2NjAzOTMyNTEsImlhdCI6MTY2MDM1NzI1MX0._8FzuGQNlO20gvRwqEUpv7be3VD1-HxydXBrhwC-2Zo"
		}

2. For getting all Inventories: </br>
Method: GET</br>
URL: http://{local_server_host}:{local_server_port}/api/gilded-rose/getAllInventories</br>
		
		
		Response :
		{
		    "itemEntities": [
			{
			    "id": 101,
			    "name": "Book",
			    "description": "The Book",
			    "price": 100.0
			},
			{
			    "id": 102,
			    "name": "Car",
			    "description": "Lexus",
			    "price": 1000.0
			},
			{
			    "id": 103,
			    "name": "Chocolate",
			    "description": "KitKat",
			    "price": 11.0
			},
			{
			    "id": 104,
			    "name": "Rose",
			    "description": "flower",
			    "price": 1.0
			}
		    ]
		}

3. For getting the requested Inventory: (Here {id} corresponds to name of the item. When {id}=Chocolate, the resonse will be as below)</br>
Method: GET</br>
URL: http://{local_server_host}:{local_server_port}/api/gilded-rose/getInventory/{id} </br>

		
		Response: 
		{
		    "item": {
		            "id": 103,
		            "name": "Chocolate",
		            "description": "KitKat",
		            "price": 10.0
		        }
		}

 
4. For Buy Operation</br>
Method: POST</br>
URL: http://{local_server_host}:{local_server_port}/api/gilded-rose/buyInventory </br>


		Request:
		{
			"buyItems" : {
		    "itemEntities": [
		         {
		            "id": 101,
		            "name": "Book",
		            "description": "The Book",
		            "price": 100.0
		        },
		        {
		            "id": 102,
		            "name": "Car",
		            "description": "Lexus",
		            "price": 1000.0
		        }
				]
			}	
		}
		
		Response: 
		{
		    "orderId": 534,
		    "itemEntities": [
		        {
		            "id": 101,
		            "name": "Book",
		            "description": "The Book",
		            "price": 100.0
		        },
		        {
		            "id": 102,
		            "name": "Car",
		            "description": "Lexus",
		            "price": 1000.0
		        }
		    ],
		    "userInfo": "miw",
		    "buyTime": "2022-08-12 22:27:45"
		}

 ---------------------------------------------------------------------------------------------------------------------------------------

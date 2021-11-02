# spring-web-shop
A running PostgreSQL server is needed, the default port is set to 5433. If you want you use docker, follow the few 
next steps.

### Pull postgres 13.2 docker image (if not already pulled)
`docker pull postgres:13.2`

### Run postgres docker image with host port 5433 exposed 
**(if you want to change the port, keep in mind that the port in the application.properties also needs to be changed)**

`docker run -e POSTGRES_PASSWORD=postgres -p 5433:5432 postgres:13.2`

## Run application
The application can be run either through the IDE or via the command line using the java -jar option

`java -jar spring-web-shop-0.0.1-SNAPSHOT.jar`

##HTTP Request examples (some of them)

#### CREATE CUSTOMER 
`curl --request POST \
--url http://localhost:8765/webshop/customer \
--header 'content-type: application/json' \
--data '{
"firstName": "Drazen",
"lastName": "Bandic",
"email": "drazen.bandic@gmail.com"
}'`


#### CREATE PRODUCT
`curl --request POST \
--url http://localhost:8765/webshop/product \
--header 'content-type: application/json' \
--data '{
"code": "1234567891",
"name": "beer",
"priceHrk": 12.0,
"description": "ipa beer",
"isAvailable": true
}'`

#### CREATE ORDER
`curl --request POST \
--url http://localhost:8765/webshop/order/initialize/1`

#### ADD ORDER ITEM
`curl --request POST \
--url http://localhost:8765/webshop/order/1/add-item \
--header 'content-type: application/json' \
--data '{
"productId": 1,
"quantity": 6
}'`

#### GET ORDER ITEMS
`curl --request GET \
--url http://localhost:8765/webshop/order/1/items`

#### FINALIZE ORDER
`curl --request POST \
--url http://localhost:8765/webshop/order/1/finalize`

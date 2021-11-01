# spring-web-shop

### Pull postgres 13.2 docker image (if not already pulled)
`docker pull postgres:13.2`

### Run postgres docker image with host port 5433 exposed 
**(if you want to change the port, keep in mind that the port in the application.properties also needs to be changed)**

`docker run -e POSTGRES_PASSWORD=postgres -p 5433:5432 postgres:13.2`


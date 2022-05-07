##Application user-ws for cd evaluation

mvn package

docker build -t users-ws-docker-image .

docker-compose -f docker-compose.yaml up -d

kubectl apply -f deployment.yaml


##How to use teh application:

1. First start the mongoDB docker container (docker-compose.yaml) and then the application.

2. Log in to the application, post method on "customServerPort/login". It is necessary encode to base64 the username and the password together separated by "&" (username&password) in the body as x-www-form-urlencoded with the key "credentials".
3. Example: encode "admin&admin" to "YWRtaW4mYWRtaW4=" and send it with the key "credentials"
4. There is already a User registered in the application with email:admin and password:admin
5. All the endpoint are under "customServerPort/api/v1/users" and "customServerPort/api/v1/users/{id}", just need to change the request method.
6. Valid User payload json for testing:
{
   "name":"Example",
   "email":"example@teste.com",
   "password":"123",
   "address":{
   "number":"80",
   "street":"Avenida dos Testes",
   "neighborhood":"Bairro",
   "zipCode":"38400000",
   "country":"BR",
   "state":"MG"
   },
   "phone":null,
   "roles":[
   {
   "role":"ROLE_ADMIN"
   }
   ]
}

7. Normally would create a swagger to make things a lot easier, but I ran out of time.

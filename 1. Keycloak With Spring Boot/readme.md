Keycloak with spring boot application
=====================================

<br><h3>Task<h3> 
----------------

	how to secure a spring boot application using Keycloak identity provider.
 
 
<br><h3>Solution<h3> 
-----------------


<br><h4>Keyclaok Setup using UI</h4> 
- Create Realm
- Create Client with necessary field.
- Create user and set password of created user.


<br><h4>Spring Boot Application Setup</h4>
- Set up the properties listed below in the <a href="https://github.com/pradipinexture/keycloak-with-spring-boot/blob/main/1.%20Keycloak%20With%20Spring%20Boot/keycloak-demo/src/main/resources/application.properties">application.properties</a>file. 
	- Realm Name
	- ClientId
	- ClientSecret (If client is private)
	- Other necessary properties

- For coding-related materials for this demonstration, please visit the <a target = "_blank" href="https://docs.google.com/document/d/1p2CvWO3SW7n-37SEJqIbeyJVK1A0KTcUWoab8lXmpcY/edit#heading=h.x3ezebql38vw">4th topic of  Keycloak Document</a>.


- To get started, check out the <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/1.%20Keycloak%20With%20Spring%20Boot/keycloak-demo">keycloak-demo</a> project in git repository.

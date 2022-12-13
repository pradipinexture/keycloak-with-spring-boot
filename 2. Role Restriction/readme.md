Role-Based-Application
======================

- As demonstrated by the figure below, which shows role-based authorization. If a user wants to access an application's resource, they must have the appropriate role.

<img src="Role base authentication diagram.png" alt="Multiple Realms" style="width:700px;height:350px;">

- Here are the procedures for setting up role-based authentication in a spring-boot application.

<h4>Keycloak UI setup</h4>

	1. Create Realm
	2. Create Client with required parameter.
	3. Create User and set password also
	4. Create realm roles - user and admin in keyclaok
	5. Role assign to user in keycloak.


<h4>Spring Boot Application setup</h4>

	1. Add Realm Name, ClientID and ClientSecret(If client private) 
	2. Add resource restriction using role in spring security. ()
	2. Now run this spring boot application
	3. Test it.

- To get started, check out the <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/2.%20Role%20Restriction/role-based-app">Role-Based-Project</a>  in git repository.

Application wise different roles of user
=======================================

- As demonstrated by the figure below, which shows role-based authorization. If a user wants to access an application's resource, they must have the appropriate role.

<img src="Application wise different roles.png" alt="Multiple Realms" style="width:1400px;height:400px;">

- Here are the procedures for setting up role-based authentication in a spring-boot application.

	- Create two client with client specific roles.

	- Create User in keyclaok and assign client 

	- Add resource restriction using role in spring security.

	- Now run this spring boot application

	- Test it.

- To get started, check out the <a  href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/4.%20Application%20wise%20different%20roles%20of%20user/keycloak-demo">First Application</a> and <a  href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/4.%20Application%20wise%20different%20roles%20of%20user/keycloak-demo-2">Second Application</a> in git repository.

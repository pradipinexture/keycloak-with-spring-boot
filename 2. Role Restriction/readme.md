Role-Based-Application
======================

- As demonstrated by the figure below, which shows role-based authorization. If a user wants to access an application's resource, they must have the appropriate role.

<img src="Role base authentication diagram.png" alt="Multiple Realms" style="width:700px;height:350px;">

- Here are the procedures for setting up role-based authentication in a spring-boot application.

	- Create realm roles - user and admin in keyclaok

	-  Role assign to user in keycloak.

	- Add resource restriction using role in spring security.

	- Now run this spring boot application

	- Test it.

- To get started, check out the <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/2.%20Role%20Restriction/role-based-app">Role-Based-Project</a>  in git repository.

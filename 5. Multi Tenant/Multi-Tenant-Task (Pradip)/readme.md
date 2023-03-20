Multi-tenant-Task
=================

- A multi-tenant application is customized for every group of users (so-called tenants) while the entire architecture and core functionality remain the same.


Task for multi-tenant
---------------------

	1. Create one microservice that create realm in keycloak.
	2. Create multiple microservice and implement multi tenancy in these microsevices.
	3. Any user who creates a new realm is redirected to it and given the impression that they have their own virtual organisation.


Solution
--------
- Flow diagram of the Multi Tenant System.
<img src="Multi Tenant Task.png " alt="Multiple Realms" style="width:850px;height:500px;">

<h4>Keycloak UI setup</h4>

	1. Create realm management system client in master realm with required parameter.


<h4>Spring Boot Application setup</h4>

	1. Add Realm Name and ClientID in relam-management-system 
	2. For another microservice configure multi-tenancy.


<br><h3>Explaination</h3>
- We have created below microservices for multi-tenant task

	- realm-management-system
	- user-site
	- admin-site


<br><h4>1. realm-management-system</h4>
- This site accessible by only mater realm's user.
- It has get, create and delete realm functionally.

- When master admin create any realm then few functionally triggered

	- create realm in keycloak using admin-client
	- add common client in new realm
	- create two roles in new realm(user and admin)
	- create default admin in newly created realm  (id=master and pass=123)
	- assign admin role to default user
	- Add keyclaokDeployment entry in DB for multi-tenant feature.
	- If above all operations are done then give two option 
		- Go to User site
		- Go to admin site

- When master admin delete any realm then few functionally triggered

	- Delete realm from keycloak using admin-client
	- Delete KeycloakDeployment entry from DB

- Note : User can not perfrom any operation in master realm.

<br><h4>2. user-site</h4>
- On the user site, users can register and view their profiles.

<br><h4>3. admin-site</h4>
- The admin site features user get, create, and delete functionality.
- Note : If a user is not an admin, they are redirect to access denied page have buttons that say "logout" and "go to user's site". Only admin users are permitted to log into admin sites.

<br><b>NOTE :</b><br>
<br>In <b>application.properties</b> file configure below properties according to your keycloak server configuration.
<br><br>keycloak.auth-server-url = {keycloak server URL}
<br>keycloak-master-admin-username = {your username}
<br>keycloak-master-admin-password = {your password}
<br><br>Also change the password to your database password
<br>spring.datasource.password=password


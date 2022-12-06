Multi-tenant-Task
=================

- A multi-tenant application is customized for every group of users (so-called tenants) while the entire architecture and core functionality remain the same.


Task for multi-tenant
---------------------

	1. Create one microservice that create realm in keycloak.
	2. Create multiple microservice and implement multi tenancy in these microsevices.
	3. when any user create new realm then him/her redirect with newly created realm and he/she feels like I have mine vertual software.


Solution
--------

- We have created below microservices for multi-tenant task

	- realm-management-system
	- user-site
	- admin-site

<br><h3>1. realm-management-system</h3>
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

<br><h3>2. user-site</h3>
- In usersite user can register himself and able to see him/her profile.

<br><h3>3. admin-site</h3>
- admin-site has a functionality like get,create and delete user.
- Note : only admin user can able to login in admin site and user is not admin then redirect to eror page and give two option logout and Go to user site.




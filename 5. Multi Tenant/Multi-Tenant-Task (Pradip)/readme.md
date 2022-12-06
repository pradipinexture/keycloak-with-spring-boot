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

- Created below microservices

	1. realm-management-system
	2. user-site
	3. admin-site
	
1. realm-management-system
- This site accessible by only mater realm's user.
- It has get, create and delete realm functionally.

- When master admin create any realm then few functionally triggered

	1. create realm in keycloak using admin-client
	2. add common client in new realm
	3. create two roles in new realm(user and admin)
	4. create default admin in newly created realm  (id=master and pass=123)
	5. assign admin role to default user
	6. Add keyclaokDeployment entry in DB for multi-tenant feature.
	7. Give two option 
		a. Go to User site
		b. Go to admin site

- When master admin delete any realm then few functionally triggered

	1. Delete realm from keycloak using admin-client
	2. Delete KeycloakDeployment entry from DB

- Note : User can not perfrom any operation in master realm.


2. user-site
- In usersite user can register himself and able to see him/her profile.

3. admin-site
- admin-site has a functionality like get,create and delete user.
- Note : only admin user can able to login in admin site and user is not admin then redirect to eror page and give two option logout and Go to user site.




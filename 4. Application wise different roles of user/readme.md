Application wise different roles of user
=======================================


keycloak 
--------

1. Create two client in keycloak

	EX : spring-boot-app-1, spring-boot-app-2

2. Create realm roles

	EX: 
	  - For spring-boot-app-1 client we create 
	     1. f_user_role, 
	     2. f_admin_role
	  - For spring-boot-app-2 client  we create 
	     1. s_user_role, 
	     2. s_admin_role and 
	     3. s_super_admin_role

3. Create user and assign roles

	EX : we create test user and he has a below roles
		1. f_user_role
		2. s_admin_role
		3. s_super_admin_role

4. Now run both spring boot applications
 
5. Test it.

	EX : 
	- Test user can access user resources and he can not access admin resources of first application.
	- For second application he can able to access admin and super admin resources and can not access user resource.

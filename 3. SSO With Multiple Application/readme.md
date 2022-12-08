SSO with Multiple Application
=============================
<br><h3>Task<h3> 
----------------

	Construct several microservices and set up Keyclaok.
	A user should have access to all resources after logging in with one  application without needing to do it again in another. 
 
 
<br><h3>Solution<h3> 
-----------------

- Firstly we need knowledge of the SSO
- Single sign-on (SSO) is an authentication method that enables users to securely authenticate with multiple applications and websites by using just one set of credentials.
- There are two possibilities for Keyclaok 

<br><h4>1. SSO with Single Realm</h4> 

- As you can see in the diagram below, Once a user logs into YouTube, he or she can access the Drive and Gmail applications because the Google organisation shares a common database across all applications. 
- This scenario is covered in this demonstration. 
<img src="Single Realm.png" alt="Single Realm" style="width:600px;height:350px;">

<br><h4>2. SSO with Multiple Realm</h4> 

- Separate realms each have their own databases, as you can see in the figure below.
As a result, if he wants to use YouTube, he must sign into his Google account. However, if he now wants to use Word, he cannot access Microsoft services without also signing into his Microsoft account. 
- This scenario is covered in this demonstration.
<img src="Multiple Realms.png" alt="Multiple Realms" style="width:700px;height:350px;">

- To get started, check out the <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/3.%20SSO%20With%20Multiple%20Application/keycloak-demo">first app</a> and <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/3.%20SSO%20With%20Multiple%20Application/keycloak-demo2">second app</a>  in git repository.

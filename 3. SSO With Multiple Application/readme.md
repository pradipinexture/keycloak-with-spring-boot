SSO with Multiple Application
=============================
<br><h3>Task<h3> 
----------------

	Create multiple microservices
	Configure keyclaok on those applicatios.
	When user login with any one application then he/she able to access all resources without login on other application..
 
 
<br><h3>Solution<h3> 
-----------------

- Firstly we need knowledge of the SSO
- Single sign-on (SSO) is an authentication method that enables users to securely authenticate with multiple applications and websites by using just one set of credentials.

<img src="https://cdn.pixabay.com/photo/2019/03/28/22/23/link-4088190_960_720.png" alt="HTML tutorial" style="width:42px;height:42px;">

- So in case of keyclaok, if any two or more applications are register on same relam then SSO works.
- EX : 
    - We have a google realm and it clients like youtube, drive and gmail etc.
    - If test user login into gmail then he able to access youtube and gmail without login.

- To get started, check out the <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/3.%20SSO%20With%20Multiple%20Application/keycloak-demo">first app</a> and <a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/3.%20SSO%20With%20Multiple%20Application/keycloak-demo2">second app</a>  in git repository.

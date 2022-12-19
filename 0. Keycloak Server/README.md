Keyclaok Server
===============

Keyclaok have two operating mode 

<br><h3>1. Developer Mode<h3> 
-----------------

- The development mode is targeted for people trying out Keycloak the first time and get it up and running quickly. It also offers convenient defaults for developers.

- In developer mode no need to configure any properties.

- The development mode is started by invoking the following command from keyclaok root direcotry

	bin/kc.sh start-dev

- To get started, check out the (<a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/0.%20Keycloak%20Server/keycloak-19.0.3-dev">Keycloak server with development mode</a> ). 

<br><h3>2. Production Mode<h3> 
 ------------------

- The production mode is for going live.

- The production mode sets the following defaults:

	- HTTP is disabled as transport layer security (HTTPS) is essential
	- HTTPS/TLS configuration is expected
	- Hostname configuration is expected

- Make sure below configuration must be configured to keyclaok

	- https-key-store-file=${kc.home.dir}/conf/localhost.p12
	- https-key-store-password=changeit

	- hostname=localhost


- The production mode is started by invoking the following command from keyclaok root direcotry
	- bin/kc.sh build
	- bin/kc.sh start
	
- To get started, check out the (<a target = "_blank" href="https://github.com/pradipinexture/keycloak-with-spring-boot/tree/main/0.%20Keycloak%20Server/keycloak-19.0.3-prod">Keycloak server with production mode</a>).


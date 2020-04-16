# WEBAPPSERVER
Servidor de aplicaciones web basadas en Java. 

## Novedades
v1.5.0: Software liberado bajo licencia [GNU/GPL v3](https://www.gnu.org/licenses/gpl-3.0.html).

v1.1: Ahora se puede publicar una aplicación web directamente desde el directorio raiz descomprimido.


### Modo de Uso
```
java -jar webappserver.jar -war archivo.war -port 1234
```
donde el parámetro *-war* especifica el archivo .war que contiene la aplicación web, y *-port* el puerto TCP de escucha, o
```
java -jar webappserver.jar -dir path/to/webapp/root 
```
donde el parámetro *-dir* especifica la ruta a la aplicación web sin ensamblar.

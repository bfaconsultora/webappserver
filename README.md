# WEBAPPSERVER
Servidor de aplicaciones web basadas en Java. 

## Novedades
v1.1: Ahora se puede publicar una aplicación web directamente desde el directorio raiz descomprimido.

## Descarga
[Última versión estable](http://artifactory.bfaconsultora.com/artifactory/local/webappserver-1.1.jar)

### Modo de Uso
```
java -jar webappserver-x.x.jar -war archivo.war -port 1234
```
donde el parámetro *-war* especifica el archivo .war que contiene la aplicación web, y *-port* el puerto TCP de escucha, o
```
java -jar webappserver-x.x.jar -dir path/to/webapp/root 
```
donde el parámetro *-dir* especifica la ruta a la aplicación web sin ensamblar.

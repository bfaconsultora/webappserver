# WEBAPPSERVER
Servidor de aplicaciones web basadas en Java. 

## Novedades
### v1.6.0: 
* Se solucionan algunos problemas de CORS. 
* Se migra a JETTY v10.0.0
* Es posible especificar la ip en donde se desea que el servidor escuche (por defecto 0.0.0.0)

### v1.5.0: 
* Software liberado bajo licencia [GNU/GPL v3](https://www.gnu.org/licenses/gpl-3.0.html).

### v1.1.0:
* Ahora se puede publicar una aplicación web directamente desde el directorio raiz descomprimido.
<br><br>
### Compilación
```
$ mvn clean compile package
```

### Modo de Uso
```
webappserver -war archivo.war -port 1234 -ip 192.168.0.1
```
donde 
* **-war** especifica el archivo .war que contiene la aplicación web.
* **-port** el puerto TCP de escucha (por defecto 8080).
* **-ip** la dirección ip de escucha (por defecto 0.0.0.0).

o
```
webappserver -dir path/to/webapp/root -port 1234 -ip 192.168.0.1
```
donde el parámetro **-dir** especifica la ruta a la aplicación web sin ensamblar.

Los parámetros **-war** y **-dir** no pueden utilizarse simultaneamente.

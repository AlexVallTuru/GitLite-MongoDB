# GitLite-MongoDB

![https://github.com/AlexVallTuru](https://github.com/AlexVallTuru/GitLite-MongoDB/blob/master/readme_images/bannerGit.jpg)

Git-Lite es una aplicación de línea de comandos que te permite crear, eliminar y gestionar repositorios remotos de Git de forma sencilla. Con Git Repo Manager, puedes realizar operaciones de push y pull entre tus repositorios locales y remotos, así como clonar repositorios completos.

Funcionalidades
Git Repo Manager proporciona las siguientes funcionalidades:

### CREATE:
crea un repositorio remoto vacío a partir de un directorio local.
### DROP:
elimina un repositorio remoto y todos sus archivos dependientes.
### PUSH:
agrega un archivo local al repositorio remoto solo si el archivo local es más reciente que su equivalente remoto. También puedes especificar el parámetro force para omitir esta verificación y forzar la operación.
### PULL:
descarga un archivo del repositorio remoto al directorio local solo si el archivo local es más antiguo que su equivalente remoto. De nuevo, puedes especificar el parámetro force para omitir la verificación.
### COMPARE:
compara los contenidos de los archivos locales con sus equivalentes remotos y muestra las diferencias. También puedes especificar el parámetro detail para comparar línea por línea los archivos diferentes.
### CLONE:
clona un repositorio remoto creando un directorio local con aquellos archivos remotos con tiempo igual o inferior a un timestamp especificado.

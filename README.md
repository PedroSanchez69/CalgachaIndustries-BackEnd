# CalgachaIndustries-BackEnd
Repositorio del proyecto back-end de Calgacha donde se encuentran todos los métodos REST para la API.

## Requisitos
- Java 17 (para ejecutar localmente sin Docker)
- Docker (opcional, recomendado para despliegue consistente)

## Construir y ejecutar con Docker
1. Construir la imagen Docker (desde la raíz del repositorio):

```powershell
docker build -t calgacha-backend "${PWD}"
```

2. Ejecutar el contenedor (mapea el puerto 8080 por defecto):

```powershell
docker run --rm -e PORT=8080 -p 8080:8080 calgacha-backend
```

El `Dockerfile` usa una construcción multi-stage que compila el proyecto con Maven y luego copia el JAR resultante a una imagen JRE mínima. En tiempo de ejecución se respeta la variable de entorno `PORT` (útil para Render y otros PaaS).

## Despliegue en Render (usando Docker)
1. Subir el repositorio a GitHub (asegúrate de que `Dockerfile` esté en la raíz).
2. En Render, crea un nuevo **Web Service** y conecta tu repositorio de GitHub.
3. Selecciona el branch (por ejemplo `main`) y Render detectará el `Dockerfile` y construirá la imagen.
4. Render asignará automáticamente un `PORT` en ejecución; la imagen respeta `PORT` y el servicio debería funcionar sin configuraciones adicionales.

## Despliegue en Render (sin Docker)
Si prefieres usar el build nativo de Render en lugar de Docker:
- Build Command: `./mvnw -B -DskipTests package` (o `mvn -B -DskipTests package` si no usas el wrapper)
- Start Command: `java -jar target/<nombre-del-jar>.jar --server.port=$PORT`

## Notas
- El `.dockerignore` excluye `target`, `.git`, y archivos de IDE para acelerar builds.
- Si usas CI/CD (GitHub Actions), considera usar la caché de Maven para acelerar builds.
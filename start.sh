#!/bin/bash
JAR_DIR="./back/bio"
DOCKER_COMPOSE_DIR="./src/main/docker"
SPRING_BOOT_DIR="../../../"
SQL_SCRIPT_DIR="./src/main/resources/script/bio_spring_data.sql"
cd $JAR_DIR
echo "Démarrage du build..."
./gradlew clean build -x test   
cp build/libs/bio-0.0.1-SNAPSHOT.jar src/main/docker
echo "Build exécuté avec succès."
echo "Démarrage de Docker Compose..."
cd $DOCKER_COMPOSE_DIR
echo "Démarrage de la base de données et du serveur smtp"
docker-compose up -d db pgadmin smtp4dev
echo "Démarré"
sleep 60
echo "Démarrage de springboot et angular"
docker-compose up -d app angular
sleep 60
echo "Démarré"
echo "Docker Compose démarré avec succès."
cd $SPRING_BOOT_DIR
echo "Exécution du script SQL de jeu de données..."
sleep 30
docker exec -i db psql -U springuser biospring < $SQL_SCRIPT_DIR
echo "Script SQL de jeu de données exécuté avec succès."

# Site Bio International 
## Plateforme de réservation de produits cosmétiques
Ce projet est une plateforme de réservation de produits cosmétiques pour le site Bio International. 

Il est conteneurisé à l'aide de Docker pour simplifier le déploiement et la gestion des environnements de développement et de production.

## Prérequis
Avant de commencer, assurez-vous d'avoir installé Docker sur votre système.

## Instructions pour le lancement
Pour lancer l'application, suivez ces étapes :

Clonez ce dépôt sur votre machine locale :
```bash
git clone https://github.com/vava56754/bio-international.git
```
1. Assurez-vous d'avoir Docker installé sur votre machine.

2. Exécutez le script start.sh situé à la racine du projet :

```bash
bash ./start.sh
```
Ce script effectue les étapes suivantes :

Compile le projet Spring Boot.

Copie le fichier JAR généré dans le répertoire Docker.

Démarre les conteneurs Docker à l'aide de Docker Compose.

Exécute un script SQL pour initialiser la base de données avec des données de test.

Accès à l'application

Une fois que les conteneurs Docker sont démarrés avec succès, vous pouvez accéder à l'application via votre navigateur web à l'adresse suivante : http://localhost:4200

## Configuration

Le fichier docker-compose.yml contient la configuration des services Docker nécessaires à l'application, notamment la base de données PostgreSQL.

Les fichiers Dockerfile permettent de créer les images Docker pour l'application Spring Boot et les autres services nécessaires.

Le répertoire src/main/resources/script/ contient les scripts SQL pour initialiser la base de données avec des données de test.

N'hésitez pas à explorer le code source et les fichiers de configuration pour plus de détails sur le fonctionnement de l'application.


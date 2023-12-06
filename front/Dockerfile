# Étape de construction
FROM node:latest AS builder

WORKDIR /app

# Copier les fichiers du projet Angular dans le conteneur
COPY . .

# Installer les dépendances
RUN npm install

# Construire l'application Angular pour la production

RUN npm run build --omit=dev

# Étape de déploiement avec Nginx
FROM nginx:alpine

COPY angular.conf /etc/nginx/conf.d/angular.conf

# Copier les fichiers de build de l'application Angular dans le répertoire de Nginx
COPY --from=builder /app/dist/front /usr/share/nginx/html

# Exposer le port 80 pour accéder à l'application
EXPOSE 80

# Commande de démarrage pour exécuter Nginx
CMD ["nginx", "-g", "daemon off;"]

package com.perso.bio.constants;

public class MessageConstants {


    public static final String ERROR_MESSAGE = "Erreur lors de la ";

    /*** SERVICES ***/

    public static final String BODY_SERVICE_ERROR_MESSAGE = "Partie de corps non trouvé avec l'ID : ";
    public static final String PRODUCT_TYPE_SERVICE_ERROR_MESSAGE = "Type de produit non trouvé avec l'ID : ";
    public static final String HOUSE_SERVICE_ERROR_MESSAGE = "Maison non trouvé avec l'ID : ";
    public static final String JWT_SERVICE_USER_ERROR_MESSAGE = "Utilisateur non trouvé avec l'username : ";
    public static final String LINE_PROCUREMENT_SERVICE_ERROR_MESSAGE = "Ligne de commande non trouvé avec l'ID : ";
    public static final String PROCUREMENT_SERVICE_ERROR_MESSAGE = "Commande non trouvé avec l'ID : ";
    public static final String PRODUCT_SERVICE_ERROR_MESSAGE = "Produit non trouvé avec l'ID : ";

    /*** JWT ***/

    public static final String TOKEN_UNKNOWN = "Token invalide ou inconnu";
    public static final String TOKEN_INVALID = "Token invalide";
    public static final String DELETE_TOKEN = "Suppression des token à {}";

    public static final String AUTHENTICATION = "Authentication : ";

    public static final String ALL_CLAIMS = "allclaims ";

    /*** FILE ***/

    public static final String FILE_PATH = "src/main/resources/images";
    public static final String FILE_ERROR_UPLOAD = "Impossible d'initialiser le dossier pour le téléchargement !";
    public static final String FILE_ALREADY_EXIST = "Le fichier existe deja.";
    public static final String FILE_READ_ERROR = "impossible de lire le fichier";

    /*** USER ***/

    public static final String USER_NOT_FOUND = "Aucun Utilisateur ne correspond a cette identifiant";
    public static final String CODE_EXPIRE = "Votre code a expire";
    public static final String USER_UNKNOWN = "Utilisateur inconnu";
    public static final String USER_ALREADY_EXIST = "Utilisateur deja existant";

    /*** VALIDATION ***/

    public static final String CODE_INVALID = "Votre code est invalide";

    /*** PRODUCT ***/
    public static final String PRODUCT_NOT_FOUND = "produit non trouvé";

    /*** CONTROLLER ADVICE ***/
    public static final String INTERNAL_ERROR = "Erreur interne du serveur : ";
    public static final String INVALID_FORMAT = "Le format passer ne correspond pas au format attendu : ";
    public static final String MAIL_ALREADY_EXIST = "Cette adresse mail est deja utilisée : ";
    public static final String FILE_NOT_FOUND = "Aucun fichier trouvé : ";
    public static final String MALFORMED_URL = "L'url est incorrect ou mal construite : ";


    /*** CRUD ***/
    public static final String CREATE = "Création Réussie";
    public static final String UPDATE = "Mise à jou Réussie";
    public static final String DELETE = "Suppression Réussie";

    /*** USER CONTROLLER ***/
    public static final String SIGN_IN = "Inscription Réussie";
    public MessageConstants() {
    }
}

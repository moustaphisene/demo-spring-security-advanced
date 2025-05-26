Ce projet a pour objectif d'intégrer progressivement les mécanismes de sécurité dans une application Spring Boot, en suivant un plan d'action structuré en 5 étapes.

  Objectifs du projet
 
Mettre en œuvre les bonnes pratiques de sécurité dans une application Spring Boot à travers :

           L'activation de la sécurité de base
           
           L’implémentation de l’authentification
           
           La gestion des autorisations
           
           La sécurisation des méthodes
           
           L’intégration de JWT avec OAuth2/OpenID

 Sécurité de base

        Création d’un projet Spring Boot
        Ajout de Spring Security
        Activation de la sécurité de base (BasicAuth)

Authentification

       Ajout d’un écran de login personnalisé
       Protection des URLs
       Configuration CSRF et CORS

Autorisation

      Définition des rôles et autorités
      Restriction de l’accès selon les rôles utilisateurs

Sécurité au niveau des méthodes

      Utilisation des annotations @Secured, @PreAuthorize, etc.
      Implémentation de filtres personnalisés pour l’authentification et l’autorisation

Intégration JWT avec OAuth2 / OpenID

       Génération de JWT pour authentification
       Utilisation d’un provider OpenID (Google, Clerk, Be-auth, Keycloak…)
       Sécurisation avancée avec OAuth2

Technologies utilisées

       Java 17+
       Spring Boot 3.x
       Spring Security
       JWT (Java Web Token)
       OAuth2 / OpenID Connect
       Maven 

Si vous avez des questions sur la sécurité de Spring, la première ressource à consulter est la documentation officielle de Spring Security.

     https://spring.io/
Autres ressources complémentaires

       https://www.oauth.com/playground/
       https://stackoverflow.com/
       https://jwt.io/
       https://oauth.net/2/
       https://developers.google.com/identity/protocols/oauth2


     
Les contributions sont les bienvenues ! Merci de bien vouloir créer une issue ou une pull request.

# demo-spring-security-advanced

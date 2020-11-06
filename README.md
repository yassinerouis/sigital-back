
Dans cette partie, je vais donner quelques explications en ce qui concerne le code d'authentification JWT. L'utilisateur saisit son email,son mot de passe et clique sur le bouton connexion, spring security filterChain va appeler la méthode attemptAuthentication de la classe AuthenticationFilter pour récuperer l'utilisateur à partir de la base de données via une implementation de la classe UserDetailsService, après la récuperation de l'utilisateur avec son role la méthode successfulAuthentication est appélee par par spring security filterChain pour générer le token et l'envoyer à l'application front (Angular) pour le stocker dans le local storage, le diagramme de Séquence UML ci-dessous explique mieux:

# Diagramme de Séquence UML

![sigital1](https://user-images.githubusercontent.com/56519992/98405215-2a1c8100-206c-11eb-9df8-acb558e69666.PNG)

# Après Authentification

Après l'authentification, l'utilisateur qui à le rôle "user" pour tout faire en communiquant avec le back-end, mais à condition d'envoyer
le token stocké lors de l'authentification avec chaque requête et il faut qu'il soit valide pour effectuer le traitement demandé. La vérification de la validation du token est au niveau de la classe AuthorizationFilter et plus particulièrement la méthode doInternal.

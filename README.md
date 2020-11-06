
Dans cette partie, je vais donner quelques explications du code de l'authentification JWT, par le diagramme de Séquence UML suivant:

# Diagramme de Séquence UML

![sigital1](https://user-images.githubusercontent.com/56519992/98405215-2a1c8100-206c-11eb-9df8-acb558e69666.PNG)

# Après Authentification

Après l'authentification, l'utilisateur qui à le rôle "user" pour tout faire en communiquant avec le back-end, mais à condition d'envoyer 
le token avec chaque requête envoyée et il faut qu'il soit valide. la vérification est au niveau de la classe AuthorizationFilter 
et plus particulièrement la méthode doInternal.

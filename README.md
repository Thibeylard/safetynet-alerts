[![shields](https://img.shields.io/badge/project%20status-validated-deepgreen)](https://shields.io/)
[![shields](https://img.shields.io/badge/made%20with-java-orange)](https://shields.io/)
[![shields](https://img.shields.io/badge/powered%20by-spring-green)](https://shields.io/)
____________________

> Ce README est basé sur les conclusions évoquées dans la présentation réalisée à la fin du projet.

# Créez votre première application web avec Spring Boot

## Dans le cadre de la formation OpenClassrooms "Développeur d'application Java"

### Objectif du projet
En partant de zéro, développer une application Spring Boot exposant une API basique.

### Progression
Avec la prise en main de Spring Boot, la mise en place d'une API, ce projet SOLID a été très formateur. Beaucoup de nouvelles notions, qui ont forcément amené des questions et des difficultés. Parmi elles : 
* Flow de développement pas encore au point. Trop horizontal et global, plutôt que vertical et itératif
* Les types de retours et de paramètres pour les Controllers (ResponseEntity, Optional) 
* La sérialisation et désérialisation du Json avec Jackson
* La définition des modèles

### Réalisation
En fin de compte, je suis allé au delà de ce qui était demandé, avec plusieurs initiatives :
* Définition d'exceptions personnalisées
* Mise en place du Java Pattern _Factory_ pour faciliter les tests
* Nombreux DTOs spécifiques

Le projet respecte également les principes SOLID, notamment par l'injection des dépendances, les interfaces DAO et leur implémentation, et les services spécifiques.

### Axes d'améliorations
Plusieurs choix de conception pourraient être ré-évalués afin de rendre le programme plus cohérent. On peut envisager entre autres :
* Optimisation de JsonFileDatabase avec une réécriture partielle et non complète du fichier
* Redéfinition plus intuitive des modèles
* Valeurs de retour sur les méthodes HTTP POST, PUT, DELETE avec des DTO plutôt qu'une simple chaîne de caractère
* Utilisation d'une factory ou d'un builder pour construire les DTOs plutôt que dans AlertsService
* Exceptions personnalisées supplémentaire pour la validation des modèles 

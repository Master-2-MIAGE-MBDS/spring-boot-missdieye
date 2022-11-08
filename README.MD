# Projet spring boot Master 2 MBDS 2022 par DIEYE Khadidiatou
## Informations générales 
Ce projet est une plateforme e-commerce. Grace à un système de panier, l'utilisateur peut effectuer des commandes de produits.
Une solution de base est proposée par M Galli, enseignant indépendant et chef de projet à l'Université de Nice Sophia Antipolis.
Cette solution de base comporte déjà la configuration du projet, quelques méthodes et expose dans une interface l'ensemble des annonces créées. Elle est disponible sur gitlab: https://gitlab.com/mooc-sources/spring-boot-microservices.
Mon travail dans ce projet consiste à complèter la plateforme en:
- ajoutant une page de détail du produit,
- ajoutant un bouton pour ajouter des produits au panier,
- ajoutant Un nouveau microservice pour gérer les commandes.
- mettant en place un système de gestion de commande. L'utilisateur grace à un bouton peut commander en validant son panier. A cette étape le panier est vidé et la commande passée.

## Structure du projet
Dans ce projet nous avons 4 microservices qui comminuquent entre eux. Nous avons le microservice:
- **Product** : qui expose un ensemble d'APIs facilitant la gestion des produits à vendre du site;
- **Cart** : qui permet de stocker virtuellement les produits choisis, mais aussi de les gérer;
- **Order**: qui permet de stocker virtuellement les produits validés par l'utilisateur mais aussi d'assurer leur gestion;
- **Client**: qui consommer les services des autres microservices et fournit une interface à partir des données des services.

## Démarrage et compilation
Pour lancer ce projet, il vous suffit de l'ouvrir avec votre IDE et exécuter toutes les classes mains de l'application. Il s'agit de ClientAplication, CartAplication, OrderAplication et ProductAplication.
# Auteur
L'auteur de ce projet est **DIEYE Khadidiatou**.
Mr Galli professeur au sein de l'université Côte d'Azur a beaucoup contribué sur la partie backend.

## Licence
Ce projet est "open source".

## Fonctionnalités
- Affichage Détails d'un produit: Un bouton **Voir les détails** a été créé en dessous de chaque produit pour voir plus d'informations du produit.
- Ajouter au panier: Un bouton **Ajouter au panier** a été créé en dessous de chaque produit pour l'ajouter au panier.
- Affichage détails panier: Une page expose l'ensemble des produits qui ont été ajouté au panier. Nous y avons comme information pour chaque produit: son illustration, son nom, son prix unitaire, la quantité qu'a pris l'utilisateur et le prix total. En bas de la page nous avons le prix total de tout le panier.
- Valider le panier: Un bouton **Valider mon panier** a été créé et permet à l'utilisateur de passer sa commande. Une fois cliquée, le panier est vidé et les produits passent à l'étape commande.

### Idées de fonctionnalités pertinentes développées
- Quand un produit est ajouté plusieurs fois dans le panier, sa quantité est incrémentée,
- L'utilisateur peut incrémenter ou décrémenter le produit à partir de son panier,
- L'utilisateur peut supprimer un produit de son panier,
- L'utiulisateur peut voir toutes les commandes effectuées mais aussi les détails de chacune des commandes.

## Versions
Version de Spring boot : 2.3.4


## Technologies
Les technologies utilisées sont :
- Spring boot,
- HTML,
- CSS.

## Comment contribuer
Pour contribuer, veuillez me demander la permission pour que je puisse créer un document contributeur.

## Versionnement
- Version 1
- Version 2: Créer des profils utilisateurs.

## Crédit
Pour la réalisation de ce projet, nous nous sommes ressourcées:
- des documents du cours donner par Monsieur Galli,
- de https://spring.io/guides .


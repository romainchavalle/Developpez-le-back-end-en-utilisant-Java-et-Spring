# RentalsApp Backend

Ce projet représente la partie backend de l'application **RentalsApp**, une plateforme de mise en relation entre futurs locataires et propriétaires.

## **Fonctionnalités principales**
- Gestion sécurisée des utilisateurs et des propriétés grâce à **Spring Security** et **OAuth2**.
- API REST pour les opérations principales de l'application.
- Documentation des APIs avec **Swagger**.

---

## **Lancement du projet**

### **Prérequis**
- Java 17 ou supérieur
- Maven
- Node.js et Angular CLI (version 14 ou ultérieure)

### **Étapes pour lancer le projet**

1. **Cloner le dépôt** :
   git clone git@github.com:romainchavalle/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
2. **Configurer la base de données**:
  - Installer MySQL et créer la base de donnée
  - Créer une connexion:
    - Créer une nouvelle source de donnée MySQL
    - Configurez les paramètres suivants :
      - Hostname : localhost
      - Port : 3306
      - Username : root
      - Password : ....
  - Créer la base de données :
    - Lancez le script présent dans le fichier "[script.sql](../ressources/sql/script.sql)"
4. **Lancer le backend** :
   mvn spring-boot:run
5. **Lancer le frontend** :
   ng serve

---

## **Documentation API**

La documentation des APIs est accessible via Swagger : http://localhost:3001/swagger-ui/index.html

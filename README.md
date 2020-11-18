# PLD-Agile
Projet longue durée d'optimisation de livraison en java.

## Téléchargement 
Sous éclipse :
File -> Import -> Git -> Projects from git (Smart import) -> clone URI -> ...   
Une fois le projet importé, faire un clic droit dans le fichier pom.xml -> Maven -> Update project .
Vous devriez voir apparaître les dépendances dans un dossier maven dependancies.

## Pour ne plus avoir à entrer vos identifiants GitHub

### Créer une clé SSH
Exécuter `ssh-keygen` dans un terminal et valider les valeurs par défaut (appuyer sur entrer plusieurs fois).
Cela va créer deux fichiers `id_rsa` (clé privée) et `id_rsa.pub` (clé publique) dans votre home (`/home/votreNom/.ssh/` sous Linux, `C:\Users\votreNom\.ssh` sous Windows)

### Ajouter la clé SSH à votre compte GitHub
Sur https://github.com/ dans **settings** > **SSH and GPG keys**, ajoutez cette nouvelle clé SSH en lui donnant un titre et en y copiant le contenu de votre clé publique (`id_rsa.pub`)

### Mettre à jour votre remote pour ce projet
Exécuter `git remote origin set-url git@github.com:jeromehue/PLD-Agile.git` dans le projet Agile (pour le mettre à jour et utiliser SSH).

Vous devriez maintenant pouvoir utiliser Git sans avoir à entrer vos identifiants, et à l'avenir, utilisez le lien SSH plutôt que HTTPS pour cloner un projet sur GitHub.

Gestion de Bibliothèque – Java
DESCRIPTION

Projet Java console pour gérer des livres avec SQLite.
Fonctionnalités : ajouter, supprimer, afficher, compter.

PREREQUIS


Java JDK

SQLite3

JDBC driver SQLite (sqlite-jdbc-3.51.1.0.jar dans lib/)


INITIALISER LA BASE

cd sql

sqlite3 livres.db

.read init.sql

.exit

COMPILATION

javac -cp ".;lib/sqlite-jdbc-3.51.1.0.jar" Main.java

EXECUTION

java -cp ".;lib/sqlite-jdbc-3.51.1.0.jar" Main

UTILISATION

Menu interactif dans le terminal :


1 - Ajouter un livre

2 - Supprimer un livre

3 - Afficher tous les livres

4 - Nombre total de livres

5 - Quitter

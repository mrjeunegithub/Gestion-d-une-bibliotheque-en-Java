-- Suppression de la table si elle existe déjà
DROP TABLE IF EXISTS livres;

-- Création de la table
CREATE TABLE livres (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titre TEXT NOT NULL,
    auteur TEXT NOT NULL,
    annee INTEGER NOT NULL,
    genre TEXT DEFAULT 'non spécifié'
);

-- Insertion de 20 livres variés
INSERT INTO livres (titre, auteur, annee, genre) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', 1943, 'Conte'),
('L''Étranger', 'Albert Camus', 1942, 'Roman philosophique'),
('1984', 'George Orwell', 1949, 'Science-fiction'),
('Les Misérables', 'Victor Hugo', 1862, 'Roman historique'),
('Harry Potter à l''école des sorciers', 'J.K. Rowling', 1997, 'Fantasy'),
('Le Seigneur des Anneaux', 'J.R.R. Tolkien', 1954, 'Fantasy'),
('Voyage au centre de la Terre', 'Jules Verne', 1864, 'Science-fiction'),
('Germinal', 'Émile Zola', 1885, 'Roman social'),
('L''Assommoir', 'Émile Zola', 1877, 'Roman naturaliste'),
('Madame Bovary', 'Gustave Flaubert', 1857, 'Roman réaliste'),
('Le Rouge et le Noir', 'Stendhal', 1830, 'Roman psychologique'),
('Les Fleurs du mal', 'Charles Baudelaire', 1857, 'Poésie'),
('À la recherche du temps perdu', 'Marcel Proust', 1913, 'Roman'),
('Le Comte de Monte-Cristo', 'Alexandre Dumas', 1844, 'Roman d''aventure'),
('Les Trois Mousquetaires', 'Alexandre Dumas', 1844, 'Roman d''aventure'),
('Candide', 'Voltaire', 1759, 'Conte philosophique'),
('Le Père Goriot', 'Honoré de Balzac', 1835, 'Roman réaliste'),
('Notre-Dame de Paris', 'Victor Hugo', 1831, 'Roman historique'),
('La Peste', 'Albert Camus', 1947, 'Roman philosophique'),
('Bel-Ami', 'Guy de Maupassant', 1885, 'Roman réaliste'),
('Le Horla', 'Guy de Maupassant', 1887, 'Nouvelle fantastique'),
('Vingt mille lieues sous les mers', 'Jules Verne', 1870, 'Science-fiction'),
('L''Île mystérieuse', 'Jules Verne', 1875, 'Roman d''aventure');

-- Vérification de l'insertion
SELECT COUNT(*) as 'Nombre de livres' FROM livres;
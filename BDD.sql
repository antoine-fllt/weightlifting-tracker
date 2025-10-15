-- Création de la base de données
CREATE DATABASE IF NOT EXISTS lifttrack_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE lifttrack_app;

-- Table des utilisateurs
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table des mouvements d'haltérophilie
CREATE TABLE exercises (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    category VARCHAR(50), -- Ex: olympique, powerlifting, accessoire
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table des records/scores
CREATE TABLE records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    weight DECIMAL(6,2) NOT NULL, -- Poids en kg
    reps INT DEFAULT 1, -- Nombre de répétitions
    date_achieved DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE,
    INDEX idx_user_exercise (user_id, exercise_id),
    INDEX idx_date (date_achieved)
);

-- Insertion des mouvements pré-établis
INSERT INTO exercises (name, description) VALUES
('Squat', 'Flexion complète des jambes avec barre'),
('Bench Press', 'Développé couché'),
('Deadlift', 'Soulevé de terre'),
('Clean & Jerk', 'Épaulé-jeté'),
('Snatch', 'Arraché'),
('Overhead Press', 'Développé militaire'),
('Front Squat', 'Squat avant'),
('Power Clean', 'Épaulé en puissance'),
('Romanian Deadlift', 'Soulevé de terre roumain'),
('Pull-ups', 'Tractions');

-- Données de test (optionnel)
INSERT INTO users (username, email, password) VALUES
('demo_user', 'demo@example.com', '$2a$10$XYZ...');

INSERT INTO records (user_id, exercise_id, weight, reps, date_achieved, notes) VALUES
(1, 1, 120.5, 1, '2024-01-15', 'Nouveau PR!'),
(1, 1, 125.0, 1, '2024-03-20', 'Progression régulière'),
(1, 2, 90.0, 2, '2024-02-10', 'Premier essai'),
(1, 3, 150.0, 1, '2024-01-25', 'Bonne forme');
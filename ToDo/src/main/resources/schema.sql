CREATE TABLE IF NOT EXISTS category (
    category_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS task (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    due DATETIME,
    priority ENUM('HIGH', 'MEDIUM', 'LOW', 'DEFAULT') DEFAULT 'DEFAULT',
    completed BOOLEAN DEFAULT false,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE ON UPDATE CASCADE
);
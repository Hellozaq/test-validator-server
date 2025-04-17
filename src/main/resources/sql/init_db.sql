CREATE DATABASE IF NOT EXISTS fxedu_dev
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

-- Create a dedicated user
CREATE USER IF NOT EXISTS 'fxedu_dev_user'@'localhost' IDENTIFIED BY 'F*angXiaDe*v';

-- Grant full access on fxedu_dev
GRANT ALL PRIVILEGES ON fxedu_dev.* TO 'fxedu_dev_user'@'localhost';

-- Apply privilege changes
FLUSH PRIVILEGES;

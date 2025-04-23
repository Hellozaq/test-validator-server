USE fxedu_dev;

CREATE TABLE fx_users (
    id CHAR(36) PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    display_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_type INT NOT NULL,

    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE fx_email_verification (
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    verification_code CHAR(6) NOT NULL,
    expired_time DATETIME NOT NULL,

    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE fx_courses (
    id CHAR(36) PRIMARY KEY,
    course_owner CHAR(36) NOT NULL,
    course_name VARCHAR(255) NOT NULL,
    course_description VARCHAR(1023) NOT NULL,
    is_open BOOLEAN NOT NULL,
    is_active BOOLEAN NOT NULL,
    course_join_code CHAR(8) NOT NULL,

    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (course_owner) REFERENCES fx_users(id) ON DELETE CASCADE
);

CREATE TABLE fx_course_users (
    user_id CHAR(36) KEY,
    course_id CHAR(36) KEY,

    created_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (user_id, course_id)
);

CREATE TABLE IF NOT EXISTS USER.user_role
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER.user (id),
    FOREIGN KEY (role_id) REFERENCES USER.role (id)
)
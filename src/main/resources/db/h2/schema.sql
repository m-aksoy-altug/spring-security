DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS userRoles;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS persistent_logins;

CREATE TABLE users(
	username varchar(50) PRIMARY KEY NOT NULL,
	password varchar(100) DEFAULT  NULL,
	city varchar(100) DEFAULT  NULL,
	phone varchar(100) DEFAULT  NULL,
	enabled tinyint(4) NOT NULL DEFAULT '1'
);

CREATE TABLE user_roles(
	user_role_id int(11) PRIMARY KEY AUTO_INCREMENT NOT NULL,
	username varchar(50)  NOT NULL,
	role varchar(50) NOT NULL,
	FOREIGN KEY (username) REFERENCES user(username)
);

CREATE TABLE persistent_logins(
	series varchar(64) PRIMARY KEY NOT NULL,
	username varchar(50)  NOT NULL,
	token varchar(54) NOT NULL,
	last_used timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE VIDEOS
(
  videoId VARCHAR(50) NOT NULL,
  title VARCHAR(50) NOT NULL,
  year INTEGER NOT NULL,
  duration INTEGER NOT NULL
);

CREATE TABLE USERS
(
  userName VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  firstName VARCHAR(30) NOT NULL,
  lastName VARCHAR(50) NOT NULL
);

INSERT INTO USERS (userName, password, firstName, lastName) VALUES ('admin', 'admin', 'Administrator', '');
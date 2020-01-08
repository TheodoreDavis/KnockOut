CREATE TABLE IF NOT EXISTS Users (
  ID VARCHAR(30),
  Username VARCHAR(30),
  Authenticator VARCHAR(255),
  IsAdmin VARCHAR(80),
  INDEX(ID)
) engine=InnoDB;
CREATE TABLE `user` 
(
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`login` VARCHAR(50) NOT NULL,

`password` VARCHAR(50) NOT NULL,

`email` VARCHAR(25) NOT NULL,

`avatar` VARCHAR(45) NULL DEFAULT NULL,

PRIMARY KEY(`ID`)
)
ENGINE=innoDB;

CREATE TABLE `image` 
(
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`image` VARCHAR(40) NOT NULL,

`title` VARCHAR(45) NOT NULL,

`rating` INT(10) NOT NULL,

`add_date` DATETIME NOT NULL,

`main_date` DATETIME NULL DEFAULT NULL,

`user` INT(10) NULL DEFAULT NULL,

PRIMARY KEY(`ID`),

INDEX `user_image` (`user`),

CONSTRAINT `user_image` FOREIGN KEY(`user`)
REFERENCES `user` (`ID`)
)
ENGINE=innoDB;

CREATE TABLE `comment` 
( 
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`user` INT(10) NOT NULL,

`image` INT(10) NOT NULL,

`content` VARCHAR(500),

PRIMARY KEY(ID),

INDEX `user_sign` (`user`),

INDEX `image_sign` (`image`),

CONSTRAINT `user_sign` FOREIGN KEY(`user`)
REFERENCES `user` (`ID`),

CONSTRAINT `image_sign` FOREIGN KEY(`image`)
REFERENCES `image` (`ID`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=innoDB;


CREATE TABLE `rating` 
( 
`ID` INT(11) NOT NULL AUTO_INCREMENT,

`user` INT(10) NOT NULL,

`image` INT(10) NOT NULL,

PRIMARY KEY(ID),

INDEX `user_sign` (`user`),

INDEX `image_sign` (`image`),

CONSTRAINT `user_sign` FOREIGN KEY(`user`)
REFERENCES `user` (`ID`),

CONSTRAINT `image_sign` FOREIGN KEY(`image`)
REFERENCES `image` (`ID`) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=innoDB;

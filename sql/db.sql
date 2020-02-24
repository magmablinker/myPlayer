DROP DATABASE IF EXISTS myPlayer;
CREATE DATABASE myPlayer;
USE myPlayer;

CREATE TABLE directories (
	id INT NOT NULL AUTO_INCREMENT,
	path VARCHAR(300) NOT NULL,
	deleted TINYINT(1) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE playlist (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
	deleted TINYINT(1) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE song (
	id INT NOT NULL AUTO_INCREMENT,
	path VARCHAR(300) NOT NULL,
	deleted TINYINT(1) NOT NULL,
	PRIMARY KEY(id)
);

CREATE TABLE playlistSong (
	id INT NOT NULL AUTO_INCREMENT,
	pid INT NOT NULL,
	sid INT NOT NULL,
	deleted TINYINT(1) NOT NULL,
	PRIMARY KEY(id),
	FOREIGN KEY(pid) REFERENCES playlist(id)
	ON DELETE RESTRICT ON UPDATE CASCADE,
	FOREIGN KEY(sid) REFERENCES song(id)
	ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE equalizerPreset (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(32) NOT NULL,
	preset VARCHAR(400) NOT NULL,
	deleted TINYINT(1) NOT NULL,
	PRIMARY KEY(id)
);

ALTER TABLE directories ALTER deleted SET DEFAULT 0;
ALTER TABLE playlist ALTER deleted SET DEFAULT 0;
ALTER TABLE song ALTER deleted SET DEFAULT 0;
ALTER TABLE playlistSong ALTER deleted SET DEFAULT 0;
ALTER TABLE equalizerPreset ALTER deleted SET DEFAULT 0;

-- The default preset
INSERT INTO equalizerPreset(name, preset) VALUES("Default", "2.335135135135129;2.5297297297297305;0.9729729729729755;0.5837837837837796;-2.335135135135136;-3.11351351351351;-1.9459459459459474;-0.38918918918918877;-0.19459459459459438;-2.1405405405405418;");

CREATE USER 'music'@'localhost' IDENTIFIED BY '1337';
GRANT SELECT, UPDATE, INSERT ON myPlayer.* TO 'music'@'localhost';
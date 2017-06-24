create database logger_db;

create table Log (
	id BIGINT NOT NULL,
	phoneNumber VARCHAR(35) NOT NULL,
	brandModel VARCHAR(100) NOT NULL,
	version VARCHAR(100) NOT NULL,
	imei VARCHAR(17) NOT NULL,
	imsi VARCHAR(15) NOT NULL,
	extPhoneNumber VARCHAR(35) NOT NULL,
	dateTime DATETIME NOT NULL,
	duration INT,
	smsContent TEXT,
	direction ENUM('INCOMING', 'OUTGOING') NOT NULL,
	latitude FLOAT(7,4),
	longitude FLOAT(7,4),
	radius FLOAT(15,4),
	cellId INT NOT NULL,
	lac INT NOT NULL,
	rat VARCHAR(20) NOT NULL,
	mnc INT,
	mcc INT,
	rssi INT NOT NULL,
	lteRSRP VARCHAR(10),
	lteRSRQ VARCHAR(10),
	lteRSSNR VARCHAR(10),
	lteCQI VARCHAR(10),
	logType ENUM('CALL', 'SMS') NOT NULL,
	PRIMARY KEY (id)
);

create table Users (
	userName VARCHAR(50) NOT NULL,
	password VARCHAR(41) NOT NULL,
	PRIMARY KEY (userName)
);

ALTER DATABASE logger_db CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
ALTER TABLE Log CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
ALTER TABLE Log CHANGE smsContent smsContent TEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
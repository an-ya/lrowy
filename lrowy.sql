CREATE TABLE `bookmark` (
  `bookmark_id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `desc` varchar(128),
  `title` varchar(128),
  `faviconUrl` varchar(256),
  `faviconBlurUrl` varchar(256),
  `faviconOriginalUrl` varchar(256),
  `keyword` varchar(32),
  `state` varchar(32),
  `url` varchar(256),
  `baseUrl` varchar(256),
  `visits` int(10) NOT NULL,
  `delFlag` tinyint(1) NOT NULL,
  `hiddenFlag` tinyint(1) NOT NULL,
  `accessible` tinyint(1) NOT NULL,
  `faviconFlag` tinyint(1) NOT NULL,
  `createDate` datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
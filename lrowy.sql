CREATE TABLE bookmark (
  bookmarkId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  bookmarkCategoryId int(10) NOT NULL,
  description varchar(128),
  title varchar(128),
  faviconUrl varchar(256),
  faviconBlurUrl varchar(256),
  faviconOriginalUrl varchar(256),
  state varchar(32),
  url varchar(256),
  baseUrl varchar(256),
  visits int(10) NOT NULL,
  delFlag tinyint(1) NOT NULL,
  hiddenFlag tinyint(1) NOT NULL,
  isAccessible tinyint(1) NOT NULL,
  shortcutFlag tinyint(1) NOT NULL,
  createDate datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE bookmark_category (
  bookmarkCategoryId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name varchar(128)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
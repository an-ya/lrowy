CREATE TABLE bookmark (
  bookmarkId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  bookmarkCategoryId int(10) NOT NULL,
  description varchar(128),
  title varchar(128),
  state varchar(32),
  url varchar(256),
  baseUrl varchar(256),
  visits int(10) NOT NULL,
  delFlag tinyint(1) NOT NULL,
  hiddenFlag tinyint(1) NOT NULL,
  isAccessible tinyint(1) NOT NULL,
  shortcutFlag tinyint(1) NOT NULL,
  extraNetFlag tinyint(1) NOT NULL,
  createDate datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE bookmark_category (
  bookmarkCategoryId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name varchar(128) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE favicon (
  faviconId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  domain varchar(256) NOT NULL,
  topDomain varchar(256) NOT NULL,
  faviconUrl varchar(256),
  faviconBlurUrl varchar(256),
  faviconOriginalUrl varchar(256)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE bookmark_favicon (
  bookmarkId int(10) NOT NULL,
  faviconId int(10) NOT NULL,
  PRIMARY KEY (bookmarkId,faviconId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE article (
  articleId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  creatorId int(10) NOT NULL,
  articleCategoryId int(10),
  title varchar(128),
  content text,
  description varchar(128),
  template varchar(128),
  visits int(10) NOT NULL,
  delFlag tinyint(1) NOT NULL,
  hiddenFlag tinyint(1) NOT NULL,
  createDate datetime NOT NULL,
  updateDate datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE article_category (
  articleCategoryId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name varchar(128) NOT NULL,
  imgUrl varchar(256)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE article_tag (
  articleTagId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name varchar(128) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
CREATE TABLE article_tag_r (
  articleId int(10) NOT NULL,
  articleTagId int(10) NOT NULL,
  PRIMARY KEY (articleId,articleTagId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE user (
  userId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  type varchar(32) NOT NULL,
  name varchar(32),
  email varchar(128),
  website varchar(128),
  password varchar(128),
  description varchar(128),
  avatarVersion int(5),
  origin varchar(32) NOT NULL,
  originId varchar(64)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4;
CREATE TABLE comment (
  commentId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  issueId int(10) NOT NULL,
  issueType varchar(32) NOT NULL,
  parentId int(10),
  userId int(10) NOT NULL,
  createDate datetime NOT NULL,
  content text
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4;
CREATE TABLE captcha (
  captchaId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  sendId int(10) NOT NULL,
  sendMode varchar(32) NOT NULL,
  sendTo varchar(32) NOT NULL,
  ip varchar(32) NOT NULL,
  code varchar(64) NOT NULL,
  createDate datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4;
CREATE TABLE email (
  emailId int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  content text NOT NULL,
  subject varchar(64) NOT NULL,
  sendType varchar(16) NOT NULL,
  sendFrom varchar(32) NOT NULL,
  sendTo varchar(32) NOT NULL,
  sendDate datetime NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8mb4;
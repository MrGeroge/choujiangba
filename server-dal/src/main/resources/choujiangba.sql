DROP TABLE `caiqi`.`tb_account`,
           `caiqi`.`tb_account_profile`,
           `caiqi`.`tb_account_register_record`,
           `caiqi`.`tb_activity`,
           `caiqi`.`tb_activity_complete`,
           `caiqi`.`tb_item_hot`,
           `caiqi`.`tb_activity_join_record`,
           `caiqi`.`tb_activity_result`,
           `caiqi`.`tb_activity_underway`,
           `caiqi`.`tb_admin_account`,
           `caiqi`.`tb_auth_record`,
           `caiqi`.`tb_delivery_address`,
           `caiqi`.`tb_feedback`,
           `caiqi`.`tb_item`,
           `caiqi`.`tb_item_base_statistics`,
           `caiqi`.`tb_item_purchase_statistics`,
           `caiqi`.`tb_item_reply`,
           `caiqi`.`tb_item_view_statistics`,
           `caiqi`.`tb_transactions`;

CREATE TABLE `tb_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_hsqa0fp3f7rv9yj885sixijg8` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_account_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar_url` varchar(255) DEFAULT NULL,
  `balance` double NOT NULL,
  `gender` int(11) NOT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_account_register_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `ip` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `desc_img_urls` longtext NOT NULL,
  `detail` longtext NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `property` varchar(255) NOT NULL,
  `thumbnail_url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `reward_interval` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `winner_id` bigint(20) DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activity_complete` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `finish_time` datetime NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`),
  FOREIGN KEY (`activity_id`) REFERENCES `tb_activity`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activity_join_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `city` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `join_time` datetime NOT NULL,
  `price` double NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`activity_id`) REFERENCES `tb_activity`(`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activity_result` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `reward_time` datetime NOT NULL,
  `winner_id` bigint(20) DEFAULT 0,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`activity_id`) REFERENCES `tb_activity`(`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_activity_underway` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `price` double NOT NULL,
  `real_price` double NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`activity_id`) REFERENCES `tb_activity`(`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_admin_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `ip` varchar(255) NOT NULL,
  `last_login_time` datetime NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ev0vlnehmg65undrwp3umrbbb` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_auth_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `auth_time` datetime NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `device_id` varchar(255) NOT NULL,
  `district` varchar(255) DEFAULT NULL,
  `expire_in` datetime NOT NULL,
  `ip` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_delivery_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `location` varchar(255) NOT NULL,
  `address_name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  `zip_code` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contact` varchar(255) NOT NULL,
  `content` longtext NOT NULL,
  `img_urls` longtext,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tb_item_base_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) NOT NULL,
  `published_activity_num` bigint(20) NOT NULL,
  `reply_num` bigint(20) NOT NULL,
  `view_num` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_item_hot` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `banner_url` varchar(255) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_item_purchase_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `buy_time` datetime NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `pay` double NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_item_reply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_id` bigint(20) NOT NULL,
  `create_at` datetime NOT NULL,
  `img_urls` longtext NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `text_content` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`activity_id`) REFERENCES `tb_activity`(`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_item_view_statistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `ip` varchar(255) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `latitiude` double NOT NULL,
  `longitude` double NOT NULL,
  `uid` bigint(20) NOT NULL,
  `view_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`item_id`) REFERENCES `tb_item`(`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_transactions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coin_num` double NOT NULL,
  `create_at` datetime NOT NULL,
  `description` varchar(255) NOT NULL,
  `uid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uid`) REFERENCES `tb_account`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

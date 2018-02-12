INSERT INTO tb_account (username,password) VALUES ('18629591217',MD5('123456'));
INSERT INTO tb_account (username,password) VALUES ('18689589078',MD5('123456'));
INSERT INTO tb_account (username,password) VALUES ('18202740055',MD5('123456'));
INSERT INTO tb_account (username,password) VALUES ('18202790149',MD5('123456'));
INSERT INTO tb_account (username,password) VALUES ('13797007047',MD5('123456'));

INSERT INTO tb_account_profile (uid,avatar_url,gender,nickname,balance) VALUES
(1,"",0,"zhangyu",0.0);

INSERT INTO tb_account_profile (uid,avatar_url,gender,nickname,balance) VALUES
(2,"",0,"wukunqiong",0.0);

INSERT INTO tb_account_profile (uid,avatar_url,gender,nickname,balance) VALUES
(3,"",0,"chenghao",0.0);

INSERT INTO tb_account_profile (uid,avatar_url,gender,nickname,balance) VALUES
(4,"",0,"caiqi",0.0);

INSERT INTO tb_account_profile (uid,avatar_url,gender,nickname,balance) VALUES
(5,"",0,"fangzheng",0.0);

INSERT INTO tb_account_register_record (uid,create_at,ip) VALUES
(1,'2015-10-27 21:16:17','192.168.0.105');

INSERT INTO tb_account_register_record (uid,create_at,ip) VALUES
(2,'2015-10-27 20:00:17','192.168.0.105');

INSERT INTO tb_account_register_record (uid,create_at,ip) VALUES
(3,'2015-10-20 21:16:17','192.168.0.105');

INSERT INTO tb_account_register_record (uid,create_at,ip) VALUES
(4,'2015-10-11 21:16:17','192.168.0.105');

INSERT INTO tb_account_register_record (uid,create_at,ip) VALUES
(5,'2015-10-12 21:16:00','192.168.0.105');

UPDATE tb_account_profile SET balance = balance + 5 WHERE uid=5;

INSERT INTO tb_transactions (uid,coin_num,create_at,description) VALUES
(5,5,'2015-10-20 21:16:17','用于测试');

UPDATE tb_account_profile SET balance = balance + 5 WHERE uid=5;

INSERT INTO tb_transactions (uid,coin_num,create_at,description) VALUES
(5,5,'2015-10-20 21:16:17','用于测试');

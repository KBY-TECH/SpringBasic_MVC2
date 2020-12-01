INSERT INTO USER(ID,USERNAME,EMAIL,PW,PW2) VALUES (1,'user','soccer_by@naver.com','user','user');
INSERT INTO USER(ID,USERNAME,EMAIL,PW,PW2) VALUES (2,'admin','admin@naver.com','12','12');

INSERT INTO BOARD (ID,username_id,title,contents,create_date) VALUES(1,1,'test code sql injection','content Test',current_timestamp());
INSERT INTO BOARD (ID,username_id,title,contents,create_date) VALUES(2,2,'test code sql injection2','content Test2',current_timestamp());
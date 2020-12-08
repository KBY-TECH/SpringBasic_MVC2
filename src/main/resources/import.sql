INSERT INTO USER(ID,USERNAME,EMAIL,PW,PW2,CREATE_DATE) VALUES (1,'user','soccer_by@naver.com','user','user',current_timestamp());
INSERT INTO USER(ID,USERNAME,EMAIL,PW,PW2,CREATE_DATE) VALUES (2,'admin','admin@naver.com','12','12',current_timestamp());

INSERT INTO BOARD (ID,username_id,title,contents,CREATE_DATE,COUNTER_OFANSWER ) VALUES(1,1,'test code sql injection','content Test',current_timestamp(),0);
INSERT INTO BOARD (ID,username_id,title,contents,CREATE_DATE,COUNTER_OFANSWER ) VALUES(2,2,'test code sql injection2','content Test2',current_timestamp(),0);
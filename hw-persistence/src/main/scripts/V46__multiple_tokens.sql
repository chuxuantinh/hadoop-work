ALTER TABLE SESSION 
DROP PRIMARY KEY; 

ALTER TABLE SESSION
ADD PRIMARY KEY (TOKEN, USER_NAME)
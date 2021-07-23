CREATE TABLE USER (
  ID        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  TYPE      VARCHAR(256) NOT NULL,
  NAME      VARCHAR(256) NOT NULL ,
  PASSWORD  VARCHAR(256),
  KEY_ID    INT,
  HOME_PATH VARCHAR(256),
  CONSTRAINT USER_KEY_FK FOREIGN KEY (KEY_ID) REFERENCES KEY_FILE (ID)
);

CREATE UNIQUE INDEX USER_NAME_TYPE_UK ON USER (NAME, TYPE);


ALTER TABLE USER_SETTINGS
  ADD COLUMN HDFS_USER_ID INT,
  ADD FOREIGN KEY USER_SETTINGS_HDFS_USER_FK (HDFS_USER_ID) REFERENCES USER (ID),
  ADD COLUMN OOZIE_SERVICE_USER_ID INT,
  ADD FOREIGN KEY USER_SETTINGS__OOZIE_SERVICE__USER__FK (OOZIE_SERVICE_USER_ID) REFERENCES USER (ID);


ALTER TABLE HDFS
  ADD COLUMN USER_ID INT,
  ADD FOREIGN KEY HDFS_USER_FK (USER_ID) REFERENCES USER (ID);


ALTER TABLE OOZIE_ACCESS
  ADD COLUMN USER_ID INT,
  ADD FOREIGN KEY OOZIE_ACCESS_USER_FK (USER_ID) REFERENCES USER (ID);


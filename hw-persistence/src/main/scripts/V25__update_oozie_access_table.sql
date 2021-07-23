DROP TABLE OOZIE_ACCESS;

CREATE TABLE OOZIE_ACCESS (
  PLATFORM_ID INT(11) NOT NULL,
  CLUSTER_ID VARCHAR(256) NOT NULL,
  HOST VARCHAR(256) NOT NULL,
  PORT INT(11) NOT NULL,
  CONTEXT VARCHAR(256) NOT NULL DEFAULT '/oozie',
  KERBERIZED TINYINT(1) NOT NULL DEFAULT '0',
  IMPERSONATED TINYINT(1) NOT NULL DEFAULT '0',
  USER_ID int(11) DEFAULT NULL,
  CONSTRAINT OOZIE_ACCESS_PK PRIMARY KEY (PLATFORM_ID, CLUSTER_ID),
  CONSTRAINT OOZIE_CLUSTER_FK FOREIGN KEY (PLATFORM_ID, CLUSTER_ID) REFERENCES CLUSTER (PLATFORM_ID, CLUSTER_ID) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT OOZIE_USER_FK FOREIGN KEY (USER_ID) REFERENCES USER (ID) ON DELETE SET NULL
)

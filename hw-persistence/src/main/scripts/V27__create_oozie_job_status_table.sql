CREATE TABLE CLUSTER_OOZIE_JOB_INDEXING (
  PLATFORM_ID INT(11)      NOT NULL,
  CLUSTER_ID  VARCHAR(512) NOT NULL,
  STATUS  VARCHAR(16),
  COMPLETED DATETIME,
  STARTED DATETIME,
  PRIMARY KEY (PLATFORM_ID, CLUSTER_ID),
  CONSTRAINT CLUSTER_OOZIE_JOB_INDEXING_CLUSTER_FK FOREIGN KEY (PLATFORM_ID, CLUSTER_ID) REFERENCES CLUSTER (PLATFORM_ID, CLUSTER_ID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

ALTER TABLE CLUSTER
  DROP COLUMN HDFS_INDEXING;
ALTER TABLE CLUSTER
  DROP COLUMN OOZIE_JOB_INDEXING;
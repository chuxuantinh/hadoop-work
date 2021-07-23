DROP TABLE RESOURCE_MANAGER_ACCESS;
DROP TABLE ZOOKEEPER_ACCESS;
DROP TABLE JOB_HISTORY_ACCESS;
DROP TABLE JOB_TRACKER_ACCESS;

ALTER TABLE HDFS_ACCESS DROP COLUMN NAMENODE_HOST;
ALTER TABLE HDFS_ACCESS DROP COLUMN NAMENODE_PORT;

ALTER TABLE OOZIE_ACCESS DROP COLUMN HOST;
ALTER TABLE OOZIE_ACCESS DROP COLUMN PORT;
ALTER TABLE OOZIE_ACCESS DROP COLUMN CONTEXT;
ALTER TABLE OOZIE_ACCESS DROP COLUMN IMPERSONATED;
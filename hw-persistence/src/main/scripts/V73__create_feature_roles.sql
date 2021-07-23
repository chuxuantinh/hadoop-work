CREATE TABLE FEATURE_ROLES (
  FEATURE varchar(512) not null,
  ROLE varchar(512) not null,
  primary key (FEATURE, ROLE)
);

INSERT INTO FEATURE_ROLES (FEATURE, ROLE) VALUES ('CLUSTER_SETTINGS_READ', 'admin');
INSERT INTO FEATURE_ROLES (FEATURE, ROLE) VALUES ('CLUSTER_SETTINGS_WRITE', 'admin');
INSERT INTO FEATURE_ROLES (FEATURE, ROLE) VALUES ('CLUSTER_SETTINGS_READ', 'developer');
INSERT INTO FEATURE_ROLES (FEATURE, ROLE) VALUES ('CLUSTER_SETTINGS_READ', 'qa');

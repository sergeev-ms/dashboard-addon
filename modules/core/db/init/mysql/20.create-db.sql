-- begin DASHBOARD_WIDGET_TEMPLATE
alter table DASHBOARD_WIDGET_TEMPLATE add constraint FK_DASHBOARD_WIDGET_TEMPLATE_GROUP foreign key (GROUP_ID) references DASHBOARD_TEMPLATE_GROUP(ID)^
create index IDX_DASHBOARD_WIDGET_TEMPLATE_GROUP on DASHBOARD_WIDGET_TEMPLATE (GROUP_ID)^
-- end DASHBOARD_WIDGET_TEMPLATE

-- begin DASHBOARD_DASHBOARD_GROUP
create unique index IDX_DASHBOARD_DASHBOARD_GROUP_UNIQ_NAME on DASHBOARD_DASHBOARD_GROUP (NAME) ^
-- end DASHBOARD_DASHBOARD_GROUP
-- begin DASHBOARD_PERSISTENT_DASHBOARD
alter table DASHBOARD_PERSISTENT_DASHBOARD add constraint FK_DASHBOARD_PERSISTENT_DASHBOARD_GROUP foreign key (GROUP_ID) references DASHBOARD_DASHBOARD_GROUP(ID)^
create unique index IDX_DASHBOARD_PERSISTENT_DASHBOARD_UNIQ_REFERENCE_NAME on DASHBOARD_PERSISTENT_DASHBOARD (REFERENCE_NAME) ^
create index IDX_DASHBOARD_PERSISTENT_DASHBOARD_GROUP on DASHBOARD_PERSISTENT_DASHBOARD (GROUP_ID)^
-- end DASHBOARD_PERSISTENT_DASHBOARD
-- begin DASHBOARD_TEMPLATE_GROUP
create unique index IDX_DASHBOARD_TEMPLATE_GROUP_UNIQ_NAME on DASHBOARD_TEMPLATE_GROUP (NAME) ^
-- end DASHBOARD_TEMPLATE_GROUP

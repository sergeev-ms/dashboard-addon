alter table AMXD_WIDGET_PARAMETER add constraint FK_AMXD_WIDGET_PARAMETER_DASHBOARD_WIDGET foreign key (DASHBOARD_WIDGET_ID) references AMXD_DASHBOARD_WIDGET(ID);
create index IDX_AMXD_WIDGET_PARAMETER_DASHBOARD_WIDGET on AMXD_WIDGET_PARAMETER (DASHBOARD_WIDGET_ID);
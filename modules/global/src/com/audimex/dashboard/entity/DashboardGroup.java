/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */
package com.audimex.dashboard.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "AMXD_DASHBOARD_GROUP")
@Entity(name = "amxd$DashboardGroup")
public class DashboardGroup extends StandardEntity {
    private static final long serialVersionUID = 7160480278020869794L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @OneToMany(mappedBy = "dashboardGroup")
    protected List<PersistentDashboard> dashboards;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDashboards(List<PersistentDashboard> dashboards) {
        this.dashboards = dashboards;
    }

    public List<PersistentDashboard> getDashboards() {
        return dashboards;
    }
}
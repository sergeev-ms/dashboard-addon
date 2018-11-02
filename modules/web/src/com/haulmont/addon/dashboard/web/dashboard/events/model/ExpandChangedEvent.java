/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.web.dashboard.events.model;

import com.haulmont.addon.dashboard.model.visualmodel.DashboardLayout;

public class ExpandChangedEvent extends AbstractWidgetModelEvent {
    public ExpandChangedEvent(DashboardLayout source) {
        super(source);
    }
}

/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.web.dashboard.events;

import com.haulmont.addon.dashboard.web.dashboard.layouts.CanvasWidgetLayout;
import com.haulmont.addon.dashboard.web.events.DashboardEvent;

public class OpenWidgetEditorEvent extends DashboardEvent {
    public OpenWidgetEditorEvent(CanvasWidgetLayout source) {
        super(source);
    }

    @Override
    public CanvasWidgetLayout getSource() {
        return (CanvasWidgetLayout) super.getSource();
    }
}
/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.audimex.dashboard.web.dashboard.events;

import com.audimex.dashboard.web.dashboard.layouts.CanvasLayout;
import com.audimex.dashboard.web.events.DashboardEvent;

public class LayoutRemoveEvent extends DashboardEvent {

    public LayoutRemoveEvent(CanvasLayout source) {
        super(source);
    }

    @Override
    public CanvasLayout getSource() {
        return (CanvasLayout) super.getSource();
    }
}

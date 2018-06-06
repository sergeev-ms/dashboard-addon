/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.audimex.dashboard.web.dashboard.events;

import com.audimex.dashboard.web.dashboard.layouts.CanvasLayout;
import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class WeightChangedEvent extends ApplicationEvent implements UiEvent {
    public WeightChangedEvent(CanvasLayout source) {
        super(source);
    }

    @Override
    public CanvasLayout getSource() {
        return (CanvasLayout) super.getSource();
    }
}
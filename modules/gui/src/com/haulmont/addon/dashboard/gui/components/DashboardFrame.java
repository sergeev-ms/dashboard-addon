/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.gui.components;

import com.haulmont.addon.dashboard.model.Dashboard;
import com.haulmont.addon.dashboard.model.Parameter;
import com.haulmont.cuba.gui.components.Frame;

import java.util.List;

/**
 * Represents in a frame  a dashboard {@link Dashboard}
 * Dashboard can be configured by {@link Dashboard#referenceName} or by the classpath of dashboard.json
 * It is possible to add (or rewrite) primitive parameters in the dashboard.
 */
public interface DashboardFrame extends Frame {

    String NAME = "amdxDashboardComponent";

    WidgetBrowse getWidgetBrowse(String widgetId);

    void setReferenceName(String referenceName);

    void setJsonPath(String jsonPath);

    void setXmlParameters(List<Parameter> parameters);

    void setTimerDelay(int delay);

    int getTimerDelay();

    Dashboard getDashboard();

    void refresh();

}
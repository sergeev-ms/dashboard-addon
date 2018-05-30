/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.audimex.dashboard.web.widget_types;

import com.audimex.dashboard.model.Dashboard;
import com.audimex.dashboard.model.Parameter;
import com.audimex.dashboard.model.Widget;
import com.haulmont.cuba.gui.components.AbstractFrame;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AbstractWidgetBrowse extends AbstractFrame {
    public static final String WIDGET = "WIDGET";
    public static final String DASHBOARD = "DASHBOARD";

    protected Widget widget;
    protected Dashboard dashboard;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        widget = (Widget) params.get(WIDGET);
        dashboard = (Dashboard) params.get(DASHBOARD);

        if (widget == null) {
            throw new RuntimeException("Can't get a Widget object in input parameters");
        }
        if (dashboard == null) {
            throw new RuntimeException("Can't get a Dashboard object in input parameters");
        }
    }

    public Map<String, Object> getParamsForFrame() {
        return Stream.concat(dashboard.getParameters().stream(), widget.getParameters().stream())
                .collect(Collectors.toMap(
                        Parameter::getName,
                        parameter -> parameter.getParameterValue().getValue(),
                        (p1, p2) -> p2
                ));
    }

    public Widget getWidget() {
        return widget;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}

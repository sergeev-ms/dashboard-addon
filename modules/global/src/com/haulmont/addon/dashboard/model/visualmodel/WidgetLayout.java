/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.model.visualmodel;

import com.haulmont.addon.dashboard.model.Widget;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import org.apache.commons.lang.StringUtils;

@MetaClass(name = "dashboard$WidgetLayout")
public class WidgetLayout extends DashboardLayout {

    @MetaProperty
    protected Widget widget;

    public Widget getWidget() {
        return widget;
    }

    public void setWidget(Widget widget) {
        this.widget = widget;
    }

    @Override
    public String getCaption() {
        if (widget != null) {
            return widget.getCaption();
        }
        return StringUtils.EMPTY;
    }
}

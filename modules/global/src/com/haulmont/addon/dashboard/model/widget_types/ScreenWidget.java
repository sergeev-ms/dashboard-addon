/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.model.widget_types;

import com.haulmont.addon.dashboard.annotation.WidgetType;
import com.haulmont.addon.dashboard.model.Widget;
import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;

import static com.haulmont.addon.dashboard.model.widget_types.ScreenWidget.CAPTION;

/**
 * The ScreenWidget is type for any screen. Shows screen by {@link ScreenWidget#screenId}
 */
@MetaClass(name = "amxd$ScreenWidget")
@WidgetType(name = CAPTION,
        browseFrameId = "screenWidgetBrowse",
        editFrameId = "screenWidgetEdit")
public class ScreenWidget extends Widget {
    public static final String CAPTION = "Screen";

    @MetaProperty
    protected String screenId;

    public String getScreenId() {
        return screenId;
    }

    public void setScreenId(String screenId) {
        this.screenId = screenId;
    }
}
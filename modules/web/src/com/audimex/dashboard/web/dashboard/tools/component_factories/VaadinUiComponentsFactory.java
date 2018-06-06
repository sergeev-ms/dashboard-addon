/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.audimex.dashboard.web.dashboard.tools.component_factories;

import com.audimex.dashboard.annotation_analyzer.WidgetTypeAnalyzer;
import com.audimex.dashboard.annotation_analyzer.WidgetTypeInfo;
import com.audimex.dashboard.model.Widget;
import com.audimex.dashboard.web.dashboard.frames.canvas.CanvasFrame;
import com.audimex.dashboard.web.dashboard.layouts.CanvasGridLayout;
import com.audimex.dashboard.web.dashboard.layouts.CanvasHorizontalLayout;
import com.audimex.dashboard.web.dashboard.layouts.CanvasVerticalLayout;
import com.audimex.dashboard.web.dashboard.layouts.CanvasWidgetLayout;
import com.audimex.dashboard.web.widget_types.AbstractWidgetBrowse;
import com.haulmont.bali.util.ParamsMap;
import com.vaadin.ui.Layout;
import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.DDVerticalLayout;

import javax.inject.Inject;
import java.util.Optional;

import static com.audimex.dashboard.web.widget_types.AbstractWidgetBrowse.DASHBOARD;
import static com.audimex.dashboard.web.widget_types.AbstractWidgetBrowse.WIDGET;
import static fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode.NONE;
import static java.lang.String.format;

@org.springframework.stereotype.Component("amdx_VaadinUiComponentsFactory")
public class VaadinUiComponentsFactory implements VaadinComponentsFactory {
    @Inject
    protected WidgetTypeAnalyzer typeAnalyzer;

    @Override
    public CanvasVerticalLayout createCanvasVerticalLayout() {
        CanvasVerticalLayout layout = new CanvasVerticalLayout();
        layout.setDragMode(NONE);
        layout.setSizeFull();

        DDVerticalLayout verticalLayout = layout.getDelegate();
        verticalLayout.setDragMode(NONE);
        verticalLayout.setSizeFull();
        verticalLayout.setMargin(true);

        return layout;
    }

    @Override
    public CanvasHorizontalLayout createCanvasHorizontalLayout() {
        CanvasHorizontalLayout layout = new CanvasHorizontalLayout();
        layout.setDragMode(NONE);
        layout.setSizeFull();

        DDHorizontalLayout horizontalLayout = layout.getDelegate();
        horizontalLayout.setDragMode(NONE);
        horizontalLayout.setSizeFull();
        horizontalLayout.setMargin(true);

        return layout;
    }

    @Override
    public CanvasGridLayout createCanvasGridLayout(int cols, int rows) {
        CanvasGridLayout layout = new CanvasGridLayout(cols, rows);
        layout.setDragMode(NONE);
        layout.setSizeFull();

        DDGridLayout gridLayout = layout.getDelegate();
        gridLayout.setDragMode(NONE);
        gridLayout.setSizeFull();
        gridLayout.setMargin(true);

        return layout;
    }

    @Override
    public CanvasWidgetLayout createCanvasWidgetLayout(CanvasFrame frame, Widget widget) {
        Optional<WidgetTypeInfo> widgetTypeOpt = typeAnalyzer.getWidgetTypesInfo().stream()
                .filter(widgetType -> widget.getClass().equals(widgetType.getTypeClass()))
                .findFirst();

        if (!widgetTypeOpt.isPresent()) {
            throw new RuntimeException(format("There isn't found a screen for the widget class %s", widget.getClass()));
        }

        String frameId = widgetTypeOpt.get().getBrowseFrameId();
        AbstractWidgetBrowse widgetFrame = (AbstractWidgetBrowse) frame.openFrame(null, frameId, ParamsMap.of(
                WIDGET, widget,
                DASHBOARD, frame.getDashboard()
        ));
        widgetFrame.setSizeFull();
        widgetFrame.setMargin(true);

        CanvasWidgetLayout layout = new CanvasWidgetLayout();
        layout.addComponent(widgetFrame.unwrap(Layout.class));
        layout.setWidget(widget);
        layout.setDragMode(NONE);
        layout.setSizeFull();

        DDVerticalLayout verticalLayout = layout.getDelegate();
        verticalLayout.setDragMode(NONE);
        verticalLayout.setSizeFull();
        verticalLayout.setMargin(true);

        return layout;
    }
}
/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.audimex.dashboard.web.dashboard.tools;

import com.audimex.dashboard.annotation_analyzer.WidgetTypeAnalyzer;
import com.audimex.dashboard.annotation_analyzer.WidgetTypeInfo;
import com.audimex.dashboard.model.Dashboard;
import com.audimex.dashboard.model.Widget;
import com.audimex.dashboard.web.dashboard.events.LayoutRemoveEvent;
import com.audimex.dashboard.web.dashboard.events.OpenWidgetEditorEvent;
import com.audimex.dashboard.web.dashboard.vaadin_components.layouts.CanvasGridLayout;
import com.audimex.dashboard.web.dashboard.vaadin_components.layouts.CanvasHorizontalLayout;
import com.audimex.dashboard.web.dashboard.vaadin_components.layouts.CanvasVerticalLayout;
import com.audimex.dashboard.web.dashboard.vaadin_components.layouts.CanvasWidgetLayout;
import com.audimex.dashboard.web.widget_types.AbstractWidgetBrowse;
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.web.gui.icons.IconResolver;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import fi.jasoft.dragdroplayouts.DDGridLayout;
import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.DDVerticalLayout;

import javax.inject.Inject;
import java.util.Optional;

import static com.audimex.dashboard.web.DashboardIcon.GEAR_ICON;
import static com.audimex.dashboard.web.DashboardIcon.TRASH_ICON;
import static com.audimex.dashboard.web.DashboardStyleConstants.*;
import static com.audimex.dashboard.web.widget_types.AbstractWidgetBrowse.DASHBOARD;
import static com.audimex.dashboard.web.widget_types.AbstractWidgetBrowse.WIDGET;
import static fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode.CLONE;
import static java.lang.String.format;

@org.springframework.stereotype.Component("amdx_VaadinDropComponentsFactory")
public class VaadinDropComponentsFactory implements VaadinComponentsFactory {
    @Inject
    protected WidgetTypeAnalyzer typeAnalyzer;
    @Inject
    protected IconResolver iconResolver;
    @Inject
    protected Messages messages;
    @Inject
    protected Events events;

    @Override
    public CanvasVerticalLayout createCanvasVerticalLayout() {
        CanvasVerticalLayout layout = new CanvasVerticalLayout();
        layout.setDragMode(CLONE);
        layout.setSizeFull();
        layout.addStyleName(AMXD_SHADOW_BORDER);

        Button removeButton = createRemoveButton();
        removeButton.addClickListener(e -> {
            events.publish(new LayoutRemoveEvent(layout));
        });

        HorizontalLayout buttonsPanel = layout.getButtonsPanel();
        buttonsPanel.addStyleName(AMXD_LAYOUT_CONTROLS);
        buttonsPanel.addComponent(removeButton);

        DDVerticalLayout verticalLayout = layout.getVerticalLayout();
        verticalLayout.setDragMode(CLONE);
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);
        verticalLayout.addStyleName(AMXD_LAYOUT_CONTENT);

        return layout;
    }

    @Override
    public CanvasHorizontalLayout createCanvasHorizontalLayout() {
        CanvasHorizontalLayout layout = new CanvasHorizontalLayout();
        layout.setDragMode(CLONE);
        layout.setSizeFull();
        layout.addStyleName(AMXD_SHADOW_BORDER);

        Button removeButton = createRemoveButton();
        removeButton.addClickListener(e -> {
            events.publish(new LayoutRemoveEvent(layout));
        });

        HorizontalLayout buttonsPanel = layout.getButtonsPanel();
        buttonsPanel.addStyleName(AMXD_LAYOUT_CONTROLS);
        buttonsPanel.addComponent(removeButton);

        DDHorizontalLayout horizontalLayout = layout.getHorizontalLayout();
        horizontalLayout.setDragMode(CLONE);
        horizontalLayout.setSizeFull();
        horizontalLayout.setSpacing(true);
        horizontalLayout.setMargin(true);
        horizontalLayout.addStyleName(AMXD_LAYOUT_CONTENT);

        return layout;
    }

    @Override
    public CanvasGridLayout createCanvasGridLayout(int cols, int rows) {
        CanvasGridLayout layout = new CanvasGridLayout(cols, rows);
        layout.setDragMode(CLONE);
        layout.setSizeFull();
        layout.addStyleName(AMXD_SHADOW_BORDER);

        Button removeButton = createRemoveButton();
        removeButton.addClickListener(e -> {
            events.publish(new LayoutRemoveEvent(layout));
        });

        HorizontalLayout buttonsPanel = layout.getButtonsPanel();
        buttonsPanel.addStyleName(AMXD_LAYOUT_CONTROLS);
        buttonsPanel.addComponent(removeButton);

        DDGridLayout gridLayout = layout.getGridLayout();
        gridLayout.setDragMode(CLONE);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        gridLayout.setMargin(true);
        gridLayout.addStyleName(AMXD_LAYOUT_CONTENT);

        return layout;
    }

    @Override
    public CanvasWidgetLayout createCanvasWidgetLayout(Frame targetFrame, Widget widget, Dashboard dashboard) {
        Optional<WidgetTypeInfo> widgetTypeOpt = typeAnalyzer.getWidgetTypesInfo().stream()
                .filter(widgetType -> widget.getClass().equals(widgetType.getTypeClass()))
                .findFirst();

        if (!widgetTypeOpt.isPresent()) {
            //todo add dashboard exception;
            throw new RuntimeException(format("There isn't found a screen for the widget class %s", widget.getClass()));
        }

        String frameId = widgetTypeOpt.get().getBrowseFrameId();
        AbstractWidgetBrowse widgetFrame = (AbstractWidgetBrowse) targetFrame.openFrame(null, frameId, ParamsMap.of(
                WIDGET, widget,
                DASHBOARD, dashboard
        ));
        widgetFrame.setSizeFull();
        widgetFrame.setMargin(true);

        CanvasWidgetLayout layout = new CanvasWidgetLayout();
        layout.addComponent(widgetFrame.unwrap(Layout.class));
        layout.setWidget(widget);

        layout.setDragMode(CLONE);
        layout.setSizeFull();
        layout.addStyleName(AMXD_SHADOW_BORDER);

        Button removeButton = createRemoveButton();
        removeButton.addClickListener(e -> {
            events.publish(new LayoutRemoveEvent(layout));
        });
        Button editButton = createEditButton();
        editButton.addClickListener(e -> {
            events.publish(new OpenWidgetEditorEvent(layout));
        });

        HorizontalLayout buttonsPanel = layout.getButtonsPanel();
        buttonsPanel.addStyleName(AMXD_LAYOUT_CONTROLS);
        buttonsPanel.addComponent(removeButton);
        buttonsPanel.addComponent(editButton);

        DDVerticalLayout verticalLayout = layout.getVerticalLayout();
        verticalLayout.setDragMode(CLONE);
        verticalLayout.setSizeFull();
        verticalLayout.setSpacing(true);
        verticalLayout.setMargin(true);
        verticalLayout.addStyleName(AMXD_LAYOUT_CONTENT);

        return layout;

    }

    protected Button createEditButton() {
        Button editButton = new Button();
        editButton.addStyleName(AMXD_EDIT_BUTTON);
        editButton.setIcon(iconResolver.getIconResource(GEAR_ICON.source()));
        return editButton;
    }

    protected Button createRemoveButton() {
        Button removeButton = new Button();
        removeButton.addStyleName(AMXD_EDIT_BUTTON);
        removeButton.setIcon(iconResolver.getIconResource(TRASH_ICON.source()));
        return removeButton;
    }
}

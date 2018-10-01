/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.web.dashboard.tools.componentfactory;

import com.haulmont.addon.dashboard.entity.WidgetTemplate;
import com.haulmont.addon.dashboard.model.Widget;
import com.haulmont.addon.dashboard.model.visualmodel.*;
import com.haulmont.addon.dashboard.web.DashboardIcon;
import com.haulmont.addon.dashboard.web.DashboardStyleConstants;
import com.haulmont.addon.dashboard.web.dashboard.converter.JsonConverter;
import com.haulmont.addon.dashboard.web.dashboard.frames.editor.components.PaletteButton;
import com.haulmont.addon.dashboard.web.dashboard.tools.WidgetUtils;
import com.haulmont.addon.dashboard.web.repository.WidgetRepository;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class PaletteComponentsFactoryImpl implements PaletteComponentsFactory {
    @Inject
    protected ComponentsFactory factory;
    @Inject
    protected Events events;
    @Inject
    protected Metadata metadata;
    @Inject
    protected Messages messages;
    @Inject
    protected JsonConverter converter;
    @Inject
    protected WidgetUtils widgetUtils;
    @Inject
    protected WidgetRepository widgetRepository;

    public PaletteButton createVerticalLayoutButton() {
        PaletteButton button = createCommonButton();
        button.setCaption(messages.getMainMessage("verticalLayout"));
        button.setIconFromSet(DashboardIcon.VERTICAL_LAYOUT_ICON);
        button.setLayout(metadata.create(VerticalLayout.class));
        button.getLayout().setUuid(null);
        button.setDescription(messages.getMainMessage("verticalLayout"));
        return button;
    }

    public PaletteButton createHorizontalLayoutButton() {
        PaletteButton button = createCommonButton();
        button.setCaption(messages.getMainMessage("horizontalLayout"));
        button.setIconFromSet(DashboardIcon.HORIZONTAL_LAYOUT_ICON);
        button.setLayout(metadata.create(HorizontalLayout.class));
        button.getLayout().setUuid(null);
        button.setDescription(messages.getMainMessage("horizontalLayout"));
        return button;
    }

    public PaletteButton createGridLayoutButton() {
        PaletteButton button = createCommonButton();
        button.setCaption(messages.getMainMessage("gridLayout"));
        button.setIconFromSet(DashboardIcon.GRID_LAYOUT_ICON);
        button.setLayout(metadata.create(GridLayout.class));
        button.getLayout().setUuid(null);
        button.setDescription(messages.getMainMessage("gridLayout"));
        return button;
    }

    public PaletteButton createWidgetButton(Widget widget) {
        WidgetLayout layout = metadata.create(WidgetLayout.class);
        layout.setWidget(widget);

        PaletteButton button = createCommonButton();

        button.setCaption(widgetRepository.getLocalizedWidgetName(widget));
        button.setDescription(widget.getDescription());
        button.setLayout(layout);
        button.getLayout().setUuid(null);
        return button;
    }

    public PaletteButton createWidgetTemplateButton(WidgetTemplate wt) {
        Widget widget = converter.widgetFromJson(wt.getWidgetModel());
        widget.setName(widgetUtils.getWidgetType(widget.getBrowseFrameId()));
        WidgetTemplateLayout layout = metadata.create(WidgetTemplateLayout.class);
        layout.setWidget(widget);

        PaletteButton button = createCommonButton();
        button.setCaption(wt.getName() + " (" + widgetRepository.getLocalizedWidgetName(widget) + ")");
        button.setLayout(layout);
        button.getLayout().setUuid(null);
        return button;
    }

    protected PaletteButton createCommonButton() {
        PaletteButton button = new PaletteButton();
        button.setWidth("100%");
        button.setHeight("50px");
        button.setStyleName(DashboardStyleConstants.DASHBOARD_BUTTON);
        return button;
    }
}

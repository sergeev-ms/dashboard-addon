/*
 * Copyright (c) 2016-2017 Haulmont. All rights reserved.
 */
package com.audimex.dashboard.web.widgetparameter;

import com.audimex.dashboard.entity.WidgetParameter;
import com.audimex.dashboard.entity.WidgetParameterType;
import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.FieldGroup;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.LinkedHashMap;
import java.util.Map;

public class WidgetParameterEdit extends AbstractEditor<WidgetParameter> {
    @Named("fieldGroup.parameterType")
    protected LookupField parameterTypeField;
    @Inject
    protected FieldGroup fieldGroup;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    protected Datasource<WidgetParameter> widgetParameterDs;
    @Inject
    protected Metadata metadata;

    protected LookupField metaLookupField;
    protected LookupField viewLookupField;

    @Override
    protected void initNewItem(WidgetParameter item) {
        super.initNewItem(item);

        item.setParameterType(WidgetParameterType.STRING);
    }

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        FieldGroup.FieldConfig metaConfig = fieldGroup.getField("referenceToEntity.metaClassName");
        metaLookupField = componentsFactory.createComponent(LookupField.class);
        metaLookupField.setDatasource(widgetParameterDs, metaConfig.getProperty());
        metaConfig.setComponent(metaLookupField);

        FieldGroup.FieldConfig viewConfig = fieldGroup.getField("referenceToEntity.viewName");
        viewLookupField = componentsFactory.createComponent(LookupField.class);
        viewLookupField.setDatasource(widgetParameterDs, viewConfig.getProperty());
        viewConfig.setComponent(viewLookupField);

        Map<String, Object> metaClasses = new LinkedHashMap<>();
        metadata.getTools().getAllPersistentMetaClasses()
                .forEach(metaClass ->
                        metaClasses.put(metaClass.getName(), metaClass.getName())
                );
        metaLookupField.setOptionsMap(metaClasses);

        metaLookupField.addValueChangeListener(e -> {
            MetaClass metaClass = metadata.getClass((String) e.getValue());
            if (metaClass != null) {
                Map<String, Object> views = new LinkedHashMap<>();
                views.put(View.MINIMAL, View.MINIMAL);
                views.put(View.LOCAL, View.LOCAL);
                metadata.getViewRepository().getViewNames(metaClass)
                        .forEach(view -> views.put(view, view));
                viewLookupField.setOptionsMap(views);
            }
        });

        parameterTypeField.addValueChangeListener(event -> {
            if (WidgetParameterType.ENTITY.equals(event.getValue()) ||
                    WidgetParameterType.LIST_ENTITY.equals(event.getValue())) {
                showRequiredField(metaLookupField, true);
                showRequiredField(viewLookupField, true);
            } else {
                showRequiredField(metaLookupField, false);
                showRequiredField(viewLookupField, false);
            }
        });
    }

    protected void showRequiredField(LookupField field, boolean isShow) {
        field.setVisible(isShow);
        field.setRequired(isShow);
    }
}
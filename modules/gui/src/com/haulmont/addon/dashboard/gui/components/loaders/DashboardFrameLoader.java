/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.gui.components.loaders;

import com.haulmont.addon.dashboard.gui.components.DashboardFrame;
import com.haulmont.addon.dashboard.model.Parameter;
import com.haulmont.bali.datastruct.Pair;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.ComponentsHelper;
import com.haulmont.cuba.gui.config.WindowConfig;
import com.haulmont.cuba.gui.config.WindowInfo;
import com.haulmont.cuba.gui.xml.layout.*;
import com.haulmont.cuba.gui.xml.layout.loaders.ContainerLoader;
import com.haulmont.cuba.gui.xml.layout.loaders.FrameLoader;
import com.haulmont.addon.dashboard.model.param_value_types.*;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isNotBlank;

//partially copy-pasted from com.haulmont.cuba.gui.xml.layout.loaders.FrameComponentLoader
public class DashboardFrameLoader extends ContainerLoader<DashboardFrame> {//TODO: парсит xml и создает фрейм https://youtrack.haulmont.com/issue/DASH-92

    protected String frameId;
    protected ComponentLoader frameLoader;
    protected String aClass;

    Metadata metadata;

    @Override
    public void createComponent() {
        metadata = AppBeans.get(Metadata.class);
        frameId = element.attributeValue("id");
        aClass = element.attributeValue("class");

        WindowConfig windowConfig = AppBeans.get(WindowConfig.NAME);
        WindowInfo windowInfo = windowConfig.getWindowInfo("dashboardComponent");
        String template = windowInfo.getTemplate();


        DashboardLayoutLoader layoutLoader = new DashboardLayoutLoader(context, factory, LayoutLoaderConfig.getFrameLoaders());
        layoutLoader.setLocale(getLocale());
        layoutLoader.setMessagesPack(getMessagesPack());

        String currentFrameId = context.getCurrentFrameId();
        context.setCurrentFrameId(frameId);
        try {
            Pair<ComponentLoader, Element> loaderElementPair = layoutLoader.createFrameComponent(template, frameId, context.getParams());
            frameLoader = loaderElementPair.getFirst();
            resultComponent = (DashboardFrame) frameLoader.getResultComponent();
        } finally {
            context.setCurrentFrameId(currentFrameId);
        }
    }

    @Override
    public void loadComponent() {
        if (resultComponent.getMessagesPack() == null) {
            resultComponent.setMessagesPack(messagesPack);
        }

        assignXmlDescriptor(resultComponent, element);
        loadVisible(resultComponent, element);

        loadStyleName(resultComponent, element);
        loadResponsive(resultComponent, element);

        loadAlign(resultComponent, element);

        loadHeight(resultComponent, element, ComponentsHelper.getComponentHeigth(resultComponent));
        loadWidth(resultComponent, element, ComponentsHelper.getComponentWidth(resultComponent));

        loadIcon(resultComponent, element);
        loadCaption(resultComponent, element);
        loadDescription(resultComponent, element);

        loadReferenceName(resultComponent, element);
        loadJsonPath(resultComponent, element);
        loadParams(resultComponent, element);
        loadTimerDelay(resultComponent, element);

        if (context.getFrame() != null) {
            resultComponent.setFrame(context.getFrame());
        }

        String currentFrameId = context.getCurrentFrameId();
        context.setCurrentFrameId(frameId);


        frameLoader.loadComponent();
        context.setCurrentFrameId(currentFrameId);
    }

    protected void loadReferenceName(DashboardFrame resultComponent, Element element) {
        String referenceName = element.attributeValue("referenceName");
        if (isNotBlank(referenceName)) {
            resultComponent.setReferenceName(referenceName);
        }
    }

    protected void loadJsonPath(DashboardFrame resultComponent, Element element) {
        String jsonPath = element.attributeValue("jsonPath");
        if (isNotBlank(jsonPath)) {
            resultComponent.setJsonPath(jsonPath);
        }
    }

    protected void loadTimerDelay(DashboardFrame resultComponent, Element element) {
        String timerDelayValue = element.attributeValue("timerDelay");
        if (isNotBlank(timerDelayValue)) {
            resultComponent.setTimerDelay(Integer.parseInt(timerDelayValue) * 1000);
        }
    }

    protected void loadParams(DashboardFrame resultComponent, Element element) {
        List<Parameter> parameters = (List<Parameter>) element.content().stream()
                .filter(child -> child instanceof DefaultElement &&
                        "parameter".equals(((DefaultElement) child).getName()))
                .map(xmlParam -> createParameter((DefaultElement) xmlParam))
                .collect(Collectors.toList());

        resultComponent.setXmlParameters(parameters);
    }

    protected Parameter createParameter(DefaultElement xmlParam) {
        String name = xmlParam.attributeValue("name");
        String value = xmlParam.attributeValue("value");
        String type = xmlParam.attributeValue("type");

        Parameter parameter = metadata.create(Parameter.class);
        parameter.setName(name);
        parameter.setParameterValue(createParameterValue(type, value));
        return parameter;
    }

    protected ParameterValue createParameterValue(String type, String value) {
        switch (type) {
            case "boolean":
                return new BooleanParameterValue(Boolean.valueOf(value));
            case "date":
                return new DateParameterValue(Date.valueOf(value));
            case "dateTime":
                return new DateTimeParameterValue(Date.valueOf(value));
            case "decimal":
                return new DecimalParameterValue(new BigDecimal(value));
            case "int":
                return new IntegerParameterValue(new Integer(value));
            case "long":
                return new LongParameterValue(new Long(value));
            case "time":
                return new TimeParameterValue(Date.valueOf(value));
            case "uuid":
                return new UuidParameterValue(UUID.fromString(value));
            case "string":
            default:
                return new StringParameterValue(value);
        }
    }

    protected class DashboardLayoutLoader extends LayoutLoader {

        protected DashboardLayoutLoader(ComponentLoader.Context context, ComponentsFactory factory, LayoutLoaderConfig config) {
            super(context, factory, config);
        }

        public Pair<ComponentLoader, Element> createFrameComponent(String resourcePath, String id, Map<String, Object> params) {
            ScreenXmlLoader screenXmlLoader = AppBeans.get(ScreenXmlLoader.class);
            Element element = screenXmlLoader.load(resourcePath, id, params);

            //added replace for class-control
            if (isNotBlank(aClass) && isNotBlank(element.attributeValue("class"))) {
                element.addAttribute("class", aClass);
            }

            ComponentLoader loader = getLoader(element);
            FrameLoader frameLoader = (FrameLoader) loader;
            frameLoader.setFrameId(id);

            loader.createComponent();

            return new Pair<>(loader, element);
        }
    }

}
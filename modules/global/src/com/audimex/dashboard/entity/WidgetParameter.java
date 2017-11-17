/*
 * Copyright (c) 2016-2017 Haulmont. All rights reserved.
 */
package com.audimex.dashboard.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseGenericIdEntity;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.EmbeddedParameters;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.ReferenceToEntitySupport;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@NamePattern("%s|name")
@Table(name = "AMXD_WIDGET_PARAMETER")
@Entity(name = "amxd$WidgetParameter")
public class WidgetParameter extends StandardEntity {
    private static final long serialVersionUID = -6936139215635675937L;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "ALIAS")
    protected String alias;

    @Column(name = "MAPPED_ALIAS")
    protected String mappedAlias;

    @Column(name = "PARAMETER_TYPE")
    protected Integer parameterType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DASHBOARD_WIDGET_ID")
    protected DashboardWidget dashboardWidget;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DASHBOARD_WIDGET_LINK_ID")
    protected DashboardWidgetLink dashboardWidgetLink;

    @Column(name = "INTEGER_VALUE")
    protected Integer integerValue;

    @Column(name = "STRING_VALUE")
    protected String stringValue;

    @Column(name = "DECIMAL_VALUE")
    protected BigDecimal decimalValue;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_VALUE")
    protected Date dateValue;

    @Column(name = "BOOL_VALUE")
    protected Boolean boolValue;

    @Column(name = "LONG_VALUE")
    protected Long longValue;

    @Embedded
    @EmbeddedParameters(nullAllowed = false)
    @AttributeOverrides({
        @AttributeOverride(name = "entityId", column = @Column(name = "REFERENCE_TO_ENTITY_ENTITY_ID")),
        @AttributeOverride(name = "metaClassName", column = @Column(name = "REFERENCE_TO_ENTITY_META_CLASS_NAME")),
        @AttributeOverride(name = "viewName", column = @Column(name = "REFERENCE_TO_ENTITY_VIEW_NAME"))
    })
    protected ReferenceToEntity referenceToEntity;

    @Column(name = "DOUBLE_VALUE")
    protected Double doubleValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MASTER_PARAMETER_ID")
    protected WidgetParameter masterParameter;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "masterParameter")
    protected List<WidgetParameter> listParameters;

    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "masterParameter")
    protected List<WidgetParameter> additionalParameters;

    @Column(name = "INPUT_TYPE")
    protected Integer inputType;

    @Column(name = "ORDER_NUM")
    protected Integer orderNum;

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }


    public void setMappedAlias(String mappedAlias) {
        this.mappedAlias = mappedAlias;
    }

    public String getMappedAlias() {
        return mappedAlias;
    }


    public void setInputType(ParameterInputType inputType) {
        this.inputType = inputType == null ? null : inputType.getId();
    }

    public ParameterInputType getInputType() {
        return inputType == null ? null : ParameterInputType.fromId(inputType);
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }


    public void setAdditionalParameters(List<WidgetParameter> additionalParameters) {
        this.additionalParameters = additionalParameters;
    }

    public void addAdditionalParameter(WidgetParameter additionalParameter) {
        if (additionalParameters == null) {
            additionalParameters = new ArrayList<>();
        }
        additionalParameters.add(additionalParameter);
    }

    public List<WidgetParameter> getAdditionalParameters() {
        return additionalParameters;
    }

    public WidgetParameter getAdditionalParameter(@Nonnull String name) {
        WidgetParameter parameter = null;
        if (additionalParameters != null) {
            Optional<WidgetParameter> optional = additionalParameters.stream()
                    .filter(p -> name.equals(p.getName()))
                    .findFirst();

            parameter = optional.orElse(null);
        }
        return parameter;
    }

    public void setReferenceToEntity(ReferenceToEntity referenceToEntity) {
        this.referenceToEntity = referenceToEntity;
    }

    public ReferenceToEntity getReferenceToEntity() {
        return referenceToEntity;
    }


    @PostConstruct
    public void init() {
        Metadata metadata = AppBeans.get(Metadata.NAME);
        referenceToEntity = metadata.create(ReferenceToEntity.class);
    }

    public void setMasterParameter(WidgetParameter masterParameter) {
        this.masterParameter = masterParameter;
    }

    public WidgetParameter getMasterParameter() {
        return masterParameter;
    }

    public void setListParameters(List<WidgetParameter> listParameters) {
        this.listParameters = listParameters;
    }

    public void addListParameter(WidgetParameter listParameter) {
        if (this.listParameters == null) {
            this.listParameters = new ArrayList<>();
        }

        this.listParameters.add(listParameter);
    }

    public List<WidgetParameter> getListParameters() {
        return listParameters;
    }


    public void setIntegerValue(Integer integerValue) {
        this.integerValue = integerValue;
    }

    public Integer getIntegerValue() {
        return integerValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }


    public void setDecimalValue(BigDecimal decimalValue) {
        this.decimalValue = decimalValue;
    }

    public BigDecimal getDecimalValue() {
        return decimalValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setBoolValue(Boolean boolValue) {
        this.boolValue = boolValue;
    }

    public Boolean getBoolValue() {
        return boolValue;
    }



    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setLongValue(Long longValue) {
        this.longValue = longValue;
    }

    public Long getLongValue() {
        return longValue;
    }

    public void setDashboardWidgetLink(DashboardWidgetLink dashboardWidgetLink) {
        this.dashboardWidgetLink = dashboardWidgetLink;
    }

    public DashboardWidgetLink getDashboardWidgetLink() {
        return dashboardWidgetLink;
    }


    public void setDashboardWidget(DashboardWidget dashboardWidget) {
        this.dashboardWidget = dashboardWidget;
    }

    public DashboardWidget getDashboardWidget() {
        return dashboardWidget;
    }


    public void setParameterType(WidgetParameterType parameterType) {
        this.parameterType = parameterType == null ? null : parameterType.getId();
    }

    public WidgetParameterType getParameterType() {
        return parameterType == null ? null : WidgetParameterType.fromId(parameterType);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(Object value) {
        setValue(value, null);
    }

    public void setValue(Object value, String viewName) {
        if (value == null) {
            stringValue = null;
            integerValue = null;
            decimalValue = null;
            doubleValue = null;
            boolValue = null;
            dateValue = null;
            referenceToEntity.setEntityId(null);
            listParameters = null;
        } else if (value instanceof Date) {
            setDateValue((Date) value);
        } else if (value instanceof Integer) {
            setIntegerValue((Integer) value);
        } else if (value instanceof Double) {
            setDoubleValue((Double) value);
        } else if (value instanceof BigDecimal) {
            setDecimalValue((BigDecimal) value);
        } else if (value instanceof Boolean) {
            setBoolValue((Boolean) value);
        } else if (value instanceof String) {
            setStringValue((String) value);
        } else if (value instanceof Long) {
            setLongValue((Long) value);
        } else if (value instanceof BaseGenericIdEntity) {
            Object referenceId = getReferenceId(value);
            referenceToEntity.setObjectEntityId(referenceId);
            referenceToEntity.setMetaClassName(((BaseGenericIdEntity) value).getMetaClass().getName());
            if (viewName != null) {
                referenceToEntity.setViewName(viewName);
            }
        } else if (value instanceof List) {
            Optional optional = ((List) value).stream().findAny();
            if (optional.isPresent()) {
                if (getListParameters() != null) {
                    getListParameters().clear();
                }
                ((List) value).forEach(par -> {
                    Metadata metadata = AppBeans.get(Metadata.NAME);
                    WidgetParameter wp = metadata.create(WidgetParameter.class);
                    wp.setMasterParameter(this);
                    wp.setParameterType(WidgetParameterType.ENTITY);
                    wp.setReferenceToEntity(new ReferenceToEntity());
                    wp.getReferenceToEntity().setEntityId(((BaseUuidEntity) par).getId());
                    wp.getReferenceToEntity().setMetaClassName(getReferenceToEntity().getMetaClassName());
                    wp.getReferenceToEntity().setViewName(getReferenceToEntity().getViewName());
                    addListParameter(wp);
                });
            } else {
                setListParameters(null);
            }
        } else {
            throw new IllegalArgumentException("Unsupported value type " + value.getClass());
        }
    }

    protected Object getReferenceId(Object value) {
        ReferenceToEntitySupport referenceToEntitySupport = AppBeans.get(ReferenceToEntitySupport.class);
        return referenceToEntitySupport.getReferenceId((BaseGenericIdEntity) value);
    }

    public Object getValue() {
        if (stringValue != null) {
            return stringValue;
        } else if (integerValue != null) {
            return integerValue;
        } else if (doubleValue != null) {
            return doubleValue;
        } else if (decimalValue != null) {
            return decimalValue;
        } else if (dateValue != null) {
            return dateValue;
        } else if (boolValue != null) {
            return boolValue;
        } else if (longValue != null) {
            return longValue;
        } else if (WidgetParameterType.ENTITY.getId().equals(parameterType)) {
            return referenceToEntity;
        } else if (WidgetParameterType.LIST_ENTITY.getId().equals(parameterType)) {
            return listParameters;
        }

        return null;
    }

    public void setRefEntityId(UUID id) {
        if (referenceToEntity != null) {
            referenceToEntity.setEntityId(id);
        }
    }
}
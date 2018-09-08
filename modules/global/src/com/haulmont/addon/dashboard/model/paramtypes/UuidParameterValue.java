/*
 * Copyright (c) 2016-2018 Haulmont. All rights reserved.
 */

package com.haulmont.addon.dashboard.model.paramtypes;

import java.util.UUID;

public class UuidParameterValue implements ParameterValue {
    protected UUID value;

    public UuidParameterValue() {
    }

    public UuidParameterValue(UUID value) {
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }

    public void setValue(UUID value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("type: uuid; value=%s", value);
    }
}
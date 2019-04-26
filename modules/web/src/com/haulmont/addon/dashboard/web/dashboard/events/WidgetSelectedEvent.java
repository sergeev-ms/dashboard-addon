/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.dashboard.web.dashboard.events;

import java.util.UUID;

public class WidgetSelectedEvent extends AbstractDashboardEditEvent {

    public enum Target {
        CANVAS,
        TREE
    }

    private Target target;

    public WidgetSelectedEvent(UUID source) {
        super(source);
    }

    public WidgetSelectedEvent(UUID layoutUuid, Target target) {
        super(layoutUuid);
        this.target = target;
    }

    @Override
    public UUID getSource() {
        return (UUID) super.getSource();
    }

    public Target getTarget() {
        return target;
    }
}

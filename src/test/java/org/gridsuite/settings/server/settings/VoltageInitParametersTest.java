/**
 * Copyright (c) 2023, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.gridsuite.settings.server.settings;

import java.util.List;
import java.util.UUID;

import org.gridsuite.settings.server.dto.SettingInfos;
import org.gridsuite.settings.server.dto.voltageinit.FilterEquipments;
import org.gridsuite.settings.server.dto.voltageinit.VoltageInitParametersInfos;
import org.gridsuite.settings.server.dto.voltageinit.VoltageInitVoltageLimitsParameterInfos;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */
public class VoltageInitParametersTest extends AbstractSettingTest {

    @Override
    protected SettingInfos buildSetting() {
        return VoltageInitParametersInfos.builder()
            .constantQGenerators(List.of(FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("qgenFilter1")
                    .build(), FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("qgenFilter2")
                    .build()))
            .variableTwoWindingsTransformers(List.of(FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("vtwFilter1")
                    .build(), FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("vtwFilter2")
                    .build()))
            .build();
    }

    @Override
    protected SettingInfos buildSettingUpdate() {
        return VoltageInitParametersInfos.builder()
            .voltageLimits(List.of(VoltageInitVoltageLimitsParameterInfos.builder()
                .priority(0)
                .lowVoltageLimit(2.0)
                .highVoltageLimit(20.0)
                .filters(List.of(FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("filterNameModified")
                    .build()))
                .build()))
            .variableShuntCompensators(List.of(FilterEquipments.builder()
                .filterId(UUID.randomUUID())
                .filterName("vscFilter1")
                .build()))
            .variableTwoWindingsTransformers(List.of(FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("vtwFilter1Modified")
                    .build(), FilterEquipments.builder()
                    .filterId(UUID.randomUUID())
                    .filterName("vtwFilter2Modified")
                    .build()))
            .build();
    }
}

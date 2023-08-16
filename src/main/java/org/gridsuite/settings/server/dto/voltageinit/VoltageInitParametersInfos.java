/**
 * Copyright (c) 2023, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.gridsuite.settings.server.dto.voltageinit;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

import org.gridsuite.settings.server.dto.SettingInfos;
import org.gridsuite.settings.server.entities.voltageinit.VoltageInitParametersEntity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Schema(description = "Voltage init setting")
@JsonTypeName("VOLTAGE_INIT_SETTING")
public class VoltageInitParametersInfos extends SettingInfos {
    List<VoltageInitVoltageLimitsParameterInfos> voltageLimits;

    List<FilterEquipments> constantQGenerators;

    List<FilterEquipments> variableTwoWindingsTransformers;

    List<FilterEquipments> variableShuntCompensators;

    @Override
    public VoltageInitParametersEntity toEntity() {
        return new VoltageInitParametersEntity(this);
    }
}

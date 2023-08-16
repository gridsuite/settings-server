/*
  Copyright (c) 2023, RTE (http://www.rte-france.com)
  This Source Code Form is subject to the terms of the Mozilla Public
  License, v. 2.0. If a copy of the MPL was not distributed with this
  file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.gridsuite.settings.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.gridsuite.settings.server.SettingType;
import org.gridsuite.settings.server.dto.voltageinit.VoltageInitParametersInfos;
import org.gridsuite.settings.server.entities.SettingEntity;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = VoltageInitParametersInfos.class),
})
@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "Setting attributes")
public class SettingInfos {
    @Schema(description = "Setting id")
    private UUID uuid;

    @Schema(description = "Setting date")
    private ZonedDateTime date;

    @Schema(description = "Setting name")
    private String name;

    @JsonIgnore
    public SettingEntity toEntity() {
        throw new UnsupportedOperationException("TODO");
    }

    @JsonIgnore
    public final SettingType getType() {
        return SettingType.valueOf(this.getClass().getAnnotation(JsonTypeName.class).value());
    }
}

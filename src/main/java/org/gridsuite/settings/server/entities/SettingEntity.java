/*
  Copyright (c) 2023, RTE (http://www.rte-france.com)
  This Source Code Form is subject to the terms of the Mozilla Public
  License, v. 2.0. If a copy of the MPL was not distributed with this
  file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.gridsuite.settings.server.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import javax.persistence.*;

import org.gridsuite.settings.server.SettingType;
import org.gridsuite.settings.server.dto.SettingInfos;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "setting")
public class SettingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SettingType type;

    protected SettingEntity(SettingInfos settingInfos) {
        if (settingInfos == null) {
            throw new NullPointerException("Missing setting description");
        }
        //We need to limit the precision to avoid database precision storage limit issue (postgres has a precision of 6 digits while h2 can go to 9)
        this.date = ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MICROS);
        this.name = settingInfos.getName();
        this.type = settingInfos.getType();
    }

    public SettingInfos toSettingInfos() {
        return SettingInfos.builder()
                .uuid(this.id)
                .date(this.date)
                .name(this.name)
                .build();
    }

    public void update(SettingInfos settingInfos) {
        // Basic attributes are immutable in the database
        if (settingInfos == null) {
            throw new NullPointerException("Impossible to update entity from null DTO");
        }
        this.name = settingInfos.getName();
    }

}

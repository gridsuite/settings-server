/**
 * Copyright (c) 2023, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.gridsuite.settings.server;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.gridsuite.settings.server.dto.SettingInfos;
import org.gridsuite.settings.server.entities.SettingEntity;
import org.gridsuite.settings.server.repository.SettingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;

    public SettingsService(SettingsRepository settingsRepository) {
        this.settingsRepository = settingsRepository;
    }

    public SettingInfos createSetting(SettingInfos settingInfos) {
        return settingsRepository.save(settingInfos.toEntity()).toSettingInfos();
    }

    public SettingInfos getSetting(UUID settingUuid) {
        return settingsRepository.findById(settingUuid).orElseThrow().toSettingInfos();
    }

    public List<SettingInfos> getSettingsByType(SettingType type) {
        if (type == null) {
            return settingsRepository.findAll().stream().map(SettingEntity::toSettingInfos).collect(Collectors.toList());
        }
        return settingsRepository.findAllByType(type).stream().map(SettingEntity::toSettingInfos).collect(Collectors.toList());
    }

    @Transactional
    public void updateSetting(UUID settingUuid, SettingInfos settingInfos) {
        settingsRepository.findById(settingUuid).orElseThrow().update(settingInfos);
    }

    public void deleteSetting(UUID settingUuid) {
        settingsRepository.deleteById(settingUuid);
    }
}

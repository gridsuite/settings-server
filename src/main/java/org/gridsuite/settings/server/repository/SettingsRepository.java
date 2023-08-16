/**
 * Copyright (c) 2020, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.gridsuite.settings.server.repository;

import java.util.List;
import java.util.UUID;

import org.gridsuite.settings.server.SettingType;
import org.gridsuite.settings.server.entities.SettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */

@Repository
public interface SettingsRepository extends JpaRepository<SettingEntity, UUID> {
    List<SettingEntity> findAllByType(SettingType type);
}

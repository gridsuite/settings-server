/**
 * Copyright (c) 2023, RTE (http://www.rte-france.com)
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.gridsuite.settings.server.settings;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.gridsuite.settings.server.dto.SettingInfos;
import org.gridsuite.settings.server.entities.SettingEntity;
import org.gridsuite.settings.server.repository.SettingsRepository;

import static org.gridsuite.settings.server.utils.assertions.Assertions.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ayoub LABIDI <ayoub.labidi at rte-france.com>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class AbstractSettingTest {

    private static final String URI_SETTINGS_BASE = "/v1/settings";

    private static final String URI_SETTINGS_GET_PUT = URI_SETTINGS_BASE + "/";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper mapper;

    @Autowired
    private SettingsRepository settingsRepository;

    @Before
    public void setup() {
        settingsRepository.deleteAll();
    }

    @After
    public void tearOff() {
        settingsRepository.deleteAll();
    }

    @Test
    public void testCreate() throws Exception {

        SettingInfos settingToCreate = buildSetting();
        String settingToCreateJson = mapper.writeValueAsString(settingToCreate);

        mockMvc.perform(post(URI_SETTINGS_BASE).content(settingToCreateJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        SettingInfos createdSetting = settingsRepository.findAll().get(0).toSettingInfos();

        assertThat(createdSetting).recursivelyEquals(settingToCreate);
    }

    @Test
    public void testRead() throws Exception {

        SettingInfos settingToRead = buildSetting();

        UUID settingUuid = saveAndRetunId(settingToRead);

        MvcResult mvcResult = mockMvc.perform(get(URI_SETTINGS_GET_PUT + settingUuid))
                .andExpect(status().isOk()).andReturn();
        String resultAsString = mvcResult.getResponse().getContentAsString();
        SettingInfos receivedSetting = mapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertThat(receivedSetting).recursivelyEquals(settingToRead);
    }

    @Test
    public void testUpdate() throws Exception {

        SettingInfos settingToUpdate = buildSetting();

        UUID settingUuid = saveAndRetunId(settingToUpdate);

        settingToUpdate = buildSettingUpdate();

        String settingToUpdateJson = mapper.writeValueAsString(settingToUpdate);

        mockMvc.perform(put(URI_SETTINGS_GET_PUT + settingUuid).content(settingToUpdateJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        SettingInfos updatedSetting = settingsRepository.findById(settingUuid).get().toSettingInfos();

        assertThat(updatedSetting).recursivelyEquals(settingToUpdate);
    }

    @Test
    public void testDelete() throws Exception {

        SettingInfos settingToDelete = buildSetting();

        UUID settingUuid = saveAndRetunId(settingToDelete);

        mockMvc.perform(delete(URI_SETTINGS_GET_PUT + settingUuid)).andExpect(status().isOk()).andReturn();

        List<SettingEntity> storedSettings = settingsRepository.findAll();

        assertTrue(storedSettings.isEmpty());
    }

    @Test
    public void testGetAllByType() throws Exception {
        SettingInfos setting1 = buildSetting();

        SettingInfos setting2 = buildSettingUpdate();

        saveAndRetunId(setting1);

        saveAndRetunId(setting2);

        MvcResult mvcResult = mockMvc.perform(get(URI_SETTINGS_BASE + "?type=" + setting1.getType()))
                .andExpect(status().isOk()).andReturn();
        String resultAsString = mvcResult.getResponse().getContentAsString();
        List<SettingInfos> receivedSettings = mapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertThat(receivedSettings).hasSize(2);

        //get All with no type provided
        mvcResult = mockMvc.perform(get(URI_SETTINGS_BASE))
                .andExpect(status().isOk()).andReturn();
        resultAsString = mvcResult.getResponse().getContentAsString();
        receivedSettings = mapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertThat(receivedSettings).hasSize(2);
    }

    /** Save a setting into the repository and return its UUID. */
    protected UUID saveAndRetunId(SettingInfos settingInfos) {
        settingsRepository.save(settingInfos.toEntity());
        return settingsRepository.findAll().get(0).getId();
    }

    protected abstract SettingInfos buildSetting();

    protected abstract SettingInfos buildSettingUpdate();

}


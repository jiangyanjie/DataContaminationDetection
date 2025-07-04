/*
 * Copyright 2023 Apollo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.ctrip.framework.apollo.biz.service;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import com.ctrip.framework.apollo.biz.entity.ServerConfig;
import com.ctrip.framework.apollo.biz.repository.ServerConfigRepository;
import com.ctrip.framework.apollo.common.config.RefreshablePropertySource;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.foundation.Foundation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@Component
public class BizDBPropertySource extends RefreshablePropertySource {

  private static final Logger logger = LoggerFactory.getLogger(BizDBPropertySource.class);

  private final ServerConfigRepository serverConfigRepository;

  private final DataSource dataSource;

  private final Environment env;

  @Autowired
  public BizDBPropertySource(final ServerConfigRepository serverConfigRepository, DataSource dataSource,
                             final Environment env) {
    super("DBConfig", Maps.newConcurrentMap());
    this.serverConfigRepository = serverConfigRepository;
    this.dataSource = dataSource;
    this.env = env;
  }

  @PostConstruct
  public void runSqlScript() throws Exception {
    if (env.acceptsProfiles(Profiles.of("h2"))) {
      Resource resource = new ClassPathResource("jpa/init.h2.sql");
      if (resource.exists()) {
        DatabasePopulatorUtils.execute(new ResourceDatabasePopulator(resource), dataSource);
      }
    }
  }

  String getCurrentDataCenter() {
    return Foundation.server().getDataCenter();
  }

  @Override
  protected void refresh() {
    Iterable<ServerConfig> dbConfigs = serverConfigRepository.findAll();

    Map<String, Object> newConfigs = Maps.newHashMap();
    //default cluster's configs
    for (ServerConfig config : dbConfigs) {
      if (Objects.equals(ConfigConsts.CLUSTER_NAME_DEFAULT, config.getCluster())) {
        newConfigs.put(config.getKey(), config.getValue());
      }
    }

    //data center's configs
    String dataCenter = getCurrentDataCenter();
    for (ServerConfig config : dbConfigs) {
      if (Objects.equals(dataCenter, config.getCluster())) {
        newConfigs.put(config.getKey(), config.getValue());
      }
    }

    //cluster's config
    if (!Strings.isNullOrEmpty(System.getProperty(ConfigConsts.APOLLO_CLUSTER_KEY))) {
      String cluster = System.getProperty(ConfigConsts.APOLLO_CLUSTER_KEY);
      for (ServerConfig config : dbConfigs) {
        if (Objects.equals(cluster, config.getCluster())) {
          newConfigs.put(config.getKey(), config.getValue());
        }
      }
    }

    //put to environment
    for (Map.Entry<String, Object> config: newConfigs.entrySet()){
      String key = config.getKey();
      Object value = config.getValue();

      if (this.source.get(key) == null) {
        logger.info("Load config from DB : {} = {}", key, value);
      } else if (!Objects.equals(this.source.get(key), value)) {
        logger.info("Load config from DB : {} = {}. Old value = {}", key,
                    value, this.source.get(key));
      }

      this.source.put(key, value);

    }

  }

}

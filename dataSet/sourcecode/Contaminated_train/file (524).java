package org.nashapamiac.backupconfigurator.logic;

import org.nashapamiac.backupconfigurator.configbuilder.ConfigFileBuilder;
import org.nashapamiac.backupconfigurator.configwriter.ConfigWriter;
import org.nashapamiac.backupconfigurator.entities.Config;
import org.nashapamiac.backupconfigurator.processrunner.BackupRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 16.11.13
 * Time: 20:44
 * Application Logic
 */
public class ConfiguratorLogic {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ConfigFileBuilder cfgbuilder;
    private ConfigWriter writer;
    private BackupRunner runner;
    Logger logger = LoggerFactory.getLogger("Main");

    @Autowired
    public void setCfgbuilder(ConfigFileBuilder cfgbuilder) {
        this.cfgbuilder = cfgbuilder;
    }

    @Autowired
    public void setWriter(ConfigWriter writer) {
        this.writer = writer;
    }

    @Autowired
    public void setRunner(BackupRunner runner) {
        this.runner = runner;
    }

    public Config getConfig() {
        logger.info("Getting config from server");
        lock.readLock().lock();
        Config result = cfgbuilder.buildConfig();
        lock.readLock().unlock();
        return result;
    }

    public void writeConfig(Config config) {
        logger.info("Attempting to write new config");
        lock.writeLock().lock();
        writer.writeConfig(config);
        lock.writeLock().unlock();
    }

    public void runBackup() {
        logger.info("Attempting to make extra copy");
        new ProcessRunner().start();
    }

    class ProcessRunner extends Thread{
        public void run() {
            runner.createCopy();
        }
    }
}
//TODO: Возможность запуска команды на немедленное копирование

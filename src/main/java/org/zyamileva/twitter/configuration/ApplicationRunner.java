package org.zyamileva.twitter.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.configuration.migrations.Migration;
import org.zyamileva.twitter.configuration.options.Configuration;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.configuration.options.OptionsConfiguration;

public class ApplicationRunner {
    private static final Logger log = LogManager.getLogger(ApplicationRunner.class);

    public static void run(String[] args) {
        Configuration configuration = createConfiguration(args);
        Migration.getInstance().performMigration(configuration);
        Context.getInstance().initContext(configuration);
    }

    private static Configuration createConfiguration(String[] args) {
        Configuration configuration = OptionsConfiguration.configureOptions(args);
        log.info("Configuration created: " + configuration);
        return configuration;
    }
}
package org.zyamileva.twitter.configuration.options;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class OptionsConfiguration {
    public static final String POPULATE_DB_OPTION = "populateDB";
    public static final String POPULATE_DB_DESCRIPTION = "Fill db with dummy data.";
    private static final Logger log = LogManager.getLogger(OptionsConfiguration.class);

    public static Configuration configureOptions(String[] args) {
        Options options = new Options();
        options.addRequiredOption(DaoTypeOption.DAO_TYPE_OPTION_SHORT, DaoTypeOption.DAO_TYPE_OPTION_LONG, true, DaoTypeOption.DAO_TYPE_OPTION_DESCRIPTION);
        options.addOption(POPULATE_DB_OPTION, POPULATE_DB_DESCRIPTION);

        DefaultParser defaultParser = new DefaultParser();
        try {
            CommandLine cmd = defaultParser.parse(options, args);
            DaoTypeOption daoTypeOption = parseDaoTypeOption(cmd);
            boolean populateDBOption = parsePopulateDBOption(cmd);
            return new Configuration(daoTypeOption, populateDBOption);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception during parsing options.");
            System.exit(1);
            return null;
        }
    }

    private static DaoTypeOption parseDaoTypeOption(CommandLine cmd) {
        String daoTypeOption = cmd.getOptionValue(DaoTypeOption.DAO_TYPE_OPTION_SHORT);
        if (daoTypeOption == null) {
            daoTypeOption = cmd.getOptionValue(DaoTypeOption.DAO_TYPE_OPTION_LONG);
        }
        if (DaoTypeOption.IN_MEMORY.getValue().equals(daoTypeOption)) {
            return DaoTypeOption.IN_MEMORY;
        } else if (DaoTypeOption.JDBC.getValue().equals(daoTypeOption)) {
            return DaoTypeOption.JDBC;
        } else {
            Set<String> allowedOptionValues = Arrays
                    .stream(DaoTypeOption.values())
                    .map(DaoTypeOption::getValue)
                    .collect(Collectors.toSet());
            throw new RuntimeException("Error during parsing daoType option: " + daoTypeOption + ". Allowed option values: " + allowedOptionValues + ".");
        }
    }

    private static boolean parsePopulateDBOption(CommandLine cmd) {
        return cmd.hasOption(POPULATE_DB_OPTION);
    }
}
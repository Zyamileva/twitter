package org.zyamileva.twitter.configuration.options;

import org.apache.commons.cli.Options;

public class OptionsConfiguration {
    public static void configureOptions(String[] args) {
        Options options = new Options();
        options.addRequiredOption(DaoTypeOption.DAO_TYPE_OPTION_SHORT,DaoTypeOption.DAO_TYPE_OPTION_LONG, true, DaoTypeOption.DAO_TYPE_OPTION_DESCRIPTION);

    }
}

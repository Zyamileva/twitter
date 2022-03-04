package org.zyamileva.twitter.commands.general;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.commands.Command;
import org.zyamileva.twitter.commands.CommandEnum;
import org.zyamileva.twitter.commands.CommandResponse;
import org.zyamileva.twitter.commands.NextCommands;

public class ExitCommand extends Command {
    private static final Logger log = LogManager.getLogger(ExitCommand.class);

    public ExitCommand() {
        super(CommandEnum.EXIT);
    }

    @Override
    public CommandResponse execute() {
        log.info("Shutdown twitter");
        System.exit(0);
        return null;
    }

    @Override
    public NextCommands nextCommands() {
        return null;
    }
}
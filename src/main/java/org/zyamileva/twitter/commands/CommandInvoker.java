package org.zyamileva.twitter.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.zyamileva.twitter.commands.general.ShowMainCommand;

public class CommandInvoker {
    private static final Logger log = LogManager.getLogger(CommandInvoker.class);

    public static void invoke() {
        ShowMainCommand showMainCommand = new ShowMainCommand();
        NextCommands nextCommands = showMainCommand.nextCommands();
        while (true) {
            Command command = parseCommand(nextCommands);
            System.out.println(command);
            CommandResponse response = command.execute();
            if (response.isSuccessful()) {
                System.out.println("Command executed: " + command);
                System.out.println(response.getOutput());
                nextCommands = command.nextCommands();
            } else {
                System.out.println(response.getErrors());
            }
        }
    }

    private static Command parseCommand(NextCommands nextCommands) {
        Command command = null;
        while (command == null) {
            System.out.println(nextCommands);
            int commandNumber = CommandLineReader.readInt("Please enter command number:");
            command = nextCommands.getNextCommands().get(commandNumber);
            if (command == null) {
                System.err.println("Invalid command number: " + commandNumber);
            }
        }
        return command;
    }
}
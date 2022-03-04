package org.zyamileva.twitter.commands;

public abstract class Command {
    protected final CommandEnum command;

    protected Command(CommandEnum command) {
        this.command = command;
    }

    public abstract CommandResponse execute();

    public abstract NextCommands nextCommands();

    public String toString() {
        return command.getValue();
    }
}
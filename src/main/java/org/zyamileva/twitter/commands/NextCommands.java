package org.zyamileva.twitter.commands;

import java.util.TreeMap;

import static org.zyamileva.twitter.utils.StringUtils.*;

public class NextCommands {
    private TreeMap<Integer, Command> nextCommands;

    public NextCommands(Command... commands) {
        this.nextCommands = new TreeMap<>();
        for (int i = 0; i < commands.length; i++) {
            nextCommands.put(i + 1, commands[i]);
        }
    }

    public TreeMap<Integer, Command> getNextCommands() {
        return nextCommands;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(NEW_LINE)
                .append("-----------------------------------Commands--------------------------------").append(NEW_LINE);
        nextCommands.forEach((number, command) ->
                stringBuilder.append(number).append(SPACE).append("-").append(SPACE).append(command).append(NEW_LINE));
        stringBuilder.append(SEPARATOR_LONG);
        return stringBuilder.toString();
    }
}
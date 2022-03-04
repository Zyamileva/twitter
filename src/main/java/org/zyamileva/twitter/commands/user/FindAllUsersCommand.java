package org.zyamileva.twitter.commands.user;

import org.zyamileva.twitter.commands.*;
import org.zyamileva.twitter.commands.general.ShowMainCommand;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.service.UserService;

import java.util.Set;
import java.util.UUID;

import static org.zyamileva.twitter.utils.StringUtils.NEW_LINE;
import static org.zyamileva.twitter.utils.StringUtils.SEPARATOR_SHORT;

public class FindAllUsersCommand extends Command {
    private final UserService userService = Context.getInstance().getUserService();

    public FindAllUsersCommand() {
        super(CommandEnum.FIND_ALL_USERS);
    }

    @Override
    public CommandResponse execute() {
        StringBuilder stringBuilder = new StringBuilder();
        userService
                .findAll()
                .forEach(user -> stringBuilder
                        .append("id: ").append(user.getId()).append(NEW_LINE)
                        .append(user).append(NEW_LINE)
                        .append(SEPARATOR_SHORT).append(NEW_LINE)
                );
        return new CommandResponse(true, stringBuilder.toString());
    }

    @Override
    public NextCommands nextCommands() {
        return new NextCommands(new ShowMainCommand());
    }
}
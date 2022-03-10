package org.zyamileva.twitter.commands.user;

import org.zyamileva.twitter.commands.*;
import org.zyamileva.twitter.commands.general.ShowMainCommand;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.service.UserService;

import java.util.Set;

public class FindUserByLoginCommand extends Command {
    private final UserService userService = Context.getInstance().getUserService();

    public FindUserByLoginCommand() {
        super(CommandEnum.FIND_USER_BY_LOGIN);
    }

    public CommandResponse execute() {
        String login = CommandLineReader.readLine("Please enter user login:");
        return userService.findByLogin(login)
                .map(user -> new CommandResponse(true, user.toString()))
                .orElseGet(() -> new CommandResponse(false, Set.of("User not found by login: " + login)));
    }

    @Override
    public NextCommands nextCommands() {
        return new NextCommands(new ShowMainCommand());
    }
}
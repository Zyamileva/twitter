package org.zyamileva.twitter.commands.user;

import org.zyamileva.twitter.commands.*;
import org.zyamileva.twitter.commands.general.ShowMainCommand;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.service.UserService;

import java.util.Set;
import java.util.UUID;

public class FindUserByIdCommand extends Command {
    private final UserService userService = Context.getInstance().getUserService();

    public FindUserByIdCommand() {
        super(CommandEnum.FIND_USER_BY_ID);
    }

    @Override
    public CommandResponse execute() {
        UUID id = CommandLineReader.readUUID("Please enter user id:");
        return userService.findById(id)
                .map(user -> new CommandResponse(true, user.toString()))
                .orElseGet(()->new CommandResponse(false, Set.of("User not found by id: " + id)));
    }

    @Override
    public NextCommands nextCommands() {
        return new NextCommands(new ShowMainCommand());
    }
}
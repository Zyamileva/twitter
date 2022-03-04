package org.zyamileva.twitter.commands.general;

import org.zyamileva.twitter.commands.Command;
import org.zyamileva.twitter.commands.CommandEnum;
import org.zyamileva.twitter.commands.CommandResponse;
import org.zyamileva.twitter.commands.NextCommands;
import org.zyamileva.twitter.commands.user.CreateUserCommand;
import org.zyamileva.twitter.commands.user.FindAllUsersCommand;
import org.zyamileva.twitter.commands.user.FindUserByIdCommand;

public class ShowMainCommand extends Command {

    public ShowMainCommand() {
        super(CommandEnum.MAIN_COMMANDS);
    }

    @Override
    public CommandResponse execute() {
        return new CommandResponse(true);
    }

    @Override
    public NextCommands nextCommands() {
        return new NextCommands(new CreateUserCommand(), new FindAllUsersCommand(), new FindUserByIdCommand(),new ExitCommand());
    }

}
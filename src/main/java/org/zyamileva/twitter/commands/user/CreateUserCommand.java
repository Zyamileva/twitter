package org.zyamileva.twitter.commands.user;

import org.zyamileva.twitter.commands.*;
import org.zyamileva.twitter.commands.general.ShowMainCommand;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.CreateUserResponse;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.utils.StringUtils;

public class CreateUserCommand extends Command {
    private final UserService userService = Context.getInstance().getUserService();

    public CreateUserCommand() {
        super(CommandEnum.CREATE_USER);
    }

    @Override
    public CommandResponse execute() {
        String username = CommandLineReader.readLine("Please enter username:");
        String login = CommandLineReader.readLine("Please enter login. It should star with '@'. Length between 3 and 14 characters:");
        String about = CommandLineReader.readLine("Please enter about info:");
        String location = CommandLineReader.readLine("Please enter location:");

        User user = new User(username, login);
        user.setAbout(about);
        user.setLocation(location);

        CreateUserResponse createUserResponse = userService.saveUser(user);
        if (createUserResponse.getUserOptional().isPresent()) {
            User createdUser = createUserResponse.getUserOptional().get();
            return new CommandResponse(true, "id: " + createdUser.getId() + StringUtils.NEW_LINE + createdUser);
        } else {
            return new CommandResponse(false, createUserResponse.getErrors());
        }
    }

    @Override
    public NextCommands nextCommands() {
        return new NextCommands(new ShowMainCommand());
    }
}
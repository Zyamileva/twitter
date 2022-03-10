package org.zyamileva.twitter.commands.user;

import org.zyamileva.twitter.commands.*;
import org.zyamileva.twitter.commands.general.ShowMainCommand;
import org.zyamileva.twitter.configuration.options.Context;
import org.zyamileva.twitter.entities.User;
import org.zyamileva.twitter.model.CreateUserResponse;
import org.zyamileva.twitter.service.UserService;
import org.zyamileva.twitter.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class UpdateUserCommand extends Command {
    private final UserService userService = Context.getInstance().getUserService();

    public UpdateUserCommand() {
        super(CommandEnum.UPDATE_USER);
    }

    @Override
    public CommandResponse execute() {
        String currentLogin = CommandLineReader.readLine("Please enter your login:");

        if (!userService.findByLogin(currentLogin).isEmpty()) {
            String login = CommandLineReader.readLine("Please enter new login. It should star with '@'. Length between 3 and 14 characters:");
            String about = CommandLineReader.readLine("Please enter new about info:");
            String location = CommandLineReader.readLine("Please enter new location:");

            User user = userService.findByLogin(currentLogin).orElseThrow();

            user.setLogin(login);
            user.setAbout(about);
            user.setLocation(location);

            CreateUserResponse updateUserResponse = userService.saveUser(user);
            if (updateUserResponse.getUserOptional().isPresent()) {
                User updatedUser = updateUserResponse.getUserOptional().get();
                return new CommandResponse(true, "id: " + updatedUser.getId() + StringUtils.NEW_LINE + updatedUser);
            } else {
                return new CommandResponse(false, updateUserResponse.getErrors());
            }
        } else {
            Set<String> error = new HashSet<>();
            error.add("You have entered an invalid username.");
            return new CommandResponse(false, error);
        }
    }

    @Override
    public NextCommands nextCommands() {
        return new NextCommands(new ShowMainCommand());
    }
}
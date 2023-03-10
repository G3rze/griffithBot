package com.griffith;

import com.griffith.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {


    private List<Command> commandList = new ArrayList<>();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        for (Guild guild : event.getJDA().getGuilds()) {
            for (Command command : commandList) {
                guild.upsertCommand(command.name, command.description).addOptions(command.args).queue();
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        for (Command command : commandList) {
            if (command.name.equals(event.getName())) {
                command.execute(event);
                return;
            }
        }
    }

    public void add(Command command) {
        commandList.add(command);
    }
}

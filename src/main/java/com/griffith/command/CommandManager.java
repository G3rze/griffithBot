package com.griffith.command;

import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandManager extends ListenerAdapter {

  private List<OptionCommand> optionCommandList = new ArrayList<>();
  private List<SimpleCommand> simpleCommandList = new ArrayList<>();

  @Override
  public void onReady(@NotNull ReadyEvent event) {
    for (Guild guild : event.getJDA().getGuilds()) {
      for (OptionCommand optionCommand : optionCommandList) {
        guild
            .upsertCommand(optionCommand.name, optionCommand.description)
            .addOptions(optionCommand.args)
            .queue();
      }
      for (SimpleCommand simpleCommand : simpleCommandList) {
        guild.upsertCommand(simpleCommand.name, simpleCommand.description).queue();
      }
    }
  }

  @Override
  public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
    for (OptionCommand optionCommand : optionCommandList) {
      if (optionCommand.name.equals(event.getName())) {
        optionCommand.execute(event);
        return;
      }
    }
    for (SimpleCommand simpleCommand : simpleCommandList) {
      if (simpleCommand.name.equals(event.getName())) {
        simpleCommand.execute(event);
        return;
      }
    }
  }

  public void add(OptionCommand optionCommand) {
    optionCommandList.add(optionCommand);
  }

  public void add(SimpleCommand simpleCommand) {
    simpleCommandList.add(simpleCommand);
  }
}

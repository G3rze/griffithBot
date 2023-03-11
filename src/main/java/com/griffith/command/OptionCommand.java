package com.griffith.command;

import com.griffith.bot.Builder;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public abstract class OptionCommand {
  public Builder bot;
  public String name;
  public String description;
  public List<OptionData> args;
  public Permission permission;
  public Permission botPermission;

  protected OptionCommand(Builder bot) {
    this.bot = bot;
    this.args = new ArrayList<>();
  }

  public abstract void execute(SlashCommandInteraction event);
}

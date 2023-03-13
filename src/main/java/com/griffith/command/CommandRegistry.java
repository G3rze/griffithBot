package com.griffith.command;

import com.griffith.bot.Builder;
import com.griffith.command.management.GiveRole;
import com.griffith.command.management.NewRole;
import com.griffith.command.music.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandRegistry extends ListenerAdapter {
  CommandManager manager = new CommandManager();

  public CommandManager register(Builder bot) {
    manager.add(new NewRole(bot));
    manager.add(new GiveRole(bot));
    manager.add(new Play(bot));
    manager.add(new Skip(bot));
    manager.add(new Stop(bot));
    manager.add(new Leave(bot));
    manager.add(new Loop(bot));

    return manager;
  }
}

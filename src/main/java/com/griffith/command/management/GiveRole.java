package com.griffith.command.management;

import com.griffith.bot.Builder;
import com.griffith.command.OptionCommand;
import java.util.ArrayList;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class GiveRole extends OptionCommand {

  public GiveRole(Builder bot) {
    super(bot);
    this.name = "give-role";
    this.description = "Gives a role to a guild member!";
    this.permission = Permission.ADMINISTRATOR;
    this.botPermission = Permission.ADMINISTRATOR;
    this.args = new ArrayList<>();

    args.add(new OptionData(OptionType.USER, "name", "Select a member to assign a role", true));
    args.add(new OptionData(OptionType.ROLE, "role", "Select the role you want to assign", true));
  }

  @Override
  public void execute(SlashCommandInteraction event) {
    Member member = event.getOption("name").getAsMember();
    Role role = event.getOption("role").getAsRole();

    if (event.getMember().getPermissions().contains(permission)) {
      if (member.getRoles().contains(role)) {
        event.reply("This user has already this role!").queue();
      } else {
        event
            .getGuild()
            .addRoleToMember(member, event.getGuild().getRoleById(role.getId()))
            .queue();
        event
            .reply(
                "The role "
                    + role.getAsMention()
                    + " has been assigned to "
                    + member.getAsMention())
            .queue();
      }
    } else {
      event.reply("You don't have the permission to do this!").setEphemeral(true).queue();
    }
  }
}

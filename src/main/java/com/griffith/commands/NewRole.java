package com.griffith.commands;

import com.griffith.GriffithBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.awt.*;
import java.util.ArrayList;


public class NewRole extends Command {
    public NewRole(GriffithBot bot) {
        super(bot);
        this.name = "new-role";
        this.description = "Creates new roles!";
        this.permission = Permission.ADMINISTRATOR;
        this.botPermission = Permission.ADMINISTRATOR;
        this.args = new ArrayList<>();

        args.add(new OptionData(OptionType.STRING, "role", "The name of the role that you want to create", true));
        args.add(new OptionData(OptionType.STRING, "permission", "Permissions of the role")
                .addChoice("Admin", "admin")
                .addChoice("Mod", "mod"));
    }

    @Override
    public AuditableRestAction<Void> execute(SlashCommandInteraction event) {

        OptionMapping role = event.getOption("role");
        String name = role.getAsString();
        if (event.getMember().getPermissions().contains(permission)) {
            if (!event.getGuild().getRoles().contains(role)) {
                if (event.getOption("permission") != null) {
                    OptionMapping permission = event.getOption("permission");
                    String type = permission.getAsString();
                    switch (type.toLowerCase()) {
                        case "admin" -> {
                            Guild guild = event.getGuild();
                            guild.createRole()
                                    .setName(String.valueOf(name))
                                    .setHoisted(true)
                                    .setMentionable(false)
                                    .setPermissions(Permission.ADMINISTRATOR)
                                    .setColor(Color.YELLOW)
                                    .queue();
                        }
                        case "mod" -> {
                            Guild guild = event.getGuild();
                            guild.createRole()
                                    .setName(String.valueOf(name))
                                    .setHoisted(true)
                                    .setMentionable(false)
                                    .setColor(Color.CYAN)
                                    .setPermissions(Permission.MODERATE_MEMBERS
                                            , Permission.BAN_MEMBERS
                                            , Permission.KICK_MEMBERS
                                            , Permission.VOICE_MUTE_OTHERS)
                                    .queue();
                        }
                        default -> {
                            break;
                        }
                    }
                } else {
                    Guild guild = event.getGuild();
                    guild.createRole()
                            .setName(String.valueOf(name))
                            .setHoisted(true)
                            .setMentionable(true)
                            .queue();
                }
                event.reply("The new " + name + " role was created!").queue();
            } else {
                event.reply("This role already exist!").queue();
            }
        } else {
            event.reply("You don't have the permission to do this!").setEphemeral(true).queue();
        }
        return null;
    }


}

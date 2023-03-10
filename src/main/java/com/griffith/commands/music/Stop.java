package com.griffith.commands.music;

import com.griffith.GriffithBot;
import com.griffith.commands.SimpleCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import com.griffith.lavaplayer.TrackScheculer;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class Stop extends SimpleCommand {

    public Stop(GriffithBot bot) {
        super(bot);
        this.name = "stop";
        this.description = "Stop the current song!";
        this.permission = Permission.MESSAGE_SEND;
        this.botPermission = Permission.VOICE_SPEAK;
    }

    @Override
    public void execute(SlashCommandInteraction event) {
        InChannelChecker checker = new InChannelChecker();

        checker.inChannerlChecker(event);

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        TrackScheculer trackScheculer = guildMusicManager.getTrackScheculer();
        trackScheculer.getQueue().clear();
        trackScheculer.getPlayer().stopTrack();

        event.reply("Stopped, the queue is clear now!").queue();
    }
}

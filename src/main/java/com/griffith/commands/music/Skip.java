package com.griffith.commands.music;

import com.griffith.GriffithBot;
import com.griffith.commands.OptionCommand;
import com.griffith.commands.SimpleCommand;
import com.griffith.lavaplayer.GuildMusicManager;
import com.griffith.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

public class Skip extends SimpleCommand {

    public Skip(GriffithBot bot) {
        super(bot);
        this.name = "skip";
        this.description = "Skip the current song!";
        this.permission = Permission.MESSAGE_SEND;
        this.botPermission = Permission.VOICE_SPEAK;
    }

    @Override
    public void execute(SlashCommandInteraction event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            event.reply("You must be in a voice channel!").queue();
            return;
        }

        Member selfBot = event.getGuild().getSelfMember();
        GuildVoiceState selfBotVoiceState = selfBot.getVoiceState();

        if(!selfBotVoiceState.inAudioChannel()){
            event.reply("I'm not in an audio channel!").queue();
            return;
        }

        if(selfBotVoiceState.getChannel() != memberVoiceState.getChannel()){
            event.reply("You're not in the same channel as me!").queue();
            return;
        }

        GuildMusicManager guildMusicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
        guildMusicManager.getTrackScheculer().getPlayer().stopTrack();

        event.reply("Skipped").queue();
    }
}

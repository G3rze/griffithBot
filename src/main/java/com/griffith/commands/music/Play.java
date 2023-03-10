package com.griffith.commands.music;

import com.griffith.GriffithBot;
import com.griffith.commands.OptionCommand;
import com.griffith.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;

public class Play extends OptionCommand {

    public Play(GriffithBot bot) {
        super(bot);
        this.name = "play";
        this.description = "Plays some music!";
        this.permission = Permission.MESSAGE_SEND;
        this.botPermission = Permission.VOICE_SPEAK;
        this.args = new ArrayList<>();

        args.add(new OptionData(OptionType.STRING, "url", "The URL of the song that you want to play", true));
    }

    @Override
    public void execute(SlashCommandInteraction event) {
        Member member = event.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            event.reply("You must be in a voice channel").queue();
            return;
        }

        Member selfBot = event.getGuild().getSelfMember();
        GuildVoiceState selfBotVoiceState = selfBot.getVoiceState();

        if(!selfBotVoiceState.inAudioChannel()) {
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
            event.reply("Connecting to '\uD83D\uDD0A' " + memberVoiceState.getChannel().getAsMention()).queue();

        } else {
            if(selfBotVoiceState.getChannel() != memberVoiceState.getChannel()){
                event.reply("You must be in the same channel with me").queue();
                return;
            }
        }
        PlayerManager playerManager = PlayerManager.getInstance();
        playerManager.play(event.getGuild(), event.getOption("url").getAsString());

        event.reply("Next song added to the queue!").queue();


        return;
    }
}

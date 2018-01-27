/*
 * ShaBo - Discord BOT
 * Copyright (C) 2018 - ji2L
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package shabo.command;

import java.util.Arrays;

import javax.annotation.Nonnull;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import shabo.Config;
import shabo.command.abs.Command;

public class CommandContext {

	private final Guild guild;
	private final TextChannel textChannel;
	private final Member invoker;
	private final Message message;
	
	private String[] args;
	private Command command;
	private String trigger;
	
	private CommandContext(@Nonnull Guild guild, @Nonnull TextChannel textChannel, @Nonnull Member invoker, @Nonnull Message message) {
		this.guild = guild;
		this.textChannel = textChannel;
		this.invoker = invoker;
		this.message = message;
	}
	
	public static CommandContext parse(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        TextChannel textChannel = event.getTextChannel();
        Member member = event.getMember();
        Message message = event.getMessage();
        
        String prefix = Config.CONFIG.getPrefix();
        String rawMessage = message.getContentRaw();
        if(rawMessage.startsWith(prefix))
        	rawMessage = rawMessage.substring(prefix.length());
        
        //use \p{javaSpaceChar} because it includes unicode whitespace, add + to skip multiple whitespaces
        String[] args = rawMessage.split("\\p{javaSpaceChar}+");
        
        if(args.length < 1)
        	return null;
        
        String trigger = args[0];
        
        Command command = CommandRegistry.findCommand(trigger);
        
        if(command == null) {
        	System.err.println("Couldn't find command " + trigger);
        	return null;
        }
        
        CommandContext commandContext = new CommandContext(guild, textChannel, member, message);
        commandContext.setArgs(Arrays.copyOfRange(args, 1, args.length));
        commandContext.setCommand(command);
        commandContext.setTrigger(trigger);
        
        return commandContext;
	}
	
	public void setArgs(String[] args) {
		this.args = args;
	}
	
	public void setCommand(Command command) {
		this.command = command;
	}
	
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public Guild getGuild() {
		return guild;
	}

	public TextChannel getTextChannel() {
		return textChannel;
	}

	public Member getInvoker() {
		return invoker;
	}

	public Message getMessage() {
		return message;
	}

	public String[] getArgs() {
		return args;
	}

	public Command getCommand() {
		return command;
	}

	public String getTrigger() {
		return trigger;
	}
}

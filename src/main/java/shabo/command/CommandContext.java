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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import shabo.Config;
import shabo.command.abs.Command;

/**
 * This class represents the context of a command.
 * A context gathers info about the guild and the text channel in which the command was sent, as well as who sent it and the message itself.
 * It is used to parse a message to extract the arguments of a command and find the right command in the registry.
 * 
 * @author ji2L
 */
public class CommandContext {
	
	private static final Logger logger = LoggerFactory.getLogger(CommandContext.class);

	private final Guild guild;				//The Guild that this message was sent in. (Guilds are Servers)
	private final TextChannel textChannel;	//The TextChannel that this message was sent to.
	private final Member invoker;			//The Member that sent the message.
	private final Message message;			//The message that was received.
	
	private String[] args;					//The arguments of the command (excluding its name).
	private Command command;				//The corresponding command in the registry.
	private String trigger;					//The trigger (name) of the command.
	
	private CommandContext(@Nonnull Guild guild, @Nonnull TextChannel textChannel, @Nonnull Member invoker, @Nonnull Message message) {
		this.guild = guild;
		this.textChannel = textChannel;
		this.invoker = invoker;
		this.message = message;
	}
	
	/**
	 * Parses a received message to extract the neccessary information to create a CommandContext (guild, text channel, etc.)
	 * 
	 * @param event - the event to parse
	 * @return A command context for the event or null if the command doesn't exist
	 */
	public static CommandContext parse(MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        TextChannel textChannel = event.getTextChannel();
        Member member = event.getMember();
        Message message = event.getMessage();
        
        String prefix = Config.CONFIG.getPrefix();
        String rawMessage = message.getContentRaw();
        if(rawMessage.startsWith(prefix))
        	rawMessage = rawMessage.substring(prefix.length());
        else
        	return null; //Not a command
        
        //use \p{javaSpaceChar} because it includes unicode whitespace, add + to skip multiple whitespaces
        String[] args = rawMessage.split("\\p{javaSpaceChar}+");
        
        if(args.length < 1)
        	return null;
        
        String trigger = args[0];
        
        Command command = CommandRegistry.findCommand(trigger);
        
        if(command == null) {
        	System.err.println("Couldn't find command " + trigger);
        	logger.error("Couldn't find command {}" + trigger);
        	return null;
        }
        
        CommandContext commandContext = new CommandContext(guild, textChannel, member, message);
        commandContext.setArgs(Arrays.copyOfRange(args, 1, args.length));
        commandContext.setCommand(command);
        commandContext.setTrigger(trigger);
        
        return commandContext;
	}
	
	private void setArgs(String[] args) {
		this.args = args;
	}
	
	private void setCommand(Command command) {
		this.command = command;
	}
	
	private void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	/**
	 * @return The guild the message was sent in
	 */
	public Guild getGuild() {
		return guild;
	}

	/**
	 * @return The text channel the message was sent in
	 */
	public TextChannel getTextChannel() {
		return textChannel;
	}

	/**
	 * @return The member who sent the message
	 */
	public Member getInvoker() {
		return invoker;
	}

	/**
	 * @return The message that was sent
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @return The arguments of the command, excluding its name
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @return The corresponding command in the command registry
	 */
	public Command getCommand() {
		return command;
	}

	/**
	 * @return The name (trigger) of the command
	 */
	public String getTrigger() {
		return trigger;
	}
}

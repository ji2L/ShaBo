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

package shabo.event;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import shabo.command.CommandContext;
import shabo.command.CommandManager;

/**
 * This class contains the event listeners used by the bot.
 * 
 * @author ji2L
 */
public class EventListeners extends ListenerAdapter {
	
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        String msg = message.getContentDisplay();		//A human readable version of the Message.

        boolean bot = author.isBot();

        if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
        {
        	//Guild specific information
            Guild guild = event.getGuild();             //The Guild that this message was sent in. (Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //The Member that sent the message.

            String name;
            if (message.isWebhookMessage())
                name = author.getName();                //If this is a Webhook message, then there is no Member associated with the User, thus we default to the author for name.
            else
                name = member.getEffectiveName();       //This will either use the Member's nickname if they have one, otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
            
            if(!bot) {
            	CommandContext commandContext = CommandContext.parse(event);
            	
            	if(commandContext == null)
            		return;
            	
            	executeCommand(commandContext);
            }
        }
        else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {
            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
        }
        else if (event.isFromType(ChannelType.GROUP))   //If this message was sent to a Group. This is CLIENT only!
        {
            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";  //A group name can be null due to it being unnamed.

            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), msg);
        }
    }
    
    /**
     * Sends the command context to the command manager to execute the command.
     * 
     * @param commandContext - the context of the command to be executed
     */
    public void executeCommand(CommandContext commandContext) {
    	CommandManager.commandCalled(commandContext);
    }
}

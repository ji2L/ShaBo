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

package shabo.command.util_commmands;

import java.util.Random;

import net.dv8tion.jda.core.entities.TextChannel;

import shabo.command.CommandContext;
import shabo.command.abs.Command;

public class RollCommand extends Command {

	/**
	 * Constructor of the roll command.
	 * 
	 * @param name - name of the command
	 * @param aliases - aliases of the command
	 */
	public RollCommand(String name, String... aliases) {
		super(name, aliases);
	}

	/**
	 * Invokes the command.
	 */
	@Override
	public void invoke(CommandContext commandContext) {
        Random rand = new Random();
        int roll = rand.nextInt(6) + 1;
        
        TextChannel textChannel = commandContext.getTextChannel();
        textChannel.sendMessage("Your roll: " + roll).queue(sentMessage ->
        	{
        		if (roll < 3)
        			textChannel.sendMessage("The roll for messageId: " + sentMessage.getId() + " wasn't very good... Must be bad luck!\n").queue();
        	}
        );	
	}

	@Override
	public String help() {
		return "Roll a d6.";
	}

}

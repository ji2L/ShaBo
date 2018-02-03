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

import net.dv8tion.jda.core.entities.TextChannel;
import shabo.command.CommandContext;
import shabo.command.CommandRegistry;
import shabo.command.abs.Command;

public class HelpCommand extends Command {

	/**
	 * Constructor of the help command.
	 * 
	 * @param name - name of the command
	 * @param aliases - aliases of the command
	 */
	public HelpCommand(String name, String... aliases) {
		super(name, aliases);
	}
	
	// Creates a string containing the help string of every registerd commands
	private String createHelp() {
		String help = "```";
		
		for(Command c : CommandRegistry.getAllCommands()) {
			help += c.getName();
			
			if(c.getAliases().size() > 0) {
				help += " (aliases : ";
				for(int i = 0; i < c.getAliases().size(); i++) {
					help += c.getAliases().get(i);
					if(i < c.getAliases().size() - 1)
						help += ", ";
					else
						help += " ";
				}
				
				help += ")";
			}
			
			help += " : " + c.help() + "\n";
		}
		
		return help + "```";
	}

	@Override
	public void invoke(CommandContext commandContext) {
		TextChannel textChannel = commandContext.getTextChannel();
		textChannel.sendMessage(createHelp()).queue();
	}

	@Override
	public String help() {
		return "Print this message.";
	}

}

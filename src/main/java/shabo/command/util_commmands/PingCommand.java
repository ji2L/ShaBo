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
import shabo.command.abs.Command;

public class PingCommand extends Command {

	public PingCommand(String name, String... aliases) {
		super(name, aliases);
	}

	@Override
	public void invoke(CommandContext commandContext) {
		TextChannel textChannel = commandContext.getTextChannel();
		
		textChannel.sendMessage("pong!").queue();
	}

	@Override
	public String help() {
		return "For every !ping, the bot answers with pong!";
	}

}

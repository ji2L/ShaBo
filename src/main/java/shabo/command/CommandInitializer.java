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

import shabo.command.util_commmands.PingCommand;
import shabo.command.util_commmands.RollCommand;

/**
 * This class adds every command and their aliases to the command registry.
 * 
 * @author ji2L
 *
 */
public class CommandInitializer {

	/**
	 * Statically initializes commands.
	 */
	public static void initCommands() {
		CommandRegistry commandRegistry = new CommandRegistry();
		commandRegistry.registerCommand(new PingCommand("ping"));
		commandRegistry.registerCommand(new RollCommand("roll", "r"));
	}
}

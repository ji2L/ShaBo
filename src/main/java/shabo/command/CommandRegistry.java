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

import java.util.HashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import shabo.command.abs.Command;

/**
 * This class is a registry which maps every command name and aliases to actual commands.
 * 
 * @author ji2L
 */
public class CommandRegistry {
	
	private static CommandRegistry commandRegistry;
	private HashMap<String, Command> registry = new HashMap<String, Command>();

	public CommandRegistry() {
		commandRegistry = this;
	}
	
	/**
	 * Adds a command to the registry, creating an entry for each alias.
	 * 
	 * @param command - the command to add to the registry
	 */
	public void registerCommand(@Nonnull Command command) {
		String name = command.getName().toLowerCase();
		registry.put(name, command);
		
		for(String alias : command.getAliases())
			registry.put(alias, command);
	}
	
	/**
	 * Returns a command from the registry.
	 * 
	 * @param name - the name of the command to get
	 * @return The command corresponding to name or null if no entry was found
	 */
	public Command getCommand(@Nonnull String name) {
		return registry.get(name);
	}
	
	/**
	 * Statically finds a command in the registry.
	 * 
	 * @param name - the name of the command to find
	 * @return The command corresponding to name or null if no entry was found
	 */
	@Nullable
	public static Command findCommand(@Nonnull String name) {
		return commandRegistry.getCommand(name);
	}
}

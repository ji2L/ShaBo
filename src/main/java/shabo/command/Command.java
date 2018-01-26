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
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Abstract class to describe a command.
 * 
 * @author ji2L
 */
public abstract class Command implements ICommand {
	
	private String name;
	private List<String> aliases;
	
	/**
	 * Command constructor.
	 * 
	 * @param name - The default name of the command (cannot be null)
	 * @param aliases - An arbitrary number of aliases for the command
	 */
	public Command(@Nonnull String name, String... aliases) {
		this.name = name;
		this.aliases = Arrays.asList(aliases);
	}
	
	/**
	 * @return The name of the command
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The aliases of the command
	 */
	public List<String> getAliases() {
		return aliases;
	}
}

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

package shabo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to load the bot's credentials.
 * 
 * @author ji2L
 */
public class Config {
	
	private final String botToken;
	
	/**
	 * Creates a new Config, given the name of the file to read the bot's token from.
	 * 
	 * @param tokenFile - the path to the file containing the bot's token
	 */
	public Config(String tokenFile) {
		botToken = loadTokenFromFile(tokenFile);
	}
	
	/**
	 * Loads the bot's token, given the path to the file to read from.
	 * 
	 * @param fileName - the path to the single line file containing the bot's token
	 * @return The bot's token or an empty String if it could not be loaded
	 */
	public String loadTokenFromFile(String fileName) {
		String token = "";
		
		//Use try-with-resources to auto-close the buffer
		try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			token = br.readLine();
		} catch(FileNotFoundException NFe) {
			System.err.println("Could not open " + fileName);
		} catch(IOException IOe) {
			System.err.println("Could not read token");
		}
		
		return token;
	}
	
	/**
	 * @return The bot's token
	 */
	public String getBotToken() {
		return botToken;
	}
}

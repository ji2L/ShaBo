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

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import shabo.command.CommandInitializer;
import shabo.event.EventListeners;

public class ShaBo extends ListenerAdapter
{
    public static void main(String[] args)
    {
    	CommandInitializer.initCommands();
    	
        try {
            JDA jda = new JDABuilder(AccountType.BOT)
                    		.setToken(Config.CONFIG.getBotToken())
                    		.addEventListener(new EventListeners())
                    		.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
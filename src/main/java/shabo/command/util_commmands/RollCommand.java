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

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Pattern;

import net.dv8tion.jda.core.entities.TextChannel;

import shabo.command.CommandContext;
import shabo.command.abs.Command;
import shabo.util.InvalidRollExpressionException;

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
	
	// Regroups the arguments of the command in order to obtain a single, whitespace free string
	private String regroupArgs(String[] args) {
		String res = "";
		
		for(String a : args) res += a.toLowerCase();

		return res;
	}
	
	// Parses a roll expression and creates a list of subexpressions
	private ArrayList<Roll> parseRoll(String rollExpression) throws InvalidRollExpressionException {
		String[] parts = rollExpression.split("\\+");
		ArrayList<Roll> res = new ArrayList<Roll>();
		
		String modifier = "(\\d+)";
		String roll = "d(\\d+)";
		
		Pattern modifierPattern = Pattern.compile("^" + modifier + "$");
		Pattern rollPattern = Pattern.compile("^" + roll + "$");
		Pattern multRollPattern = Pattern.compile("^" + modifier + roll + "$");

		for(String s : parts) {
			if(modifierPattern.matcher(s).matches()) {
				res.add(new Roll(0, 0, Integer.parseInt(s)));
			}
			else if(rollPattern.matcher(s).matches()) {
				String dice = s.substring(1, s.length());
				res.add(new Roll(1, Integer.parseInt(dice), 0));
			}
			else if(multRollPattern.matcher(s).matches()) {
				String[] multRoll = s.split("d");
				res.add(new Roll(Integer.parseInt(multRoll[0]), Integer.parseInt(multRoll[1]), 0));
			}
			else
				throw new InvalidRollExpressionException();
		}

		return res;
	}
	
	// Executes each subexpression of a roll
	private void executeRoll(ArrayList<Roll> parts) {
		Random rand = new Random();
		for(Roll r : parts) {
			if(r.modifier != 0) // this roll is only a modifier (i.e. : +2)
				r.result.add(r.modifier);
			else {				// this roll is an actual roll (i.e. : d20 or 3d10)
				for(int i = 0; i < r.mult; i++)
					r.result.add(rand.nextInt(r.dice) + 1);
			}
		}
	}
	
	// Creates the string that will be displayed
	private String rollToString(ArrayList<Roll> parts) {
		String res = "```";
		int total = 0;
		
		boolean isFirstItr = true;
		for(Roll r : parts) {
			if(r.modifier != 0) {
				if(isFirstItr) {
					res += r.modifier;
					total += r.modifier;
					isFirstItr = false;
				}
				else {
					res += " + " + r.modifier;
					total += r.modifier;
				}
			}
			else {
				if(r.mult == 1) {	// this is a simple roll (i.e. : d20)
					if(isFirstItr) {
						res += r.result.get(0);
						total += r.result.get(0);
						isFirstItr = false;
					}
					else {
						res += " + " + r.result.get(0);
						total += r.result.get(0);
					}
				}
				else {				// this is a multRoll (i.e. : 4d6)
					if(isFirstItr) {
						res += "( ";
						isFirstItr = false;
					}
					else
						res += " + ( ";
						
					for(int i = 0; i < r.result.size(); i++) {
						if(i == r.result.size() - 1)
							res += r.result.get(i) + " ";
						else
							res += r.result.get(i) + " + ";
						
						total += r.result.get(i);
					}
						
					res += ")";
				}
			}
		}
	
		if(parts.size() > 1 || (parts.size() == 1 && parts.get(0).mult > 1))
			res += " = " + total;
		
		res += "```";
			
		return res;
	}

	@Override
	public void invoke(CommandContext commandContext) {
        TextChannel textChannel = commandContext.getTextChannel();
        
        if(commandContext.getArgs().length == 0 || "help".equals(commandContext.getArgs()[0])) {
        	textChannel.sendMessage(help()).queue();
        	return;
        }
        
        try {
        	ArrayList<Roll> roll = parseRoll(regroupArgs(commandContext.getArgs()));
        	executeRoll(roll);
        	String res = rollToString(roll);
        	textChannel.sendMessage(res).queue();
        } catch(InvalidRollExpressionException e) {
        	textChannel.sendMessage("Invalid roll expression, use the following format : ```XdY+Z```\n").queue();
        }
	}

	@Override
	public String help() {
		return "Roll dice according to an expression following the format : ```XdY+Z```\n";
	}
	
	// Inner class which represents a roll
	class Roll {
		private int mult;
		private int dice;
		private int modifier;
		private ArrayList<Integer> result;
		
		public Roll(int mult, int dice, int modifier) {
			this.mult = mult;
			this.dice = dice;
			this.modifier = modifier;
			this.result = new ArrayList<Integer>();
		}
	}
}

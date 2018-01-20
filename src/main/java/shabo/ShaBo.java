package shabo;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class ShaBo extends ListenerAdapter
{
    public static void main(String[] args)
    {
    	BufferedReader in;
    	String token = "";
    	
		try {
			in = new BufferedReader(new FileReader("token.txt"));
			token = in.readLine();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

        try {
            JDA jda = new JDABuilder(AccountType.BOT)
                    		.setToken(token)
                    		.addEventListener(new ShaBo())
                    		.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        JDA jda = event.getJDA();
        long responseNumber = event.getResponseNumber();

        //Event specific information
        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //The MessageChannel that the message was sent to.

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
            {
                name = author.getName();                //If this is a Webhook message, then there is no Member associated
            }                                           // with the User, thus we default to the author for name.
            else
            {
                name = member.getEffectiveName();       //This will either use the Member's nickname if they have one,
            }                                           // otherwise it will default to their username. (User#getName())

            System.out.printf("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
        }
        else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {
            PrivateChannel privateChannel = event.getPrivateChannel();

            System.out.printf("[PRIV]<%s>: %s\n", author.getName(), msg);
        }
        else if (event.isFromType(ChannelType.GROUP))   //If this message was sent to a Group. This is CLIENT only!
        {
            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";  //A group name can be null due to it being unnamed.

            System.out.printf("[GRP: %s]<%s>: %s\n", groupName, author.getName(), msg);
        }

        if (msg.equals("!ping")) //Command : !ping/pong!
        {
            channel.sendMessage("pong!").queue();
        }
        else if (msg.equals("!roll")) //Command : !roll
        {
            Random rand = new Random();
            int roll = rand.nextInt(6) + 1;
            channel.sendMessage("Your roll: " + roll).queue(sentMessage ->
            	{
            		if (roll < 3)
            			channel.sendMessage("The roll for messageId: " + sentMessage.getId() + " wasn't very good... Must be bad luck!\n").queue();
            	}
            );
        }
    }
}
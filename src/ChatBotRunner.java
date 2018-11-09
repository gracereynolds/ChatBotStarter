import java.util.Scanner;
//made by Grace Reynolds, Vincent Tran, Karen Wu

/**
 * A simple class to run our chatbot teams.
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBotRunner
{

	/**
	 * Create instances of each chatbot, give it user input, and print its replies. Switch chatbot responses based on which chatbot the user is speaking too.
	 */
	public static void main(String[] args)
	{
		ChatBot1 chatbot1 = new ChatBot1();
		ChatBot2 chatbot2 = new ChatBot2();
		ChatBot3 chatbot3 = new ChatBot3();
		

		Scanner in = new Scanner (System.in);
		System.out.println("Welcome to the Museum of Science Fiction T.V. shows!");
		System.out.println("Which character would you like to chat with?");
		System.out.println("To choose who to chat with, type 'Fives', 'Spock', or 'Barry'");
		String statement = in.nextLine();


		while (!statement.equals("Bye"))
		{
			if (statement.equals("Spock"))
			{
				chatbot1.chatLoop(statement);
			}
			else if (statement.equals("Fives"))
			{
				chatbot3.chatLoop(statement);
			}
			else if (statement.equals("Barry"))
			{
				chatbot2.chatLoop(statement);
			}


			statement = in.nextLine();


		}
	}

}

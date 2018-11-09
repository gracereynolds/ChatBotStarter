import java.util.Random;
import java.util.Scanner;
//made by Grace Reynolds

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Brooklyn Tech CS Department
 * @version September 2018
 */
public class ChatBot1
{
	//emotion can alter the way our bot responds. Emotion can become more negative or positive over time.
	int emotion = 0;

	/**
	 * Runs the conversation for this particular chatbot, should allow switching to other chatbots.
	 * @param statement the statement typed by the user
	 */
	public void chatLoop(String statement)
	{
		Scanner in = new Scanner (System.in);
		System.out.println (getGreeting());


		while (!statement.equals("Bye"))
		{


			statement = in.nextLine();
			//getResponse handles the user reply
			System.out.println(getResponse(statement));


		}

	}
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Greetings, I am Spock. What would you like to discuss?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		{
			if (emotion > -5)
			{
				response = "You seem very quiet. Is there anything you wish to say?";
			}
			else {
				response = "Is there a reason you are here, or are you just going to stand there?";
			}
		}
		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Are you being deliberately obtuse?";
                	emotion--;
		}
		else if (findKeyword(statement, "folwell") >= 0)
		{
			if(emotion > -5)
			{
				response = "Mr Folwell is a very logical thinker.";
			}
			else {
				response = "Mr Folwell is extremely logical, for a human.";
			}
			emotion++;
		}
		else if (findKeyword(statement, "kirk") >= 0)
		{
			if(emotion >= 5)
			{
				response = "The captain is surprisingly logical, a talented and brave leader, and exceedingly loyal, even if he does seem far too concerned with the doings of women.";
			}
			else if (emotion > -5)
			{
				response = "Captain Kirk and Dr Mccoy are currently on an away mission, and have not yet reported back.";
			}
			else {
				response = "The captain is on an away mission, not that it's any of your business.";
			}
			emotion++;
		}
		else if (findKeyword(statement, "mccoy") >= 0)
		{
			if(emotion >= 10)
			{
				response = "Dr Mccoy is extremely, and oftentimes deliberately illogical, but he has his finer points.";
				emotion++;
			}
			else if (emotion > -5)
			{
				response = "Captain Kirk and Dr Mccoy are currently on an away mission, and have not yet reported back.?";
				emotion++;
			}
			else {
				response = "The doctor is one of the most unreasonable and illogical creatures I've ever met.";
				emotion--;
			}
		}
		else if (findKeyword(statement, "green-blooded devil") >= 0)
		{
			response = "I find I prefer being a 'green-blooded devil' than a human like you.";
			emotion-=5;
		}

		// Response transforming I want to statement
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}
		else if (findKeyword(statement, "I want",0) >= 0)
		{
			response = transformIWantStatement(statement);
		}
		else if (findKeyword(statement, "I") >= 0 && findKeyword(statement, "you") >= 0)
		{
			response = transformIYouStatement(statement);
		}
		else if (findKeyword(statement, "It is") >= 0 && findKeyword(statement, "out") >= 0)
		{
			response = transformItIsOutStatement(statement);
		}
		else
		{
			response = getRandomResponse();
		}
		if (emotion <= -20)
		{
			response = "*Everyone has a limit before they snap. You just found the limit of a super strong alien*";
		}
		
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "Why do you want to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want to " + restOfStatement + "?";
	}

	
	/**
	 * Take a statement with "I want <something>." and transform it into 
	 * "Would you really be happy if you had <something>?"
	 * @param statement the user statement, assumed to contain "I want"
	 * @return the transformed statement
	 */
	private String transformIWantStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want", 0);
		String restOfStatement = statement.substring(psn + 6).trim();
		return "Would it really please you to have " + restOfStatement + "?";
	}
	
	
	/**
	 * Take a statement with "I <something> you" and transform it into 
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIYouStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfI = findKeyword (statement, "I", 0);
		int psnOfYou = findKeyword (statement, "you", psnOfI);
		
		String restOfStatement = statement.substring(psnOfI + 1, psnOfYou).trim();
		return "Why do you " + restOfStatement + " me?";
	}

	/**
	 * Take a statement with "It is <something> out and return a transformed statement based on mood.
	 * @param statement the user statement, assumed to contain "It is <something> out"
	 * @return the transformed statement
	 */
	private String transformItIsOutStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}

		int psnOfI = findKeyword (statement, "It is", 0);
		int psnOfYou = findKeyword (statement, "out", psnOfI);

		String restOfStatement = statement.substring(psnOfI + 5, psnOfYou).trim();
		if (emotion <= -5)
		{
			return "Did you think I was somehow unaware it is" + restOfStatement +" out, or do you think discussing the weather will somehow change it? Either way, illogical.";
		}
		else
		{
			return "How long do you think it will stay " + restOfStatement +"?";
		}
	}

	
	
	/**
	 * Search for one word in phrase. The search is not case
	 * sensitive. This method will check that the given goal
	 * is not a substring of a longer string (so, for
	 * example, "I know" does not contain "no").
	 *
	 * @param statement
	 *            the string to search
	 * @param goal
	 *            the string to search for
	 * @param startPos
	 *            the character of the string to begin the
	 *            search at
	 * @return the index of the first occurrence of goal in
	 *         statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal,
			int startPos)
	{
		String phrase = statement.trim().toLowerCase();
		goal = goal.toLowerCase();

		// The only change to incorporate the startPos is in
		// the line below
		int psn = phrase.indexOf(goal, startPos);

		// Refinement--make sure the goal isn't part of a
		// word
		while (psn >= 0)
		{
			// Find the string of length 1 before and after
			// the word
			String before = " ", after = " ";
			if (psn > 0)
			{
				before = phrase.substring(psn - 1, psn);
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(
						psn + goal.length(),
						psn + goal.length() + 1);
			}

			// If before and after aren't letters, we've
			// found the word
			if (((before.compareTo("a") < 0) || (before
					.compareTo("z") > 0)) // before is not a
											// letter
					&& ((after.compareTo("a") < 0) || (after
							.compareTo("z") > 0)))
			{
				return psn;
			}

			// The last position didn't work, so let's find
			// the next, if there is one.
			psn = phrase.indexOf(goal, psn + 1);

		}

		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse ()
	{
		Random r = new Random ();
		if (emotion <= -5)
		{	
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}
		if (emotion > 10)
		{	
			return randomHappyResponses [r.nextInt(randomHappyResponses.length)];
		}	
		return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];
	}
	
	private String [] randomNeutralResponses = {"Interesting, tell me more",
			"Hmmm.",
			"Is that so?",
			"Fascinating",
			"Indeed.",
			"Would you like a tour of the Enterprise?",
			"I did not understand. Could you repeat?"
	};
	private String [] randomAngryResponses = {"I fail to see your logic.", "Do you intend to follow me all day? I'm sure you have other tasks.", "If I was an emotional human like you, I would be extremely irritated."};
	private String [] randomHappyResponses = {"Do you plan to stay aboard tomorrow?", "I am sure the captain will be sorry he missed you.", "You possess a quick and logical mind."};
	
}

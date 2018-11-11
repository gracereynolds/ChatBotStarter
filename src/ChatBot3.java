import java.util.Random;
import java.util.Scanner;

/**
 * A program to carry on conversations with a human user.
 * This version:
 * @author Vincent Tran
 * @version September 2018
 */
public class ChatBot3
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
		return "The name is Fives";
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


		// Fives responses
		if (statement.length() == 0)
		{
			response = "Are you a droid? Say something!";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "What do you mean no?!";
			emotion--;
		}

		else if (findKeyword(statement, "orders") >= 0)
		{
			if (emotion < 5) {
				response = "I'm sorry, I cannot just follow orders when I know they are wrong!";
			}
			if (emotion > 0)
			{
				response = "I am a soldier, if an order is given by a superior then I should follow it";
			}
		}

        else if (findKeyword(statement, "honor") >= 0)
        {
            if (emotion < 5) {
                response = "Where is the honor in blindly following orders?";
            }
            if (emotion > 0)
            {
                response = "I believe in honor but not at needless cost of my brothers";
            }
        }

		else if (findKeyword(statement, "Fives") >= 0)
		{
			if (emotion < 5) {
				response = "You'll address me as sir.";
			}
			if (emotion >= 5) {
				response = "In case you wanted to know my official designation is Arc - 5555";
			}
			emotion++;
		}

		else if (findKeyword(statement, "Weapon") >= 0)
		{
			response = "My go to weapon are the twin DC - 17 and from time to time the DC - 15A";
			emotion++;
		}

        //Guessing game
		else if (findKeyword(statement, "game") >= 0)
		{
			response = "If you want to play a game, guess a number from 1-10(in words)";
		}
		else if (findKeyword(statement, "ten") >= 0)
		{
			response = "You got it! It is ten.";
			emotion++;
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
		else if (findKeyword(statement, "hate",0) >= 0)
		{
			response = transformIHateYouStatement(statement);
		}
		else if (findKeyword(statement, "I dislike",0) >= 0)
		{
			response = transformIDislikeStatement(statement);
		}
		else if (findKeyword(statement, "Name",0) >= 0)
		{
			response = transformNamingStatement(statement);
		}
		else
		{
			response = getRandomResponse();
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
		return "Would you really be happy if you had " + restOfStatement + "?";
	}


	/**
	 * Take a statement with "I <something> you" and transform it into
	 * "Why do you <something> me?"
	 * @param statement the user statement, assumed to contain "I" followed by "you"
	 * @return the transformed statement
	 */
	private String transformIHateYouStatement(String statement)
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

		int psn = findKeyword (statement, "I hate", 0);

		String restOfStatement = statement.substring(psn + 6).trim();
		return "Why do you hate " + restOfStatement + " me?";
	}

	/** Transformer */
	private String transformIDislikeStatement(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement.length()-1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0,statement.length() -1);
		}
		int psn = findKeyword (statement, "I dislike", 0);
		String restofStatement = statement.substring(psn + 9).trim();
		return "Why don't you like " + restofStatement + "?";
	}
	private String transformNamingStatement(String statement)
	{
		statement = statement.trim();
		String lastChar = statement.substring(statement.length()-1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0,statement.length() -1);
		}
		int psn = findKeyword (statement, "Name", 0);
		return "My clone trooper designation is 27 - 5555" + ".";
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
		if (emotion > 5)
		{
			return randomHappyResponses[r.nextInt(randomHappyResponses.length)];
		}
		if (emotion < -5)
		{
			return randomAngryResponses [r.nextInt(randomAngryResponses.length)];
		}
		    return randomNeutralResponses [r.nextInt(randomNeutralResponses.length)];

	}

	private String [] randomNeutralResponses = {"Beats me..",
			"Whats the purpose of all this?",
			"Hmmm",
			"I might need to check on up that",
			"For the Republic",
			"I'm a clone, a soldier!",
			"The highest levels are involved",
			"I'm not crazy!"


	};
	private String [] randomAngryResponses = {"I'm not just another number, none of us are!", "My blood is boiling for a fight", "This can't be good..","The nightmares.. The missions.. It's finally over.."};
	private String [] randomHappyResponses = {"I was apart of one the best squads, the Domino squad.", "I fight for the 501st legion", "There is another option"};

}
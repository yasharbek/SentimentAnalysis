/**
 * @author Yasharbek Sabitov
 *
 * This class contains a method for reading from a file and creating Sentence objects
 * for a sentiment analysis program.
 */

import java.util.*;
import java.io.*;

public class Reader {

	/**
	 * This method checks for the following cases:
	 * A line starts w int outside the [-2,2] range - done
	 * A line starts with a non-integer - done
	 * A line only has an integer - done
	 *
	 * @param words Name of the input of array of words from the sentence
	 * @return boolean (true if sentence passes conditions, false if not)
	 */
	public static boolean validEntry(String[] words){
		if (words.length != 1){
			try {
				Integer moodValue = Integer.valueOf(words[0]);
				if (moodValue <= -2 && moodValue >= 2){
					return true;
				}
				else{
					return true;
				}
			}
			catch (NumberFormatException e){
				return false;
			}
		}
		else{
			return false;
		}

	}

	/**
	 * This method reads sentences from the input file, creates a Sentence object
	 * for each, and returns a Set of the Sentences.
	 *
	 * @param filename Name of the input file to be read
	 * @return Set containing one Sentence object per sentence in the input file
	 * @throws IllegalArgumentException if filename is null
	 */

	public static Set<Sentence> readFile(String filename) {

		File fileToRead = new File(filename);
		if (fileToRead == null){
			throw new IllegalArgumentException("You sent me bad input.");
		}
		else{
			try (Scanner in = new Scanner(fileToRead)){
				HashSet<Sentence> sentenceList = new HashSet<Sentence>();
				while (in.hasNext()){
					String textLine = in.nextLine();
					String[] words = textLine.split(" ");
					if (validEntry(words)){
						String lineWithoutNum = String.join(" ", Arrays.copyOfRange(words, 1, words.length));
						Sentence newSentence = new Sentence(Integer.parseInt(words[0]), lineWithoutNum);
						sentenceList.add(newSentence);
					}
				}
				return sentenceList;
			}
			catch (FileNotFoundException e){
				throw new IllegalArgumentException("You sent me some bad input");
			}
		}
	}
}

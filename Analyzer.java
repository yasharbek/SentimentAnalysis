/**
 * @author Yasharbek Sabitov
 *
 * This class contains the methods used for conducting a simple sentiment analysis.
 */

import java.util.*;

public class Analyzer {


	/**
	 * This method calculates the weighted average for each word in all the Sentences.
	 * This method is case-insensitive and all words should be stored in the Map using
	 * only lowercase letters.
	 *
	 * @param sentences Set containing Sentence objects with words to score
	 * @return Map of each word to its weighted average
	 * @throws IllegalArgumentException if sentences is null
	 */

	private static Map<String, Double> retypedSentences = new HashMap<>();

	public static void addToHashMap(HashMap<String, Double> ourMap, Set<Sentence> sentences, String word){
		//make sure the word is not already in the map
		if (!ourMap.containsKey(word)){
			//start accumulating scores and the number of times the word appears
			int accumScore = 0;
			int accumCount = 0;
			//for every sentence in the set
			for (Sentence eachSentence : sentences){
				//check how many times the word appears in the sentences
				int tempAccum = eachSentence.wordChecker(word);
				//if it is more than 0
				if (tempAccum != 0){
					//add the number of times it appears in this sentence to the accumulator
					accumCount += tempAccum;
					//multiply the sentence score by the number of times the word appears
					//to weight the score
					accumScore += eachSentence.getScore()*tempAccum;
				}
			}
			//calculate average score by dividing accumulated score by the count
			Double averageScore = (double) accumScore/accumCount;
			//put it into the map where word is the key and average score is the value
			ourMap.put(word, averageScore);
		}
	}


	/**
	 * This method creates a map of individual words and their sentiment values
	 * given a Set of Sentence object
	 *
	 * @param sentences Set of Sentence Objects
	 * @return a map of all the individual words and their sentiment values
	 * @throws IllegalArgumentException if the set of sentences is null
	 */
	public static Map<String, Double> calculateWordScores(Set<Sentence> sentences) {

		 //throw exception if input invalid
		if (sentences == null){
			throw new IllegalArgumentException("Invalid set input!");
		}
		else{
			 //create an empty HashMap
			HashMap<String, Double> wordValues = new HashMap<String, Double>();
			if (sentences.size() == 0){
				//if no sentences, return empty set
				return wordValues;
			}
			else{

				//for every sentence in the set
				for (Sentence element : sentences){
					//create an array composed of individual words from the sentence
					String[] ourWords = element.getSentenceArray();
					//and for every word
					for(String word : ourWords){
						//check if the first letter is alphabetic
						if (Character.isLetter(word.charAt(0))){
							if (Character.isLowerCase(word.charAt(0))){
								//add to hashMap
								addToHashMap(wordValues, sentences, word);
							}
							else{
								String lowercasedWord = word.toLowerCase();
								//add to HashMap along with current value
								addToHashMap(wordValues, sentences, word);
							}
						}
					}
				}
				//return the map once the loop through sentences ends
				return wordValues;
			}
		}
	}

	/**
	 * This method is a wrapper for doCalculateSentenceScore, which yields a double value
	 * that represents the sentiment value of a sentence given. This method performs 
	 * memoization, or saves past inputs from the user, along with the double sentiment
	 * values from doCalculateSentenceScore, to a Map called retypedSentences with the goal
	 * to increase performance of the program.
	 *
	 * @param wordScores Map of String words and their corresponding double sentiment values
	 * @param sentence String sentence to evalue the sentiment of
	 * @return a double value that calculates the average sentiment
	 * @throws IllegalArgumentException if the set of sentences is null
	 */
	public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence){
		if (retypedSentences.containsKey(sentence)){
			return retypedSentences.get(sentence);
		}
		else{
			double saveResult = doCalculateSentenceScore(wordScores, sentence);
			retypedSentences.put(sentence, saveResult);
			return saveResult;
		}
	}
	/**
	 * This method determines the sentiment of the input sentence using the average of the
	 * scores of the individual words, as stored in the Map.
	 * This method is case-insensitive and all words in the input sentence should be
	 * converted to lowercase before searching for them in the Map.
	 *
	 * @param wordScores Map of words to their weighted averages
	 * @param sentence Text for which the method calculates the sentiment
	 * @return double - weighted average scores of all valid words from the input sentence
	 * @throws IllegalArgumentException if the string or the map is null
	 */
	private static double doCalculateSentenceScore(Map<String, Double> wordScores, String sentence) {
		/*
		 * Implement this method in Part 3
		 */

		 //checking if Map and String are nul
		if ((wordScores == null) || (sentence == null)){
			throw new IllegalArgumentException("Map and/or Sentence are null!");
		}
		//if not null then proceed
		else{
			//if the sentence is NOTHING return 0
			if (sentence.length() == 0) {
				return 0;
			}
			//else proceed to calculate
			else {
				//break up sentence into an array
				String[] brokenSentence = sentence.split(" ");

				//count and score accumulators of all valid sentence words
				int count = 0;
				double score = 0;
				//check if each word starts with a letter AND is lwrcase
				for (String word : brokenSentence){
					//it is alphabetic, so add it to count
					if (Character.isLetter(word.charAt(0))){
						count++;
						//if it is lowercase, check if word is in map
						if (Character.isLowerCase(word.charAt(0))){
							//if it is in map, get its value and add it to score
							if(wordScores.containsKey(word)){
								score += wordScores.get(word);
							}
							//if it isn't, add it to the map with the value
							else{
								Double zero = Double.valueOf(0);
								wordScores.put(word, zero);
							}
						}
						
						//if it isn't lowercase then turn it lowercase
						else{
							String lowercasedWord = word.toLowerCase();
							if(wordScores.containsKey(lowercasedWord)){
								score += wordScores.get(lowercasedWord);
							}
							else{
								Double zero = Double.valueOf(0);
								wordScores.put(lowercasedWord, zero);
							}
						}
					}
				}
				double finalScore = score/count;
				return finalScore;
			}
		}
	}
}

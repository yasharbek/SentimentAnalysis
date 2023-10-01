/**
 * @author Yasharbek Sabitov
 *
 * This class contains the main method for the sentiment analysis program.
 */

 import java.util.*;
 import java.io.*;

public class Main {


	public static void main(String[] args) {

		if (args.length == 0){
			 System.out.println("no input file");
		}

		else{
			try{
				Set<Sentence> newFile = Reader.readFile(args[0]);
				Map<String, Double> wordScores = Analyzer.calculateWordScores(newFile);
				// Create a Scanner object to read input

				String userInput = "notquit";
				while (!userInput.equals("quit")){
					Scanner scanner = new Scanner(System.in);
					System.out.print("Please enter a sentence: ");
					userInput = scanner.nextLine();
					double sentenceScore = Analyzer.calculateSentenceScore(wordScores, userInput);
					System.out.printf("The score of your sentence is %.2f\n", sentenceScore);
				}


			}
			catch (IllegalArgumentException e){
				 System.out.println("bad input file");
			}

		}
	}
}

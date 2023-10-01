/**
 * @author Chris Murphy
 *
 * This class represents a single sentence from the input file.
 *
 */


public class Sentence {

	/**
	 * The sentiment score for the sentence. Should be in the range [-2, 2]
	 */
	private int score;

	/**
	 * The text contained in the sentence.
	 */
	private String text;

	public Sentence(int score, String text) {
		this.score = score;
		this.text = text;
	}

	public int getScore() {
		return score;
	}

	public String getText() {
		return text;
	}

	public int wordChecker(String word) {
		String[] line = getSentenceArray();
		int accum = 0;
		for (String indexedWord : line){
			if (indexedWord.equals(word)){
				accum++;
			}
		}
		return accum;
	}

	public String[] getSentenceArray(){
		String line = getText();
		return line.split(" ");
	}


}

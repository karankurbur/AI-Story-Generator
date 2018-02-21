import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class HW3 {

	//A map to store info about w0, w1, and w2.
	public static TreeMap<String, ArrayList<Node>> masterList;

	//Creates the masterlist of words.
	public HW3() throws FileNotFoundException {
		masterList = new TreeMap<String, ArrayList<Node>>();
	}

	//Fills the masterlist by scanning through entire books.
	public void fillList() throws FileNotFoundException {
		fillBook(new Scanner(new File("doyle-case-27.txt")));
		fillBook(new Scanner(new File("doyle-27.txt")));
		fillBook(new Scanner(new File("alice-27.txt")));
		fillBook(new Scanner(new File("london-call-27.txt")));
		fillBook(new Scanner(new File("melville-billy-27.txt")));
		fillBook(new Scanner(new File("twain-adventures-27.txt")));
	}

	//Adds the words in a given book to the masterlist.
	public static void fillBook(Scanner input) throws FileNotFoundException {
		ArrayList<String> words = new ArrayList<String>();
		while (input.hasNext()) {
			String word = input.next();
			words.add(word.toLowerCase());
		}
		for (int i = 0; i < words.size() - 2; i++) {
			String w0 = words.get(i);
			String w1 = words.get(i + 1);
			String w2 = words.get(i + 2);
			addToList(w0, w1, w2);
		}
	}

	//Returns the word2 with the highest frequency to be added to the story.
	public static String getW2(TreeMap<String, Integer> w2Options) {
		int highestCount = 0;
		String out = "";
		for (String word : w2Options.keySet()) {
			if (w2Options.get(word) > highestCount) {
				out = word;
				highestCount = w2Options.get(word);
			}
		}
		return out;
	}

	//Adds the three given words to the masterlist.
	public static void addToList(String w0, String w1, String w2) throws FileNotFoundException {
		if (masterList.containsKey(w0)) {
			ArrayList<Node> temp = masterList.get(w0);
			boolean w1Found = false;
			for (Node word1 : temp) {
				if (word1.word.equals(w1)) {
					w1Found = true;
					TreeMap<String, Integer> w2Map = word1.options;
					if (w2Map.containsKey(w2)) {
						w2Map.put(w2, w2Map.get(w2) + 1);
					} else {
						w2Map.put(w2, 1);
					}
				}
			}
			if (!w1Found) { // Didnt find w1, create new node for w1 and add w2
				Node w1Node = new Node(w1, w2);
				temp.add(w1Node);
			}
		} else { // w0 not found
			ArrayList<Node> w1List = new ArrayList<Node>();
			Node w1Node = new Node(w1, w2);
			w1List.add(w1Node);

			masterList.put(w0, w1List);
		}
	}

	//Randomly gets a word from the masterlist to the be first word in the sentence.
	public static String getFirstWord() {
		int rand = (int) (Math.random() * masterList.size());
		int count = 0;
		String firstWord = "";
		for (String a : masterList.keySet()) {
			if (count == rand) {
				firstWord = a;
			}
			count++;
		}
		return firstWord;
	}
	
	//Randomly gets a word based on word0 to the be second word in the sentence.
	public static String getSecondWord(String w0) {
		ArrayList<Node> secondWordOptions = masterList.get(w0);
		int rand1 = (int) (Math.random() * secondWordOptions.size());
		String secondWord = secondWordOptions.get(rand1).word;
		return secondWord;
	}

	// Creates a 1000 word story by generating sentences of a random word length
	// between 4 and 14.
	public String createStory() {
		int length = 0;
		String story = "";
		while (length < 1000) {
			int sentLength = (int) (Math.random() * 14) + 1;
			while (sentLength < 4) {
				sentLength = (int) (Math.random() * 14) + 1;
			}
			length += sentLength;
			story = story + createSentence(sentLength);
		}
		return story;
	}

	// Creates a sentence with a given word count.
	public static String createSentence(int length) {
		String w0 = getFirstWord();
		String word0 = w0.substring(0, 1).toUpperCase() + w0.substring(1);
		String w1 = getSecondWord(w0);
		String story = word0 + " " + w1;
		for (int i = 2; i < length; i++) {
			String w2 = getWord(w0, w1);
			String temp = w1;
			w0 = temp;
			w1 = w2;
			story = story + " " + w2;
		}
		return story + ". ";
	}

	//Returns the best word to add to the story given w0 and w1.
	public static String getWord(String w0, String w1) {
		ArrayList<Node> word1 = masterList.get(w0);
		String w2 = "";
		for (int i = 0; i < word1.size(); i++) {
			if (word1.get(i).word.equals(w1)) {
				w2 = getW2(word1.get(i).options);
			}
		}
		return w2;
	}

	//Fills the masterlist with the given texts and outputs a 1000 word story. 
	public static void main(String[] args) throws FileNotFoundException {
		HW3 a = new HW3();
		a.fillList();
		System.out.println(a.createStory());
	}
}

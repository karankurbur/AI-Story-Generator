import java.util.TreeMap;

public class Node {

	public String word;
	public TreeMap<String, Integer> options;

	//Creates a node to store info about word1 and word2.
	public Node() {
		options = new TreeMap<String, Integer>();
		word = new String();
	}

	//Creates a node with given w1 and w2.
	public Node(String w1, String w2) {
		options = new TreeMap<String, Integer>();
		word = w1;
		options.put(w2, 1);
	}
}

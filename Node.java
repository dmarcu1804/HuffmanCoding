

public class Node extends HuffmanBuildTree{
	
	//create the subtrees -> left and right
	public HuffmanBuildTree left;
	public HuffmanBuildTree right;

	
	//constructor initialising left and right subtree
	public Node(HuffmanBuildTree left, HuffmanBuildTree right) {
		//getting the frequency of the left and the right subtree
		super(left.frequency + right.frequency);
		this.left = left;
		this.right = right;
		// TODO Auto-generated constructor stub
	}

}

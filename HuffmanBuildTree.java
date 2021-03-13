

abstract class HuffmanBuildTree implements Comparable<HuffmanBuildTree>{


	//frequency of the tree
	public int frequency;

	//constructor initialising instance variable
	public HuffmanBuildTree(int frequency) {
		//initialising variable
		this.frequency = frequency;
	}
	
	//compares the frequency
	public int compareTo(HuffmanBuildTree tree) {
		return frequency - tree.frequency;
	}
}

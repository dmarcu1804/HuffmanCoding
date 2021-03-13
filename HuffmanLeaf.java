

public class HuffmanLeaf extends HuffmanBuildTree{
	//this is the character that the leaf will represent
	public char character;
	
	//constructor initialising the frequency and character
	public HuffmanLeaf(int frequency, char character) {
		//inheriting from HuffmanBuildTree class
		super(frequency);
		//initialising the character
		this.character = character;
		// TODO Auto-generated constructor stub
	}
}
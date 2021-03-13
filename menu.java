
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class menu {
	//this will be the size of the characterFrequencies
	final static int ASCII_size = 256;
	//public static  String ultCode = "";
	
	//stores character and its huffman code
	static HashMap<Character, String> map = new HashMap<>(); 
	
	//frequency table
	private static int[] frequencyTable(String data) {
		//array
		int[] frequency = new int[ASCII_size];
				
		//iterate through the parameter - string of data
		//data has been transformed into an array of characters so iteration is possible
		for(char character : data.toCharArray()) {
		//frequency of that character gets incremented
			
			frequency[character]++;
			//System.out.println("This is frequency character" + frequency[character]);
		}
		//return frequency		
		return frequency;
	}
	
	public static HuffmanBuildTree createTree(int[] characterFrequencies) {
		//initialise size of array to 256
		//characterFrequencies = new int[ASCII_size];
		//create a priority queue
		PriorityQueue<HuffmanBuildTree> trees = new PriorityQueue<HuffmanBuildTree>();
		
		//iterate through array
		for(int i = 0; i < ASCII_size; i++) {
			//if the frequency of a letter is >0
			if(characterFrequencies[i] > 0) {
				//add it to the tree priority queue
				//it will be a leaf, storing the characterFrequency and the character itself
				trees.add(new HuffmanLeaf(characterFrequencies[i], (char)i));
			}
		}
		
		if (!(trees.size() > 0))
		    throw new AssertionError("Please add some characters in the tree");
		
		
		//looping until there is only one tree left
		while(trees.size() > 1) {
			//removing the first element in the queue, which will be the left child
			HuffmanBuildTree leftChild = trees.poll();
			//removing the second element in the queue, which will be the right child
			HuffmanBuildTree rightChild = trees.poll();
			
			//merging the leftChild and the rightChild to make a parent
			HuffmanBuildTree parent = new Node(leftChild, rightChild);
			
			//add the parent to the tree
			trees.add(parent);
		}
		//shows the elements in the tree
		return trees.poll();
	}
	
	
	public static void addCodes(HuffmanBuildTree tree, StringBuffer code) {
		
		
		if(tree == null) {
			//System.out.println("Please add some characters in the tree");
			throw new AssertionError("Please add some characters in the tree");
		}
		
		//testing whether the object is an instance of the HuffmanLeaf class
		if(tree instanceof HuffmanLeaf) {
			
			HuffmanLeaf leaf = (HuffmanLeaf)tree;
			
			// print out character, frequency, and code for the huffman code for the leaf 
            System.out.println(leaf.character + "\t" + leaf.frequency + "\t" + code);
			//System.out.print(code);
            map.put(leaf.character,code.toString());
          //  System.out.println("Map is" + map.toString());
            
		}
		
		
		else if(tree instanceof Node) {
			Node node = (Node)tree;
			
			//traverse left
			code.append('0');
			addCodes(node.left, code);
			code.deleteCharAt(code.length()-1);
			
			//traverse right
			code.append('1');
			addCodes(node.right, code);
			//System.out.println("code before " + code.length());
			code.deleteCharAt(code.length()-1);
			//System.out.println("code deletion " + code.length());
			
			
		}
		//System.out.println();
			
	}
	
	//decoding function
	//gets the whole tree and encoded is the final result after compression
	static void decode(HuffmanBuildTree root, String encoded) {
		//checks if the tree exists
	    if (root == null) {
	        return;
	    }
	    //create char arr which stores all the characters
	    char[] arr = encoded.toCharArray();
	    int index = 0;
	    String rst = "";
	    //traversing through the array
	    while (index < arr.length) {
	    	//resets -> starts at the beginning again
	        HuffmanBuildTree node = root;
	        //while the node is not null
	        while (node != null) {
	        	//if the node is a leaf
	            if (node instanceof HuffmanLeaf) {
	            	//create leaf k and store the leaf node there
	                HuffmanLeaf k = (HuffmanLeaf)node;
	                //add the character of leaf to rst
	                rst += k.character;
	                break;
	            } 
	            //if the node is a node
	            if(node instanceof Node) {
	            	//looks at character in array
	                char c = arr[index];
	                //if it is 0
	                if (c == '0') {
	                	//goes down on the left side
	                    node = ((Node)node).left;
	                } else {
	                	//if it is 0 goes down on the right side
	                    node = ((Node)node).right;
	                }
	                //increment the index
	                index++;
	            }
	        }
	    }
	   // System.out.println();
	    System.out.println("Final decoded result is: " + rst);
	}
	
	
	public static void efficiencyCalculator(String test) {
		double probability = 0;//weight/test.length()
		int len = test.length();
		int charWeightTotal = 0;
		
	//	ArrayList<Double> probs = new ArrayList<>();
		HashMap<Character, Double> vals = new HashMap<>();
		Map<Character, Integer> codeLen = new HashMap<>();
		for(int i = 0; i < ASCII_size; i++) {
			char c = (char)i;
			probability = (double)frequencyTable(test)[i]/len;
			//probs.add(probability);
			if(probability != 0) {
				//probs.add(probability);
				vals.put(c,probability);
				charWeightTotal = (int)frequencyTable(test)[i] + charWeightTotal;
				//System.out.println("Frequency table test for character " + c +" which appears "+ (int)frequencyTable(test)[i] +" times " + " probs is " + (double)frequencyTable(test)[i]/len);
			
			}
		}
	//	System.out.println(vals.toString());
	//	System.out.println(map.toString());
		
		
		double averageCode = 0;
		for(Character key : map.keySet()) {
			int value1 = map.get(key).length();
			double value2 = vals.get(key);
			
			double multValue = value1;
			
			averageCode += multValue*value2;
		}
		System.out.println("Average code length is " + averageCode);
		
		double entropy = 0.0;
		for(Double val : vals.values()) {
			entropy += val * (Math.log(val)/Math.log(2.0));
			//System.out.println(val);
		}
		entropy*=(-1);
		System.out.println("Entropy is " + entropy);
		
		double efficiency = (entropy/averageCode) * 100;
		System.out.println("Efficiency is: "+ (String.format("%.2f", efficiency) + "%"));
		
		
									/* UNUSED CODE
		
//		System.out.println(vals.keySet());
//		
//		System.out.println(map.values());
//		
//		Map<Set<Character>, Collection<String>> codelen = new HashMap<>();
//		codelen.put(vals.keySet(), map.values());
//		
//		Map<Set<Character>, Integer> codelen2 = new HashMap<>();
//		
//		System.out.println(codelen.toString());
//		for(String s : map.values()) {
//			int lenthy = s.length();
//			String tryingout = Integer.toString(lenthy);
//			System.out.println(lenthy);
//			for(int i =  0; i < 4; i++) {
//				codelen2.put(vals.keySet(), lenthy);
//			}
//			//codelen2.put(vals.keySet(), tryingout);
//		}
//		System.out.println(map.toString());
//		for(int i = 0; i < map.values().size(); i++) {
//			for(String s : map.values()) {
//				
//			}
//			 
//		}
		
		//System.out.println("Probability is" + probability);
	//	System.out.println("Array of probs is" + probs);
		
		//System.out.println(map.toString());
//		Collection<String> values = map.values();
//		 Set<Character> keys = map.keySet();
//		 ArrayList<Character> abcde = new ArrayList<>();
//		 int codeWordLen = 0;
//		 int k =0;
//		 for(Character key : keys) {
//			 abcde.add(key);
//		 }
//		 for (String v : values) {
//	        	codeWordLen = v.length();
//	        	//codeLen.put()
//	        	System.out.println("V: " + v);
//	        	k = Integer.parseInt(v);
//	        	//codeLen.put('a',k);
//	        	for(int i = 0; i < k; i++) {
//	        		codeWordLen = v.length();
//	        		for(Character key : keys) {
//	        			codeWordLen = v.length();
//	        			codeLen.put(key,codeWordLen);}
//	        	}
//	            System.out.println("Value: " + k);
//	            System.out.println("codewordlen" + codeWordLen);
//	        }
		 
		//Merge maps
//		 codeLen.forEach(
//		     (keys, values) -> map1.merge( keys, values, (v1, v2) -> v1.equalsIgnoreCase(v2) ? v1 : v1 + "," + v2)
//		 );
//		int k = 0;
//		int codeWordLen = 0;
//		
//			for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
//				String v = iterator.next();
//	        	codeWordLen = v.length();
//	        	for(Character key : keys) {
//	        		codeLen.put(key, codeWordLen);
//	        	}
//	        	//codeLen.put()
//	        	System.out.println("codeword is" + codeWordLen);
//	        	k = Integer.parseInt(v);
//	        	
//	        	//codeLen.put(key, codeWordLen);
//	           // System.out.println("Value k : " + k);
//	           // System.out.println("codewordlen" + codeWordLen);
//				 
//	        }
	       
//	        for (Character m : keys) {
//	        	
//	    		codeLen.put(m,codeWordLen);
//	    		
//	            System.out.println("Key: " + m);
//	        }
		
//        for (String v : values) {
//        	codeWordLen = v.length();
//        	//codeLen.put()
//        	
//        	k = Integer.parseInt(v);
//        	
//        	//codeLen.put(m,codeWordLen);
//            System.out.println("Value: " + k);
//            System.out.println("codewordlen" + codeWordLen);
//        }
//       
//        for (Character m : keys) {
//    		codeLen.put(m,codeWordLen);
//            //System.out.println("Key: " + m);
//        }
//        System.out.println("Map of values probs" + vals.toString());
//        System.out.println("CodewordLen map " + codeLen.toString());
//        
//        System.out.println(map.toString());
//        for(int i = 0; i < charWeightTotal; i++) {
//        	
//        }
        
									 			*/
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);

		
			String result = "";
			System.out.println("Enter an input that you would like to convert into huffman code: ");
			Scanner scan1 = new Scanner(System.in);
			String testInput = scan1.nextLine();
			int[] freqTable = frequencyTable(testInput);
			HuffmanBuildTree tree = createTree(freqTable);
			
			StringBuffer s = new StringBuffer();
			System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
	        addCodes(tree,s);
	        
	        System.out.println();
	        System.out.print("Final encoded result is: " );
	        for(char c : testInput.toCharArray()) {
	        	System.out.print(map.get(c));
	        	result = result + map.get(c);
	        }
	        System.out.println();
	        System.out.println();
	        System.out.println("To decode the final result, press 2 and then enter");
	        System.out.println("To show efficiency, press 3 and then enter");
	        Scanner scan2 = new Scanner(System.in);
	        String scanner2 = scan2.nextLine();
	        if(scanner2.equals("2")) {
	        	decode(tree, result);
	        	
	        	System.out.println();
	        	System.out.println("If you would like to show the efficiency, press 2 and then enter");
	        	System.out.println("If you would like to exit the program, press 3 and then enter");
	        	Scanner scan3 = new Scanner(System.in);
	        	String scanner3 = scan.nextLine();
	        	if(scanner3.equals("2")) {
	        		efficiencyCalculator(testInput);
	        	}
	        	else {
	        		System.out.println("Thank you for using my program!");
	        		return;
	        	}
	        }else if(scanner2.equals("3")){
//	        	System.out.println("Please press 2 and then enter to decode the result ");
//	        	scanner2 = scan2.nextLine();
	        	efficiencyCalculator(testInput);
//	        	if(scanner2.equals("2")) {
//	        		decode(tree, result);
//	        	}
	        	System.out.println();
	        	System.out.println("If you would like to decode the final result, press 2 and then enter");
	        	System.out.println("If you would like to exit the program, press 3 and then enter");
	        	Scanner scan3 = new Scanner(System.in);
	        	String scanner3 = scan.nextLine();
	        	if(scanner3.equals("2")) {
		        	decode(tree, result);
	        	}
	        	else {
	        		System.out.println("Thank you for using my program!");
	        		return;
	        	}
	        }
	        else {
	        	System.out.println("Invalid response!");
	        	return;
	        	
	        }
	        
	        
	        
	        
	        
	        /*                   UNUSED CODE
//	        System.out.println();
//	        System.out.println("To show the efficiency of this input, press 3");
//	        Scanner scan3 = new Scanner(System.in);
//	        if(scan3.nextLine().equals("3")) {
//	        	efficiencyCalculator(testInput);
//	        }
		
		
		
//		String test = "23";
//		String result = "";
//		
//		int[] freqTable = frequencyTable(test);
//		HuffmanBuildTree tree = createTree(freqTable);
//		
//		StringBuffer s = new StringBuffer();
//		System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
//        addCodes(tree,s);
//        
//        System.out.println();
//        System.out.print("Final encoded result is: " );
//        for(char c : test.toCharArray()) {
//        	System.out.print(map.get(c));
//        	result = result + map.get(c);
//        }
//        
//        decode(tree, result);
//        
//        efficiencyCalculator(test);
	        	*/

	}

}

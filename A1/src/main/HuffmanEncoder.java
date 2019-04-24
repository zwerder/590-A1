package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.OutputStreamBitSink;

public class HuffmanEncoder {

	private Map<Integer, String> _code_map;

	public HuffmanEncoder(int[] symbols, int[] symbol_counts) {
		assert symbols.length == symbol_counts.length;
       
		// Given symbols and their associated counts, create initial
		// Huffman tree. Use that tree to get code lengths associated
		// with each symbol. Create canonical tree using code lengths.
		// Use canonical tree to form codes as strings of 0 and 1
		// characters that are inserted into _code_map.

		// Start with an empty list of nodes

		List<HuffmanNode> node_list = new ArrayList<HuffmanNode>();

		// Create a leaf node for each symbol, encapsulating the
		// frequency count information into each leaf.
		for (int i = 0; i < symbols.length; i++) {
			LeafHuffmanNode build = new LeafHuffmanNode(symbols[i], symbol_counts[i]);
			//build.setCount(symbol_counts[i]);
			node_list.add(build);
		}

		// Sort the leaf nodes
		node_list.sort(null);

		// While you still have more than one node in your list...
		while (node_list.size() > 1) {
			
			// Remove the two nodes associated with the smallest counts
			int max = 10000000;
			int max2 = 10000000;
			int maxIndex = 0;
			int maxIndex2 = 0;

			for (int i = 0; i < node_list.size(); i++) {
				if (node_list.get(i).count() < max) {
					maxIndex = i;
					max = node_list.get(i).count();
				}
			}
			HuffmanNode remove1 = node_list.get(maxIndex);
			node_list.remove(maxIndex);
			for (int i = 0; i < node_list.size(); i++) {
				if (node_list.get(i).count() > 0 && node_list.get(i).count() < max2) {
					maxIndex2 = i;
					max2 = node_list.get(i).count();
				}
			}
			HuffmanNode remove2 = node_list.get(maxIndex2);
			node_list.remove(maxIndex2);
			
			// Create a new internal node with those two nodes as children.
			InternalHuffmanNode parent = new InternalHuffmanNode(remove1, remove2);
			
			// Add the new internal node back into the list
			node_list.add(parent);
			
			// Resort
			node_list.sort(null);
		}

		// Create a temporary empty mapping between symbol values and their code strings
		Map<Integer, String> cmap = new HashMap<Integer, String>();
		// List<HuffmanNode> node_list2 = node_list;

		// Start at root and walk down to each leaf, forming code string along the
		// way (0 means left, 1 means right). Insert mapping between symbol value and
		// code string into cmap when each leaf is reached.
		InternalHuffmanNode myRoot = (InternalHuffmanNode) node_list.get(0);
		insertCodes(cmap, myRoot, "");

		// Create empty list of SymbolWithCodeLength objects
		List<SymbolWithCodeLength> sym_with_length = new ArrayList<SymbolWithCodeLength>();

		// For each symbol value, find code string in cmap and create new
		// SymbolWithCodeLength
		// object as appropriate (i.e., using the length of the code string you found in
		// cmap).
		for (int i = 0; i < symbols.length; i++) {
			//System.out.print(symbols[i]);
			//System.out.print(symbols[i]);
			SymbolWithCodeLength wow = new SymbolWithCodeLength(symbols[i], cmap.get(i).length());
			sym_with_length.add(wow);
		}

		// Sort sym_with_lenght
		sym_with_length.sort(null);

		// Now construct the canonical tree as you did in HuffmanDecodeTree constructor

		InternalHuffmanNode canonical_root = new InternalHuffmanNode(null, null);

		for (int i = 0; i < sym_with_length.size(); i++) {
			int value = sym_with_length.get(i).value();
			int length = sym_with_length.get(i).codeLength();
			canonical_root.insertSymbol(length, value);
			//setCounts(canonical_root, value, length);
		}
		

		// If all went well, tree should be full.
		assert canonical_root.isFull();

		// Create code map that encoder will use for encoding
        
		_code_map = new HashMap<Integer, String>();

		// Walk down canonical tree forming code strings as you did before and
		// insert into map.
		insertCodes(_code_map, canonical_root, "");

	}

	public String getCode(int symbol) {
		return _code_map.get(symbol);
	}

	/*public void setCounts(HuffmanNode root, int value, int length) {
		if(root.isLeaf()) {
			if(root.symbol() == value) {
				((LeafHuffmanNode) root).setCount(length);
			}
		}else {
			if(!(root.left() == null)) {
				setCounts(root.left(), value, length);
			}if (!(root.right() == null)) {
				setCounts(root.right(),value, length);
			}
		}*/
	
	public void encode(int symbol, OutputStreamBitSink bit_sink) throws IOException {
		bit_sink.write(_code_map.get(symbol));
	}

	public void insertCodes(Map<Integer, String> map, HuffmanNode node, String myString) {
		if (node.isLeaf()) {
			map.put(node.symbol(), myString);
		} else {
			if (!(node.left() == null)) {
				insertCodes(map, node.left(), myString + "0");
			}
			if (!(node.right() == null)) {
				insertCodes(map, node.right(), myString + "1");
			}
		}
	}
}

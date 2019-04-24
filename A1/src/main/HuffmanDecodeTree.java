package main;

import java.io.IOException;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffmanDecodeTree {

	private HuffmanNode _root;

	public HuffmanDecodeTree(List<SymbolWithCodeLength> symbols_with_code_lengths) {

		// Create empty internal root node.

		_root = new InternalHuffmanNode(null, null);

		// Insert each symbol from list into tree
		for (int i = 0; i < symbols_with_code_lengths.size(); i++) {
			int value = symbols_with_code_lengths.get(i).value();
			int length = symbols_with_code_lengths.get(i).codeLength();
			_root.insertSymbol(length, value);
		}
		// If all went well, your tree should be full

		assert _root.isFull();
	}

	public int decode(InputStreamBitSource bit_source) throws IOException {

		// Start at the root
		HuffmanNode sub = _root;

		while (!sub.isLeaf()) {
			// Get next bit and walk either left or right,
			// updating n to be as appropriate

			try {
				if (bit_source.next(1) == 0) {
				  sub = sub.left();
				} else {
					sub = sub.right();
				}
			} catch (InsufficientBitsLeftException e) {
				e.printStackTrace();
			}
		}

		// Return symbol at leaf
		return sub.symbol();
	}
}
package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.InputStreamBitSource;
import io.InsufficientBitsLeftException;

public class HuffDecode {

	public static void main(String[] args) throws FileNotFoundException {
		//statement set to original decompression, uncomment statements to see re-decompression 
		
		//String input_file_name = "data/compressed.dat";
		String input_file_name = "data/recompressed.dat";
		String output_file_name = "data/reuncompressed.txt";
		//String output_file_name = "data/uncompressed.txt";
		FileInputStream fis = new FileInputStream(input_file_name);

		InputStreamBitSource bit_source = new InputStreamBitSource(fis);

		List<SymbolWithCodeLength> symbols_with_length = new ArrayList<SymbolWithCodeLength>();

		// Read in huffman code lengths from input file and associate them with each
		// symbol as a
		// SymbolWithCodeLength object and add to the list symbols_with_length.
		int[] lengths = new int[256];
		// File file1 = new File("compressed.dat");
		for (int i = 0; i < 256; i++) {
			try {
				lengths[i] = bit_source.next(8);
			} catch (InsufficientBitsLeftException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// inserts
		for (int j = 0; j < 256; j++) {
			SymbolWithCodeLength temp = new SymbolWithCodeLength(j, lengths[j]);
			symbols_with_length.add(temp);
		}
		// Then sort they symbols.
		boolean tryAgain = true;
		while (tryAgain == true) {
			tryAgain = false;
			for (int i = 0; i < symbols_with_length.size() - 1; i++) {
				if (symbols_with_length.get(i).codeLength() > symbols_with_length.get(i + 1).codeLength() ) {
					Collections.swap(symbols_with_length, i, i + 1);
					tryAgain = true;
				}
			}
		}

		// Now construct the canonical huffman tree
		HuffmanDecodeTree huff_tree = new HuffmanDecodeTree(symbols_with_length);

		int num_symbols = 0;

		// Read in the next 32 bits from the input file the number of symbols

		try {
			int num = bit_source.next(32);
			num_symbols = num;
		} catch (InsufficientBitsLeftException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			// Open up output file.

			FileOutputStream fos = new FileOutputStream(output_file_name);

			for (int i = 0; i < num_symbols; i++) {
				// Decode next symbol using huff_tree and write out to file.
				
				int result = (huff_tree.decode(bit_source));
				System.out.println(result);
				fos.write(result);
			} 
             
			// Flush output and close files.

			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

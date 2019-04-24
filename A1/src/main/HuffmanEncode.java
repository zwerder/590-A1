package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import io.OutputStreamBitSink;

public class HuffmanEncode {

	public static void main(String[] args) throws IOException {
		String input_file_name = "data/uncompressed.txt";
		String output_file_name = "data/recompressed.dat";

		FileInputStream fis = new FileInputStream(input_file_name);

		long lengthFile = new File("data/uncompressed.txt").length();
		int inputLength = (int) (lengthFile);
		int[] symbol_counts = new int[256];
		int num_symbols = 0;
		
		
		// Read in each symbol (i.e. byte) of input file and 
		// update appropriate count value in symbol_counts
		// Should end up with total number of symbols 
		// (i.e., length of file) as num_symbols
        
		for (int i = 0; i < inputLength ; i++) {
        	int character= fis.read();
        	symbol_counts[character] += 1;
        	num_symbols ++; 
        }
        if(!(num_symbols == inputLength)) {
        	throw new IOException("count mistmatch");
        }
		// Close input file
		fis.close();

		// Create array of symbol values
		
		int[] symbols = new int[256];
		for (int i=0; i<256; i++) {
			symbols[i] = i;
		}
		
	// Create encoder using symbols and their associated counts from file.
		
		HuffmanEncoder encoder = new HuffmanEncoder(symbols, symbol_counts);
		
		// Open output stream.
		FileOutputStream fos = new FileOutputStream(output_file_name);
		OutputStreamBitSink bit_sink = new OutputStreamBitSink(fos);

		// Write out code lengths for each symbol as 8 bit value to output file.
		for (int i = 0; i <symbol_counts.length; i++) {
		bit_sink.write(encoder.getCode(symbols[i]).length(), 8);
		}
		// Write out total number of symbols as 32 bit value.
        bit_sink.write(num_symbols, 32);
		// Reopen input file.
		fis = new FileInputStream(input_file_name);

		// Go through input file, read each symbol (i.e. byte),
		// look up code using encoder.getCode() and write code
        // out to output file.
		for(int i = 0; i < num_symbols; i++) {
		int symbolRead= fis.read();
		bit_sink.write(encoder.getCode(symbolRead));
		}
		// Pad output to next word.
		bit_sink.padToWord();

		// Close files.
		fis.close();
		fos.close();
	}
}

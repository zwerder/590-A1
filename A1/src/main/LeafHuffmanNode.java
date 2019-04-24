package main;

public class LeafHuffmanNode implements HuffmanNode {

	//Instance fields
	private int symbol;
	private int count;
	
	public LeafHuffmanNode(int symbol, int count) {
		this.symbol = symbol;
		this.count = count;
	}
	
	@Override
	public int count() {
		// Not used in decoder 
		return count;
	}
	
	/*public void setCount(int acount) {
		count = count;
	}*/

	@Override
	public boolean isLeaf() {
		// Yes
		return true;
	}

	@Override
	public int symbol() {
		// symbol setter? 
		return symbol;
	}

	@Override
	public int height() {
		// Leaf has no height
		return 0;
	}

	@Override
	public boolean isFull() {
		// True by definition
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		// Can't Insert on Leaf
		throw new NullPointerException("Illegal Insert");
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		throw new NullPointerException("Leaf has no children");
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		throw new NullPointerException("Leaf has no children");
	}

}

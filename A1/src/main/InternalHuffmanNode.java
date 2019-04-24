package main;

public class InternalHuffmanNode implements HuffmanNode {

	HuffmanNode left;
	HuffmanNode right;

	public InternalHuffmanNode(HuffmanNode left, HuffmanNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int count() {
		int count = this.left().count() + this.right().count();
		return count;
	}

	@Override
	public boolean isLeaf() {
		return false;
	}

	@Override
	public int symbol() {
		// TODO Auto-generated method stub
		throw new NullPointerException("Internal Node No Symbol");
	}

	@Override
	public int height() {
		// recursive call till farthest leaf node
		int height = 0;
		if (!(left == null) && !(right == null)) {
			height += 1;
			this.left().height();
			this.right().height();
		} else if (!(left == null) && right == null) {
			height += 1;
			int add = this.left().height();
			height += add;
		} else {
			height += 1;
			int add = this.right.height();
			height += add;
		}
		return height;
	}

	@Override
	public boolean isFull() {
		if (this.left() == null || this.right() == null) {
			return false;
		}
		if (this.left().isFull() == false || this.right().isFull() == false) {
			return false;
		}
		return true;
	}

	@Override
	public boolean insertSymbol(int length, int symbol) {
		if (length > 1) {
			if (this.left() == null) {
				InternalHuffmanNode intern = new InternalHuffmanNode(null, null);
				this.left = intern;
				this.left().insertSymbol(length - 1, symbol);

			} else if (this.left().isLeaf() == false && this.left().isFull() == false) {
				this.left().insertSymbol(length - 1, symbol);

			} else if (this.right() == null) {
				InternalHuffmanNode intern1 = new InternalHuffmanNode(null, null);
				this.right = intern1;
				this.right().insertSymbol(length - 1, symbol);

			} else if (this.right().isLeaf() == false && this.right().isFull() == false) {
				this.right().insertSymbol(length - 1, symbol);
				
			} else {
				return false;
			}
		}else if( length == 1) {
			if(this.left() == null) {
				LeafHuffmanNode leaf = new LeafHuffmanNode(symbol, 0);
				this.left = leaf;
				return true;
			}if(this.right() == null) {
				LeafHuffmanNode leaf1 = new LeafHuffmanNode(symbol, 0);
				this.right = leaf1;
				return true;
			}else {
				return false;
			}
		}
		return false;
	}

	@Override
	public HuffmanNode left() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public HuffmanNode right() {
		// TODO Auto-generated method stub
		return right;
	}

}

package SKPLIST_A4;

import java.util.Arrays;
import java.util.Random;

public class SkipList implements SkipList_Interface {
	private SkipList_Node root;
	private final Random rand;
	private double probability;
	int size;
	// line below used to be: private const int MAXHEIGHT = 30;
	private final int MAXHEIGHT = 30; // the most links that a data cell may contain

	public SkipList(int maxHeight) {
		root = new SkipList_Node(Double.NaN, maxHeight);
		rand = new Random();
		probability = 0.5;
	}

	@Override
	public void setSeed(long seed) {
		rand.setSeed(seed);
	}

	@Override
	public void setProbability(double probability) {
		this.probability = probability;
	}

	private boolean flip() {
		// use this where you "roll the dice"
		// call it repeatedly until you determine the level
		// for a new node
		return rand.nextDouble() < probability;
	}

	@Override
	public SkipList_Node getRoot() {
		return root;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		int levels;
		for (levels = 0; levels < root.getNext().length && root.getNext(levels) != null; levels++)
			;

		// System.out.println(levels)
		StringBuilder[] sbs = new StringBuilder[levels];
		for (int i = 0; i < sbs.length; i++) {
			sbs[i] = new StringBuilder();
			sbs[i].append("level ").append(i).append(":");
		}
		SkipList_Node cur = root;
		while (cur.getNext(0) != null) {
			cur = cur.getNext(0);
			for (int i = levels - 1; i >= cur.getNext().length; i--) {
				sbs[i].append("\t");
			}
			for (int i = cur.getNext().length - 1; i >= 0; i--) {

				if (cur.getNext(i) == null) {
					levels--;
				}
				sbs[i].append("\t").append(cur.getValue());
			}
		}
		for (int i = sbs.length - 1; i >= 0; i--) {
			sb.append(sbs[i]).append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean insert(double value) {
		if (this.contains(value)) {
			return false;
		}
		int levels = 1;
		while (this.flip()) {
			levels++;
		}
		levels = levels % root.getHeight();
		SkipList_Node newNode = new SkipList_Node(value, levels);
		SkipList_Node front = root;
		for (int i = root.getHeight() - 1; i >= 0; i--) {
			while ((front.getNext(i) != null) && front.getNext(i).getValue() < value) {
				// System.out.println("Value: "+value);
				// System.out.println("front val: "+ front.getNext(i).getValue());
				front = front.getNext(i);
			}
			if (i < levels) {
				newNode.setNext(i, front.getNext(i));
				front.setNext(i, newNode);
			}
		}

		size++;
		return true;
	}

	@Override
	public boolean remove(double value) {
		if (size == 0 || !this.contains(value)) {
			return false;
		}
		SkipList_Node front = root;
		for (int i = root.getHeight() - 1; i >= 0; i--) {
			while ((front.getNext(i) != null) && front.getNext(i).getValue() < value) {
				// System.out.println("Value: "+value);
				// System.out.println("front val: "+ front.getNext(i).getValue());
				front = front.getNext(i);
			}
			if (front.getNext(i) != null && front.getNext(i).getValue() == value) {
				front.setNext(i, front.getNext(i).getNext(i));
			}
		}
		size--;
		return true;
	}

	@Override
	public boolean contains(double value) {
		SkipList_Node front = root;
		for (int i = root.getHeight() - 1; i >= 0; i--) {
			while ((front.getNext(i) != null) && front.getNext(i).getValue() < value) {
				// System.out.println("Value: "+value);
				// System.out.println("front val: "+ front.getNext(i).getValue());
				front = front.getNext(i);
			}
			if (front.getNext(i) != null && front.getNext(i).getValue() == value) {
				// System.out.println("contains: "+ value);
				return true;
			}
		}
		return false;
	}

	@Override
	public double findMin() {
		if (size == 0) {
			return Double.NaN;
		}
		return root.getNext(0).getValue();
	}

	@Override
	public double findMax() {
		if (size == 0) {
			return Double.NaN;
		}
		SkipList_Node cur = root.getNext(0);
		while (cur.getNext(0) != null) {
			cur = cur.getNext(0);
		}
		return cur.getValue();
	}

	@Override
	public boolean empty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		size = 0;
		for (int i = root.getHeight() - 1; i >= 0; i--) {
			root.setNext(i, null);
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int level() {
		if (size == 0) {
			return -1;
		}
		for (int i = root.getHeight() - 1; i >= 0; i--) {
			if (root.getNext(i) != null) {
				return (i);
			}
		}
		return 0;
	}

	@Override
	public int max() {
		return root.getHeight();
	}

}
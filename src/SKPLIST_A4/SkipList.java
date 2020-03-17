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
		// TODO Auto-generated method stub
		if(this.contains(value)) {
			return false;
		}
		size++;
		return true;
	}

	@Override
	public boolean remove(double value) {
		// TODO Auto-generated method stub
		if(size==0 || !this.contains(value)) {
			return false;
		}
		size--;
		return true;
	}

	@Override
	public boolean contains(double value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double findMin() {
		// TODO Auto-generated method stub
		if(size == 0) {
			return Double.NaN;
		}
		return 0;
	}

	@Override
	public double findMax() {
		// TODO Auto-generated method stub
		if(size == 0) {
			return Double.NaN;
		}
		return 0;
	}

	@Override
	public boolean empty() {
		if(size==0) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		size = 0;
		//TODO reset sentinel node to original value
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int level() {
		// TODO Auto-generated method stub
		if(size == 0) {
			return -1;
		}
		//TODO rest of the function
		return 0;
	}

	@Override
	public int max() {
		// TODO Auto-generated method stub
		return 0;
	}


}
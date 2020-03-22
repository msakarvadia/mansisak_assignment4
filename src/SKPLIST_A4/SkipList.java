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
		if (this.contains(value)) {
			return false;
		}
		int levels = 0;
		while (!this.flip()) {
			levels++;
		}
		SkipList_Node newNode = new SkipList_Node(value, levels);
		SkipList_Node before_node = root;
		SkipList_Node after_node = new SkipList_Node(Double.NaN,MAXHEIGHT);
		//find the node right before and node right after where we want to insert
		//TODO: figure out if for loop here is correct
		for (int i = MAXHEIGHT; i >= 0; i--) {
			if (before_node.getNext(i).getValue() != Double.NaN) {
				after_node = before_node.getNext(i);
				if (after_node.getValue() < value) {
					before_node = after_node;
					after_node = after_node.getNext(i);
				}
				if (after_node.getValue() > value) {
					continue;
				}
			}
		}
		//use the node right before and node right after, to set up appropriate connections:
		//TODO:
		before_node.setNext(0,newNode);
		newNode.setNext(0,after_node);
		for(int i = 1; i <levels; i++) {
			//start from root or something, use while loop to get next, as long as next is less than val
			SkipList_Node front = root.getNext(i);
			while(front.getNext(i).getValue()<value) {
			front= front.getNext(i);
			}
			before_node.setNext(i,newNode);
			//Figure out someway to deal with afternode
			if(after_node.getHeight()<=i) {
				newNode.setNext(i, after_node);
			}
			else {
				after_node = after_node.getNext(i);
				newNode.setNext(i, after_node);
			}
		}
		size++;
		return true;
	}

	@Override
	public boolean remove(double value) {
		// TODO Auto-generated method stub
		if (size == 0 || !this.contains(value)) {
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
		if (size == 0) {
			return Double.NaN;
		}
		return 0;
	}

	@Override
	public double findMax() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return Double.NaN;
		}
		return 0;
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
		// TODO Auto-generated method stub
		size = 0;
		// TODO reset sentinel node to original value
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int level() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return -1;
		}
		// TODO rest of the function
		return 0;
	}

	@Override
	public int max() {
		// TODO Auto-generated method stub
		return 0;
	}

}
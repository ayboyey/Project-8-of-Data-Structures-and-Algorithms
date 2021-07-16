package lab08;

import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<T extends Comparable<T>> {
	private class Node {
		T value;
		Node left, right, parent;	

		public Node(T v) {
			value = v;
		}

		@SuppressWarnings("unused")
		public Node(T value, Node left, Node right, Node parent) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

	private Node root = null;
	int size = 0;

	public BST() {
	}

	public T getElement(T toFind) {
		if (root == null || toFind == null)
			throw new NoSuchElementException();
		return getNode(toFind).value;
	}

	public Node getNode(T toFind){

		Node current = root;

		while (current != null) {
			if (toFind.compareTo(current.value) == 0) {
				break;
			}
			if (toFind.compareTo(current.value) > 0) {
				current = current.right;
			} else {
				current = current.left;
			}
		}
		return current;
	}
	public T successor(T elem) {
		Node result =successorNode(elem);
		if(result==null)
			return null;
		return result.value;
	}
	public Node successorNode(T elem) {
		Node current = getNode(elem);
		if(current==null)
			return null;
		if (current.right != null) {
			return minValue(current.right);
		}

		Node prev = current.parent;
		while (prev != null && current == prev.right) {
			current = prev;
			prev = prev.parent;
		}
		return prev;
	}

	public Node minValue(Node node) {
		Node current = node;

		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	public String fixString(String items) {
		String result = "";
		String[] toFix = items.split("\\)");
		for (int i = 0; i < toFix.length; i++) {
			if(toFix[i]!="") {
				result += toFix[i] + ")";
				if (i != toFix.length - 1)
					result += ", ";
			}
		}
		return result;
	}

	public String toStringInOrder() {
		if(size==0)
			return "";
		String result = inOrder(root);
		return fixString(result);
	}

	public String inOrder(Node node) {
		if (node == null) {
			return "";
		}
		String result = "";
		result = inOrder(node.left);
		result += node.value.toString();
		result += inOrder(node.right);
		return result;
	}

	public String toStringPreOrder() {
		if(size==0)
			return "";
		Stack<Node> nodes = new Stack<>();
		String result = "";
		nodes.push(root);
		while (!nodes.isEmpty()) {
			Node current = nodes.pop();
			result += current.value.toString();
			if (current.right != null) {
				nodes.push(current.right);
			}
			if (current.left != null) {
				nodes.push(current.left);
			}
		}
		return fixString(result);
	}

	public String toStringPostOrder() {
		if(size==0)
			return "";
		String result = "";
		Stack<Node> nodes = new Stack<>();
		if (root == null)
			return "";
		nodes.push(root);
		Node prev = null;
		while (!nodes.isEmpty()) {
			Node current = nodes.peek();

			if (prev == null || prev.left == current || prev.right == current) {
				if (current.left != null)
					nodes.push(current.left);
				else if (current.right != null)
					nodes.push(current.right);
				else {
					nodes.pop();
					result += current.value.toString();
				}
			} else if (current.left == prev) {
				if (current.right != null)
					nodes.push(current.right);
				else {
					nodes.pop();
					result += current.value.toString();
				}
			} else if (current.right == prev) {
				nodes.pop();
				result += current.value.toString();
			}

			prev = current;
		}

		return fixString(result);
	}

	public boolean add(T elem) {
		if (elem == null)
			return false;
		if(getNode(elem)!=null)
			return false;
		Node prev = null;
		Node current = root;
		while (current != null) {
			prev = current;
			if (elem.compareTo(current.value) > 0) {
				current = current.right;
			} else {
				current = current.left;
			}
		}
		Node e = new Node(elem);
		if (prev == null) {
			root = e;
		} else {
			e.parent = prev;
			if (elem.compareTo(prev.value) > 0) {
				prev.right = e;
			} else {
				prev.left = e;
			}
		}
		size++;
		return true;
	}

	public T remove(T value) {
		if(value==null)
			return null;
		Node prev=null;//y
		Node current=getNode(value);//z
		if(current==null)
			return null;
		if(current.left==null || current.right==null)
			prev=current;
		else 
			prev=successorNode(current.value);


		Node x=null;
		if(prev.left!=null)
			x=prev.left;
		else
			x=prev.right;

		if(x!=null)
			x.parent=prev.parent;

		if(prev.parent==null)
			root=x;
		else if(prev.parent.left!=null && prev.value.compareTo(prev.parent.left.value)==0)
			prev.parent.left=x;
		else
			prev.parent.right=x;
		if(prev.value.compareTo(current.value)!=0) {
			T elem = prev.value;
			prev.value = current.value;
			current.value=elem;
		}
		size--;
		return prev.value;
	}



	public void clear() {
		root = null;
		size = 0;
	}

	public int size() {
		return size;
	}
	public int height() {
		return height(root)-1;
	}

	public int height(Node node) {
		if (node==null) return 0;
		int LDepth = height(node.left);
		int RDepth = height(node.right);
		if (LDepth>RDepth) return (LDepth+1);
		else 
			return (RDepth+1);
	}
}

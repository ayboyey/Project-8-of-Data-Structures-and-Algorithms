package lab08;

import java.util.Scanner;

public class Document implements IWithName{
	public String name;
	public BST<Link> link;
	public Document(String name) {
		this.name=name.toLowerCase();
		link=new BST<Link>();
	}

	public Document(String name, Scanner scan) {
		this.name=name.toLowerCase();
		link=new BST<Link>();
		load(scan);
	}
	public void load(Scanner scan) {
		while(scan.hasNext()){
			String next = scan.next();
			if (next == null) break;
			if(next.equalsIgnoreCase("eod"))return;
			if(isCorrect(next)){
				next = next.substring(5).toLowerCase();
				Link newLink = createLink(next);
				if (newLink != null)
					link.add(newLink);
			}
		}
	}

	public static boolean isCorrectId(String id) {
		if (Character.isLetter(id.charAt(0)))
			return id.matches("[a-zA-Z0-9_]+");
		else
			return false;
	}
	public static boolean isCorrect(String id) {

		if(id.length()<6 || !id.substring(0,5).equalsIgnoreCase("link=")|| !(id.substring(5,6).matches("^[a-zA-Z]*$") && id.substring(5).matches("^[a-zA-Z0-9_()]*$")))
			return false;
		return true;
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	static Link createLink(String link) {
		String[] splitted = link.split("\\(");
		if(splitted.length > 2)
			return null;
		if(splitted.length == 2){
			if(!splitted[1].substring(splitted[1].length() - 1).equalsIgnoreCase(")"))
				return null;
			splitted[1] = splitted[1].substring(0, splitted[1].length() - 1);
			int weight;
			try {
				weight = Integer.parseInt(splitted[1]);
			}
			catch (NumberFormatException e){
				return null;
			}
			return new Link(splitted[0], weight);
		}else{
			return new Link(splitted[0]);
		}
	}

	@Override
	public String toString() {
		//TO DO
		String text = "Document: " + name;
		String values = link.toStringInOrder();
		if(values!="")
			text+='\n'+values;
		return text;
	}

	public String toStringPreOrder() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringPreOrder();
		return retStr;
	}

	public String toStringPostOrder() {
		String retStr="Document: "+name+"\n";
		retStr+=link.toStringPostOrder();
		return retStr;
	}

	@Override
	public int hashCode() {
		int result=0;
		int[] sequence = { 19,7,11,13,17};
		for(int i=0;i<this.name.length();i++) {
			int ascii = (int) name.charAt(i);
			result= result*(sequence[(i)%5]) +ascii;
		}
		return result;
	}

	@Override
	public String getName() {
		return name;
	}
}

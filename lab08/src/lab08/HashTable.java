package lab08;

import java.util.LinkedList;

public class HashTable{
	LinkedList<Document>[] arr; // use pure array
	private final static int defaultInitSize=8;
	private final static double defaultMaxLoadFactor=0.7;
	private int size;	
	private final double maxLoadFactor;
	public HashTable() {
		this(defaultInitSize);
	}
	public HashTable(int size) {
		this(size,defaultMaxLoadFactor);
	}


	@SuppressWarnings("unchecked")
	public HashTable(int initCapacity, double maxLF) {
		this.maxLoadFactor=maxLF;
		this.size=0;
		arr=new LinkedList[initCapacity];
		for(int i=0;i<arr.length;i++)
			arr[i]=new LinkedList<Document>();
	}

	public boolean add(Object elem) {
		if(elem.equals(null))
			return false;
		int index =indexHash(elem);
		size++;
		arr[index].add((Document) elem);
		double load=(double)size/arr.length;
		if((load)>=maxLoadFactor)
			doubleArray();
		return true;
	}

	private int mod (int n, int m) {
		return ((n%m)+m)%m;
	}
	public int indexHash(Object elem) {
		if(elem.equals(null) )
			return -1;
		int index = mod(elem.hashCode(),arr.length);
		return index;
	}

	@SuppressWarnings("unchecked")
	private void doubleArray() {
		LinkedList<Document>[] temp =new LinkedList[arr.length*2];
		for(int i=0;i<temp.length;i++)
			temp[i]=new LinkedList<Document>();
		for(int i=0;i<arr.length;i++) {
			for(int j=0;j<arr[i].size();j++)
			{
				Document elem =arr[i].get(j);
				temp[mod(elem.hashCode(),temp.length)].add((Document) elem);
			}
		}
		arr=temp;
	}


	@Override
	public String toString() {
		String result="";
		for(int i=0;i<arr.length;i++) {
			result+=i+": ";
			boolean first=true;
			for(Document link:arr[i]) {
				if(first)
					first=false;
				else
					result+=", ";
				result+=link.getName();
			}
			result+='\n';
		}
		return result;
	}

	public Object get(Object toFind) {
		if(toFind.equals(null))
			return null;
		LinkedList<Document> links =arr[mod(toFind.hashCode(),arr.length)];
		Document result = null;
		for(Document link:links) {
			if(link.hashCode()==toFind.hashCode()) {
				result=link;
				break;
			}
		}
		return result;
	}
}

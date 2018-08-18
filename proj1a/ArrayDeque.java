public class ArrayDeque<T> implements Deque<T>{
	private T[] items;
	private T frontPointer; //reference to the first index where there is an element
	private T backPointer;
	private int size;

	public ArrayDeque(){
		items = (T[])new Object [8];
		frontPointer = items[0];
		backPointer = items[0];
		size = 0;
	}

	@override
	public boolean isEmpty(){
		return (size == 0);
	}

	@override
	public int size(){
		return size;
	}

	@override
	public T get(int index){
		return items[index];
	}


	@override
	public void addFirst(T item){
		//to add to front, it means to add to the element space to the left of the pointer
		//how do we find the index of the pointer though?
		if (items.length == size) {
			T[] new_array = expand();
			allocate(items,new_array);
			items = new_array; //firstPointer and lastPointe missing
		}
			addFirstNotFull(item);
	}

	private void addFirstNotFull(T item) {
		int temp = getIndex(frontPointer);
		if (temp - 1 >= 0){
			items[temp - 1] = item;
		}
		else{
			items[items.length - 1] = item;
		}
		frontPointer = item;
		size += 1;
	}

	@override
	public void addLast(T item) {
		if (items.length == size) {
			T[] new_array = expand();
			allocate(items, new_array);
			items = new_array; //firstPointer and lastPointe missing
		}

		int temp = getIndex(backPointer);
		items[temp + 1] = item;
		backPointer = item;
		size += 1;
	}

	/*
	Is this version of size any good?

	public int size(){
		if (getFront() <= getBack()) {
			return getBack() - getFront() + 1;
		} else if (getFront()>getBack()) {
			int gap = getFront() - getBack() - 1;
			return items.length - gap;
		}
		else if (getFront() == null || getBack() == null){
			return 0;
		}
	}
	*/

	public int getIndex(T item){//Return the index of an element
		for (int i=0; i<items.length; i+=1){
			try {
				if (items[i] == item){
				return i;}
			}
			catch (RuntimeException e) {
				System.out.println("no such thing");
				};
			//error: program will shut down directly
		}//This function will take Linear Time though
	}//HOW DO I SOLVE THE "NO RETURN" ISSUE https://www.tutorialspoint.com/java/lang/java_lang_exceptions.htm

	@override
	public T removeLast(){
		int index = getIndex(backPointer);
		if (index == 0){
			backPointer = items[items.length-1];}
		else{
			backPointer = items[index-1];
		}
		//create a new T object to clone the T that is being removed:
		T t = items[index];
		items[index] = null;
		size -= 1;
		contract();
		return t;
		}

	@override
	public T removeFirst(){
		int index = getIndex(frontPointer);
		if (index == items.length-1){
			frontPointer = items[0];
		}else{
			frontPointer = items[index+1];
		}
		T t = items[index];
		items[index] = null;
		size -= 1;
		contract();
		return t;
		}

	private T[] expand() {//2X the original array size and return a new array
		T[] new_array = (T[]) new Object[2 * items.length];
		//T[] new_array = new (T[]) Object[2 * items.length];
		System.arraycopy(items, 0, new_array, 1, size());
		items = new_array;//items will now be referencing a new thing
		return new_array;
		}


	private boolean check_usage(int size, int len){
		if (size >= 16 && size/len < 0.25){
			contract();
			return false;
		}
		return true;
	}

	private int len = items.length;
	private void contract() {
		if (check_usage(size, len) != false) {
			return;
		} else {
			check_usage(size, len / 2 + 1);
		}
		T[] new_array = (T[]) new Object[len];
		allocate(items, new_array);
		items = new_array;
	}


	private void allocate(T[] original, T[] copy){
		if (getIndex(frontPointer) > getIndex(backPointer)){//need to copy the references to pointers as well

			//copy the first half:
			System.arraycopy(original,0,copy,0,getIndex(backPointer)+1);
			backPointer = copy[getIndex(backPointer)];//fixing backpointer to point to this new location in NEW ARRAY
			//copy the second half:
			//First find out the copy len:
			int len = original.length-1 - getIndex(frontPointer);
			//Destination position index:
			int dest_index = copy.length-1 - len;
			//copy the second set of values into new array:
			System.arraycopy(original,getIndex(frontPointer),copy,dest_index,len);
			frontPointer = copy[dest_index]; //assign frontPointer to point to this new location in this NEW array
		}
		if (getIndex(frontPointer) <= getIndex(backPointer)){
			System.arraycopy(original,getIndex(frontPointer),copy,0,original.length); //INDEX DOESN'T HAVE TO START AT 0 UNLESS FULL
			frontPointer = copy[0];
			backPointer = copy[getIndex(backPointer)];//take the index of backPointer in the Old array
		}
	}

	@override
	public void printDeque() {
		int front_index = getIndex(frontPointer);
		int i = front_index;
		while (items[i] != backPointer) {
			System.out.print(i + " ");
			i += 1;
			if (i > items.length - 1) {
				i -= items.length;
			}
		}
	}


			//compare size to length, deciding whether to call resize();
			//find the index of the firstPointer using i = getIndex();
			//if i is at the very front of the array (ie. i = 0
			//insert item at the very end of the array
			//if i is not the very first item (i!=0), insert @ i - 1

			//Need tp adjust firstPointer (by calling setFront)
		//HOW TO DO THIS IN CONSTANT TIME?





}











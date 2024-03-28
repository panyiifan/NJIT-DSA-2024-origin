package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E>{
    private Object [] itemArray;
    private int capacity;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private static final int DEFAULT_STACK_SIZE = 10;

public QueueImplementation() throws QueueAllocationException{
    this(DEFAULT_STACK_SIZE);
}

public QueueImplementation(int capacity) throws QueueAllocationException {
    if (capacity < 2) {
        throw new QueueAllocationException("Error!");
    }
    try {
        itemArray = new Object[capacity];  
        this.capacity = capacity;  
        head = 0;
        tail = 0;
        size = 0;
       } catch (OutOfMemoryError e) {  
           throw new QueueAllocationException("Error!");  
       }
    }

    @Override
    public int capacity() {
      return capacity;
    }

   @Override
   public void enqueue(E element) throws QueueAllocationException, NullPointerException {
      if (element == null){
        throw new NullPointerException("Error! The input element cannot be empty!");
      }
      if (size >= capacity){
        try {
            int Capacity2 = 2 * capacity;
            Object [] Array2 = new Object[Capacity2];
            int i = 0;
            while (i < size){
                if (head + i < capacity){
                    Array2[i] = itemArray[head + i];
                    i++;
                }else {
                    Array2[i] = itemArray[i - (capacity - head)];
                    i++;
                }  
            }
            itemArray = Array2;
            capacity = Capacity2;
            head = 0;
            tail = size;
        }catch (OutOfMemoryError e) {
            throw new QueueAllocationException("Error!");
        }
      }
      itemArray[tail] = element;
      if (tail == capacity-1){
        tail = 0;
      }else{
        tail = tail+1;
      }
      size++;
    }

   @SuppressWarnings("unchecked")
   @Override
   public E dequeue() throws QueueIsEmptyException {
      if (head == tail && size != capacity) {
         throw new QueueIsEmptyException("Queue is empty!");
      }  
      Object dequeueElement = itemArray[head];
      itemArray[head] = null;
      if (head == capacity-1){
        head = 0;
      }else{
        head++;
      }
      size--;
      return (E) dequeueElement;
    }

   @SuppressWarnings("unchecked")
   @Override
   public E element() throws QueueIsEmptyException {
       if (head == tail && size != capacity){
           throw new QueueIsEmptyException("Queue is empty!");
       }
       Object element = itemArray[head];
       return (E) element;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public void clear() {
    head = 0;
    tail = 0;
    size = 0;
   }

   @Override
   public boolean isEmpty() {
      return head == tail && size != capacity;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      int i = 0;
      while (i < size){
        if (head + i < capacity){
            builder.append(itemArray[head + i].toString());
        }else {
            builder.append(itemArray[i - (capacity - head)].toString());
        }
        if (i < size - 1) {
            builder.append(", ");
        }
        i++;
      }
      builder.append("]");
      return builder.toString();
    }
}

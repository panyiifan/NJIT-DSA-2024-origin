package oy.tol.tra;

public class StackImplementation<E> implements StackInterface<E> {

   private Object [] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;

   /**
    * Allocates a stack with a default capacity.
    * @throws StackAllocationException
    */
   public StackImplementation() throws StackAllocationException {
      this(DEFAULT_STACK_SIZE);
   }

   /** TODO: Implement so that
    * - if the size is less than 2, throw StackAllocationException
    * - if the allocation of the array throws with Java exception,
    *   throw StackAllocationException.
    * @param capacity The capacity of the stack.
    * @throws StackAllocationException If cannot allocate room for the internal array.
    */
   public StackImplementation(int capacity) throws StackAllocationException {
      if (capacity < 2) {
         throw new StackAllocationException("Error!");
      }
      try {  
         itemArray = new Object[capacity];  
         this.capacity = capacity;  
         currentIndex = -1;
      } catch (OutOfMemoryError e) {  
         throw new StackAllocationException("Error!");
      }
   }

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element == null) {
         throw new NullPointerException("Error! The input element cannot be empty!");
      }
      if (currentIndex >= capacity - 1) {
         try {
            int Capacity2 = 2 * capacity;
            Object [] Array2 = new Object[Capacity2];
            for(int i = 0; i < capacity; i++){
               Array2[i] = itemArray[i];
            };
            itemArray = Array2;
            capacity = Capacity2;
         } catch (OutOfMemoryError e) {
            throw new StackAllocationException("Error!");
         }
      }
      itemArray[currentIndex+1] = element;
      currentIndex++;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex == -1) {
         throw new StackIsEmptyException("Stack is empty!");
      }
      Object popElement = itemArray[currentIndex];
      itemArray[currentIndex] = null;
      currentIndex--;
      return (E) popElement;
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex == -1) {
         throw new StackIsEmptyException("Stack is empty!");
      }
      Object peekElement = itemArray[currentIndex];
      return (E) peekElement;
   }

   @Override
   public int size() {
      return currentIndex + 1;
   }

   @Override
   public void clear() {
      currentIndex = -1;
   }

   @Override
   public boolean isEmpty() {
      return currentIndex == -1;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      for (var index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}

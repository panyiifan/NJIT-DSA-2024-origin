package oy.tol.tra;

public class Algorithms {
    public static <T extends Comparable<T>> void sort(T [] array) {
        int i = 0;
        int j = 0;
        for(i = 0; i < array.length - 1; i++){
            for(j = 0; j < array.length - 1 - i; j++){
                if (array[j].compareTo(array[j+1])>0) {
                    T tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
            }
         } 
      }
        
    }
    
    public static <T> void reverse(T [] array) {
        int i = 0;
        while (i < array.length/2) {
            T temp = array[i];
            array[i] = array[array.length-i-1];
            array[array.length-i-1] = temp;
            i++;
        }
    }

    public static <T extends Comparable<T>> int binarySearch(T aValue, T [] fromArray, int fromIndex, int toIndex) {
        if (fromIndex > toIndex){
            return -1;
        }
        int mid = (fromIndex + toIndex)/2;
        if (aValue.compareTo(fromArray[mid]) == 0){
            return mid;
        }
        if (aValue.compareTo(fromArray[mid]) < 0){
            return binarySearch(aValue, fromArray, fromIndex, mid - 1);
        }else{
            return binarySearch(aValue, fromArray, mid + 1, toIndex);
        }   
    }

    public static <E extends Comparable<E>> void fastSort(E [] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSort(E [] array, int begin, int end) {
        if (begin < end){
            int pivot = partition(array,begin,end);
            quickSort(array, begin, pivot-1);
            quickSort(array, pivot+1, end);
        }
    }

     private static <E extends Comparable<E>> int partition(E [] array, int begin, int end) {
        int i = begin - 1;
        while (begin < end){
            if (array[begin].compareTo(array[end]) < 0){
                i++;
                E temp = array[i];
                array[i] = array[begin];
                array[begin] = temp;
            }
            begin++;
        }
        E temp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = temp;
        return i + 1;
    }

}

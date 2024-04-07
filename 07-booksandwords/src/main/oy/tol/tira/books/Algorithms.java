package oy.tol.tira.books;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class Algorithms {
    public static <T extends Comparable<T>> void sort(T [] array) {
        int i = 0;
        int j = 0;
        for(i = 0; i < array.length - 1; i++){
            for(j = 0; j < array.length - 1 - i; j++){
                if (array[j].compareTo(array[j+1])>0) {
                    swap(array, j, j+1);
            }
         } 
      }
        
    }
    
    public static <T> void reverse(T [] array) {
        int i = 0;
        while (i < array.length/2) {
            swap(array, i, array.length-i-1);
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
        quickSortHoare(array, 0, array.length - 1);
    }

    public static <E extends Comparable<E>> void quickSortHoare(E [] array, int begin, int end) {
        if (begin < end){
            int pivot = hoarePartition(array,begin,end);
            quickSortHoare(array, begin, pivot-1);
            quickSortHoare(array, pivot+1, end);
        }
    }

     private static <E extends Comparable<E>> int hoarePartition(E [] array, int begin, int end) {
        E pivot = array[begin];
        int i = begin - 1;
        int j = end + 1;
        while (true) {
            do {
                i++;
            } while (array[i].compareTo(pivot) < 0);
            do {
                j--;
            } while (array[j].compareTo(pivot) > 0);
            if (i >= j) {
                return j;
            }
            swap(array, i, j);
        }
    }

    public static <T> void swap(T[] array,int i,int j){
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <E extends Comparable<E>> int partitionByRule(E [] array, int count, Predicate<E> rule) {
        int index = 0;
        for (; index < count; index++) {
            if (rule.test(array[index])) {
                break;
            }
        }
        if (index >= count) {
            return count;
        }
        int nextIndex = index + 1;
        while (nextIndex != count) {
            if (!rule.test(array[nextIndex])) {
                swap(array, index, nextIndex);
                index++;
            }
            nextIndex++;
        }
        return index;
    }

    public static <T> void sortWithComparator(T[] array, Comparator<? super T> comparator) {  
        Arrays.sort(array, comparator);  
    } 

}

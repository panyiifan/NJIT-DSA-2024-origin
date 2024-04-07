package oy.tol.tira.books;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class BSTBookImplementation implements Book {
    private class TreeNode {
        TreeNode(String word) {
            this.word = word;
            this.count = 1;
        }
        String word;
        int count;
        TreeNode left, right;
    }

    private TreeNode root = null;
    private TreeNode[] nodes;
    private String bookFile = null;
    private String wordsToIgnoreFile = null;
    private WordFilter filter = null;
    private int uniqueWordCount = 0;
    private int totalWordCount = 0;
    private int ignoredWordsTotal = 0;
    private long loopCount = 0;

    @Override
    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        boolean success = false;
        if (checkFile(fileName)) {
            bookFile = fileName;
            if (checkFile(ignoreWordsFile)) {
                wordsToIgnoreFile = ignoreWordsFile;
                success = true;
            }
        }
        if (!success) {
            throw new FileNotFoundException("Cannot find the specified files");
        }
    }

    @Override
    public void countUniqueWords() throws IOException, OutOfMemoryError {
        if (bookFile == null || wordsToIgnoreFile == null) {
            throw new IOException("No file(s) specified");
        }
        // Reset the counters
        uniqueWordCount = 0;
        totalWordCount = 0;
        loopCount = 0;
        ignoredWordsTotal = 0;
        root = null;

        // Create the filter class to handle filtering.
        filter = new WordFilter();
        // Read the words to filter.
        filter.readFile(wordsToIgnoreFile);
        
        // Start reading from the book file using UTF-8.
        try(FileReader reader = new FileReader(bookFile, StandardCharsets.UTF_8)){
            int c;
            // Array holds the code points of the UTF-8 encoded chars.
            StringBuilder wordBuilder = new StringBuilder();
            while ((c = reader.read()) != -1) {
                // If the char is a letter, then add it to the array...
                if (Character.isLetter(c)) {
                    wordBuilder.append((char) c);
                } else if(wordBuilder.length() > 0){
                    String word = wordBuilder.toString().toLowerCase(Locale.ROOT);
                    if (!filter.shouldFilter(word) && word.length() >= 2) {
                        root = addToTree(root, word);
                        totalWordCount++;
                    } else {
                        ignoredWordsTotal++;
                    }
                    wordBuilder.setLength(0);
                }
            }
            if (wordBuilder.length() > 0) {
                String word = wordBuilder.toString().toLowerCase(Locale.ROOT);
                if (!filter.shouldFilter(word) && word.length() >= 2) {
                    root = addToTree(root, word);
                    totalWordCount++;
                } else {
                    ignoredWordsTotal++;
                }
            }
        }
    }

    private TreeNode addToTree(TreeNode node, String word) {
        if (node == null) {
            uniqueWordCount++;
            return new TreeNode(word);
        }
        int compare = word.compareTo(node.word);  
        if (compare < 0) {  
            node.left = addToTree(node.left, word);  
        } else if (compare > 0) {  
            node.right = addToTree(node.right, word); 
        } else {  
            node.count++;  
        }  
        return node;
    }

    @Override
    public void report() {
        if (root == null) {
            System.out.println("*** No words to report! ***");
            return;
        }
        System.out.println("Listing words from a file: " + bookFile);
        System.out.println("Ignoring words from a file: " + wordsToIgnoreFile);
        System.out.println("Sorting the results...");

        nodes = new TreeNode[uniqueWordCount];
        int filledSize = fillArray(root, nodes, 0);

        heapSort(nodes);
        System.out.println("...sorted.");

        for (int i = 0; i < filledSize; i++) {
            System.out.println(nodes[i].word + ": " + nodes[i].count);
        }
        System.out.println("The depth of the BST: " + maxDepth(root));
        System.out.println("Count of words in total: " + totalWordCount);
        System.out.println("Count of unique words:    " + uniqueWordCount);
        System.out.println("Count of words to ignore:    " + filter.ignoreWordCount());
        System.out.println("Ignored words count:      " + ignoredWordsTotal);
        System.out.println("\nInformation for BST implementation");
    }

    private int fillArray(TreeNode node, TreeNode[] array, int index) {
        if (node == null) {
            return index;
        }
        index = fillArray(node.left, array, index);
        array[index++] = node;
        return fillArray(node.right, array, index);
    }

    private void heapSort(TreeNode[] array) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            TreeNode temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private void heapify(TreeNode[] array, int n, int i) {
        int max = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left].count < array[max].count) {
            max = left;
        }
        if (right < n && array[right].count < array[max].count) {
            max = right;
        }
        if (max != i) {
            TreeNode temp = array[i];
            array[i] = array[max];
            array[max] = temp;
            heapify(array, n, max);
        }
    }

    private int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        } else {
            int leftDepth = maxDepth(node.left);
            int rightDepth = maxDepth(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }

    @Override
    public void close() {
        bookFile = null;
        wordsToIgnoreFile = null;
        root = null;
        if (filter != null) {
            filter.close();
        }
        filter = null;
    }

    @Override
    public int getUniqueWordCount() {
        return uniqueWordCount;
    }

    @Override
    public int getTotalWordCount() {
        return totalWordCount;
    }

    @Override
    public String getWordInListAt(int position) {
        if (nodes != null && position >= 0 && position < uniqueWordCount) {
            return nodes[position].word;
        }
        return null;
    }

    @Override
    public int getWordCountInListAt(int position) {
        if (nodes != null && position >= 0 && position < uniqueWordCount) {
            return nodes[position].count;
        }
        return -1;
    }

    private boolean checkFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists() && !file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

}

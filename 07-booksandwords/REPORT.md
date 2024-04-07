1. What is your hash function like for hash table solution (if you implemented hash table).

For the calculation of the hash value, I set the receiving string as input, multiplying the hash value by a prime 31 by traversing each character in the string, and adding the ASCII value of the current character to add the hash value. After the calculation is complete, the hash value is ensured within the size range of the hash table by modulo operation. If the calculated hash value is negative, it is converted to a positive number by adding the hash table length to ensure the validity of the index.
For dealing with hash conflicts, I use the chain address method. When a new word is inserted, the function first calculates its hash value and locates it to the appropriate slot. If there is already a node with the same word in the slot, the function updates the count for that node. If the same word is not found, the function inserts a new node at the head of the linked list in the slot, thus handling potential conflicts.


2. For binary search trees (if you implemented it), how does your implementation get the top-100 list?

When traversing to an empty node, create a new node and increase the unique word count. If the new word is smaller than the current node word, the search continues recursively in the left subtree; If it is larger than the current node word, it is searched in the right subtree. If the new word is the same as the current node word, the count of the current node is updated to reflect the frequency of the word occurrence.


3. What can you say about the **correctness** of your implementation? Any issues, bugs or problems you couldn't solve? Any idea why the problem persists and what could perhaps be the solution?

After many debugging and modification, no obvious errors have been found in the code. However, when dealing with a large number of files containing many words, the execution process can become slow. When I was testing with VSCODE, sometimes a pop-up window would give me the option to continue waiting or reopen the current file. However, the actual execution time of the test did not exceed the editor's indicated wait time, which indicates that it may be related to the editor's performance or resource management.


4. What can you say about the **time complexity** of your implementation? How efficient is the code in reading and managing the words and their counts? How efficient is your code in getting the top-100 list? Which sorting algorithm are you using? What is the time complexity of that algorithm?

(1) Time complexity:
BST: The time complexity of a binary search tree depends on the balance of the tree. Ideally, insert, find, and delete operations all have O(log n) complexity. However, in the worst case, when the tree degenerates into a linked list, the complexity of these operations can reach O(n). For example, if the number of word occurrences is counted, the time complexity is O(n) because each node needs to be accessed once.
Hash Table: Hash table inserts have an average time complexity of O(1), but reach O(n) in the event of a collision.

(2) Efficient in reading and managing the words and their counts:
BST: The read operation is O(log n) efficient. Because for any given word, we need to start at the root node and traverse the tree by comparing words until we find the word or determine that it doesn't exist. Managing the word count is done directly during the insert process, so it is as efficient as the insert operation.
Hash Table: On average, the time complexity of insert, delete, and search operations in the hash table is O(1), which is very efficient.

(3) Efficient in getting the top-100 list:
BST: Efficiency depends on the size and balance of the tree, usually get the first 100 words do not need to traverse the whole tree, once you collect enough words should stop traversing, so it is very efficient.
Hash Table: Efficiency depends on the sorting algorithm used, I use heapsort, which has a time complexity of O(nlogn), which is good for medium-size data sets.

(4) Sorting algorithm and time complexity used:
Both implementations use the heapsort algorithm for sorting with a time complexity of O(nlogn).


5. What did you find the **most difficult things** to understand and implement in this programming task? Why?
Heap sorting algorithm is difficult, because heap sorting involves the understanding and application of the special data structure of heap, and heap itself has some complex characteristics, such as the relationship between parent and child nodes, heap maintenance and so on. It is also difficult to optimize hash tabularity, because it requires us to deeply understand the inner workings of hash functions and effectively deal with hash conflicts.


6. What did you learn doing this?
Through this study, I learned the construction of hash table and the selection of hash function, and the handling strategy of hash conflict. Understand the construction and traversal methods of trees, master the implementation of insert, delete and search operations, and the principle and application of balancing binary search trees. In addition, I also have a deep understanding of heap sorting algorithm, can write and use.
StringProcessor
by Tony Ni, Sindel Donaldson, Eugene Choi, Shuzo Katayama

OVERVIEW
The StringProcessor is a group of different classes which process strings in
different ways. WordCounter solves Task 1: counting the number of characters,
words, and lines in one or more files. FileSearch solves Task 2: counting the
number of occurernces of a given String in a file. DistinctSearch solves Task 3: 
counting the number of distinct Strings in a file. And, SpellCheck solves Task 4:
finding the number of 'misspelt' words in a file. All of these classes implement 
a baseline and multithreaded implementation.

In order to process our results, we will measure the time it took for the 
basline and multithreaded results to compute, and divide it to get a result 
for the improvement of the multithreaded process. The correctness condition
was tested by using a third party tool (wordcounter.io) for completing the different 
tasks, and then checking those numbers against the baseline implementation. We then
checked to see if our multithreaded result was the same as the baseline result. 

Overall, the tasks to solve are generally simple tasks, with varying degrees of 
complexity, and with these different tasks, we deployed multithreading to see 
to what degree these tasks could be sped up. 

USAGE
To test this program, simply compile and run Tester.java from a command line. The 
results will print to the command line as the tests run. 

If you would like to add your own test cases aside from the 6 that we have already 
provided, please add a .txt file into the folder 'tests'. The only code that needs 
to be modified when adding a new test file is in Tester.java, on line 74. Here, 
in the String array 'tests', add the String you would like to search for in your
own text file to run Task 2.  

TASKS and RESULTS

TASK 1. Count the number of characters, words, and lines in one or more text files.
- For this task, we aimed to count the number of characters, words, and lines in a 
  To do this, we impleemented both a baseline implementation and 
  multithreading approach to the task of counting the different areas. For the 
  results, we will split this task into three separate subtasks (counting characters,
  counting words, and counting lines) that will be performed and measured separately.

  The baseline approach implements a rudimentary while loop approach to this problem
  by looping through the entire file using the nextLine function. The multithreaded
  approach converts the entire file into a string array using the readAllLines method
  and then has each thread work on a subsection of the string array.

  The problem with Task 1 was that there was very little room to implement 
  multithreading. In our final version, we never completed the task, and it is 
  commented out in the main method of Tester.java, so that its results are not 
  printed. The task did work on a couple of occasions in finding the number of 
  characters in a file, using multithreading, and here we can see just how poorly
  this task performed under multithreading.

  TASK 1, Test case: 6. Complete Works of Shakespeare.txt
  |-----------|------------|------------------|---------|
  |   count   |    type    |   improvement    | passed? |
  |-----------|------------|------------------|---------|
  |   5333743 |  Characters|         0.006712 |    true |

  For Task 1, the task of finding characters, words, and lines in a file was 
  implemented in an elegant and simple way, which performed reasonably well. However,
  the multithreaded solution was much more complex and convaluted, and ultimately 
  performed much worse if it was correct at all.

  We believe that this was the result of two things. First, the simple elegance of the
  baseline solution proved to be already very efficient as a task, and the overhead
  required to convert the file into a new format, create threads, run the threads,
  concurrently tally up the results, and then compute a final answer proved to be 
  much larger than the theoretical benefit gained from multithreading. Secondly, we 
  hypothesized that the access pattern of reading a file would benefit greatly 
  from a linear access pattern. As we learned in class, accessing a file linearly 
  is very efficient on modern OS's because it is most likely to pre-fetch data in a 
  linear order. This combined with the fact that the tasks are extremely simple made 
  it so that Task 1 performed extremely well without parallelization.

TASK 2. Find all occurences of a given string in one or more files.
- For this task, we will aim to find all the occurences of a given string in some text 
  by optimizing the search processes using multithreading. To create a benchmark for 
  this task, we will compare our multithreading solution to a baseline implementation 
  which will simply read every string in sequence. 

  The baseline approach implements runs through the file line by line, using the 
  BufferedReader, and searches for the target string by running through each String
  contained within the line. The multithreaded approach put every line into a 
  large array, and then gave a section of the array to each thread. Each thread then
  ran through their section in linear order and attempted to find the target String
  in the current line. Each thread then put their final count in a shared array, 
  and the results from the array were summed for the final result.

  This implementation performed the task correctly, and comparing the results showed
  that multithreading performed very poorly compared to the baseline implementation.

  TASK 2: Search for a String
  TASK 2, Test case: 1. Fitnessgram Pacer Test.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |         5 |           test|            0.000170 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 2. Multithreading Notes.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |        11 | multithreading|            0.000704 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 3. Bee Movie Screenplay.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |       265 |            bee|            0.002038 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 4. Cars 2 Screenplay.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |       386 |        McQueen|            0.001671 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 5. The Bible.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |     61680 |            the|            0.002269 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 6. Complete Works of Shakespeare.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |         7 |         madcap|            0.002491 |    true |
  |-----------|---------------|---------------------|---------|

  One point here about the experiment is that it searched for words that we thought 
  was interesting for each file. In order to get comparable results for the experiment
  we decided to attempt to search for the same String with a similar distribution 
  in most English texts: 'the'. Here are our new results:

  TASK 2: Search for a String
  TASK 2, Test case: 1. Fitnessgram Pacer Test.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |         8 |            the|            0.000725 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 2. Multithreading Notes.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |       140 |            the|            0.005135 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 3. Bee Movie Screenplay.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |       865 |            the|            0.002155 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 4. Cars 2 Screenplay.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |      1022 |            the|            0.002173 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 5. The Bible.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |     61680 |            the|            0.003042 |    true |
  |-----------|---------------|---------------------|---------|

  TASK 2, Test case: 6. Complete Works of Shakespeare.txt
  |-----------|---------------|---------------------|---------|
  |   count   |     string    |     improvement     | passed? |
  |-----------|---------------|---------------------|---------|
  |     27643 |            the|            0.002692 |    true |
  |-----------|---------------|---------------------|---------|

  The results first show that for this task, multithreading also actually slowed
  the performance down. Considering all of the results, we saw a significant decrease
  in performance after applying multithreading, on the order of 100. We believe that 
  the slowdown can be explained by the fact that the non-parallelized solution was 
  simple, and applying multithreading created more complications than was necessary.
  The test results showed that the overhead of converting the file to an array, then
  creating, running, and synchronising the threads proved to be much larger than
  the theoretical speedup gained by parallelism. Task 2 also performed very well
  without using parallelization.

TASK 3. Count the number of distinct words
- Task 3 aimed to count the number of distinct words in one or more files. This task
  has a fairly straightforward goal, but is more complicated compared to the previous
  two tasks. We implemented both a baseline implementation nad multithreaded 
  implementation of this task. The results simply showed the number of distinct 
  words found in the file, the time improvement, and if the multithreaded implementation
  produced the correct answer.

  The baseline approach implements a rudimentary while loop in order to solve this
  task in a sequential way. For each line in the file, the baseline implementation
  split the line into a List of Strings, then searched every word within that line. 
  For every word, the method searched through a separate List of Strings which 
  contained every distinct word. If the current word was found, then the method
  moved on to the next word, but if it was not found, then the current word was 
  added to the distinct word list. After going through the entire file, the length
  of the distinct word list was measured to obtain a final result.

  The multithreaded approach split the file into an array of lines, and assigned
  each thread a range of the array to work on. Each thread was also passed an
  ArrayBlockingQueue of Stringsto keep track of all of the distinct words in the file.
  Each thread searched line by line, word by word, and for each current word, checked
  to see if the word was in the ArrayBlockingQueue using the .contains() method. 
  If it was not in the ArrayBlockingQueue, it was added. At the end, the number of 
  words in teh ArrayBlockingQueue was summed for the final result.

  This implementation performed the task correctly only sometimes. Because of some
  issue in concurrency and collisions, the count would be sometimes correct, and 
  sometimes one or two words off (we never observed this disparity rise above 4).
  Because the discrepancy was so small, we decided that the results we obtained in
  terms of speedup would stil be useful data.

  TASK 3: Count the Distinct Words
  TASK 3, Test case: 1. Fitnessgram Pacer Test.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |        64 |            0.234130 |   false |
  |-----------|---------------------|---------|

  TASK 3, Test case: 2. Multithreading Notes.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |       600 |            2.643136 |   false |
  |-----------|---------------------|---------|

  TASK 3, Test case: 3. Bee Movie Screenplay.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |      3115 |            2.937940 |    true |
  |-----------|---------------------|---------|

  TASK 3, Test case: 4. Cars 2 Screenplay.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |      3434 |            2.845209 |    true |
  |-----------|---------------------|---------|

  TASK 3, Test case: 5. The Bible.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |     12606 |           22.450227 |   false |
  |-----------|---------------------|---------|

  TASK 3, Test case: 6. Complete Works of Shakespeare.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |     28123 |           49.903185 |    true |
  |-----------|---------------------|---------|

  These results show that the discrepancy in count occured sporatically, and after
  running this task over and over again, we were not able to find a pattern in the
  incorrectness. This led us to conclude that there was a problem with the 
  parallel implementation given the inconsistent incorrectness.

  However, the results showed that for this task, multithreading was able to speed up
  the task of finding distinct words by several factors. The speed up was especially
  significant in the two largest files: The Bible and the Complete Works of Shakespeare.
  This speedup, we theorise, is attributable to the fact that finding the distinct 
  words in a text file is a more difficult task than task 1 and task 2. Because of 
  this increased complexity in the task, multithreading was able to significantly
  improve performance for this task.

  A theoretical implementation we thought of for this task (too late in the project
  timeline to be able to implement) was to have producer threads and a consumer thread.
  The idea behind this implementation was to have each producer thread keep a local
  version of their distinct words list, and as threads finished, the consumer thread
  would take the finished threads' distinct words list and check those words against
  a master distinct words list to compile the final distinct words list. This would
  probably produce a correct answer, but we theorized that it would not be as efficient
  because the threads would need to check for distinct words twice.
  
TASK 4. Count the number of 'misspelt' words 
- For this task, we aimed to count the number of 'misspelt' words in a text file. We 
  put 'misspelt' in quotes because the method for doing this is to see if each word 
  is in a List of words in the English dictionary, and if it is not, then it would
  be counted as 'misspelt'. Because of this unsophisticated and uncomplex method
  to finding 'misspelt' words, words such as proper nouns and uncommon names would be
  counted as 'misspelt'. 

  The baseline approach first converts the English dictionary into a gigantic
  List of Strings. Then it goes through the file line by line, and checks each word 
  it finds against the List of Strings of words in the dictionary. If that String is 
  not found, then the word is counted as misspelt and the count is incremented.

  The multithreaded approach also converts the English dictionary into a list of 
  Strings, and then gives each thread a copy of that List. The file to check is then 
  converted into an array of lines, and each thread is given a chunk of the array
  to work on. Each thread then checks the Strings in their chunk against the List 
  of words in the dictionary. The threads add their result to a shared array of results
  and the final answers are summed to get a result.

  For Task 4, we implemented a fully functional solution that provided the correct
  answer for every run.

  TASK 4: Spell Check
  TASK 4, Spell Check: 1. Fitnessgram Pacer Test.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |         1 |            0.603404 |    true |
  |-----------|---------------------|---------|

  TASK 4, Spell Check: 2. Multithreading Notes.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |       285 |            0.921549 |    true |
  |-----------|---------------------|---------|

  TASK 4, Spell Check: 3. Bee Movie Screenplay.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |      1310 |            3.257479 |    true |
  |-----------|---------------------|---------|

  TASK 4, Spell Check: 4. Cars 2 Screenplay.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |      6974 |            7.027030 |    true |
  |-----------|---------------------|---------|

  TASK 4, Spell Check: 5. The Bible.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |     17230 |          153.831202 |    true |
  |-----------|---------------------|---------|

  TASK 4, Spell Check: 6. Complete Works of Shakespeare.txt
  |-----------|---------------------|---------|
  |   found   |     improvement     | passed? |
  |-----------|---------------------|---------|
  |    151271 |          231.613172 |    true |
  |-----------|---------------------|---------|

  The results of this experiment show that, the improvement in very small test cases 
  was not great, and because of the small size of the file, the improvement provided
  by multithreading did not beat the overhead it took to set up the multithreaded 
  implementation. However, the multithreaded runtimes improved significantly as the
  file size increased. The largest files, the Bible and the Complete Works of 
  Shakespeare saw runtime improvements in the order of hundreds. 

  We believe that this improvement, similar to Task 3, can be attributed to the relative
  complexity of finding the misspelt words in a file. Because task 4 is more complex
  compared to tasks 1 and 2, the speed up of parallelism is much more visible, and 
  makes the overhead of creating threads and setting up parallelism worth it.

Conclusion

- In this series of experiments, we found a clear divide in performance between the 
  simpler tasks, Task 1 and Task 2, and the more complex tasks, Task 3 and Task 4.
  For the very simple tasks, the overhead of creating threads and data structures
  that threads can use made the program less efficient compared to the sequential 
  implementation, even with large file sizes. However, the complex tasks, like 
  finding unique words or checking the file against a dictionary, saw a significant
  performance boost in terms of time efficiency when multithreading was applied.

Test cases:
1. Fitnessgram Pacer Test: small case
- Source: https://genius.com/Leger-and-lambert-the-fitnessgram-pacer-test-annotated
2. Multithreading Notes: medium case
- Source: https://willrosenbaum.com/teaching/2021s-cosc-273/notes/multithreading/
3. Bee Movie Screenplay: first large case
- Source: http://screenplaysandscripts.com/script_files/B/beemovie.pdf
4. Cars 2 Screenplay: second large case
- Source: https://imsdb.com/scripts/Cars-2.html
5. The Bible: Very large case
- Soure: https://corpus.canterbury.ac.nz/descriptions/
6. The Complete Works of Shakespeare: Extremely large case
- Source: https://ocw.mit.edu/ans7870/6/6.006/s08/lecturenotes/files/t8.shakespeare.txt


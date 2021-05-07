StringProcessor
by Tony Ni, Sindel Donaldson, Eugene Choi, Shuzo Katayama

OVERVIEW
The StringProcessor is a group of different classes which process strings in
different ways. The WordCounter solves Task 1: counting the number of characters,
words, and lines in one or more files. FileSearch solves Task 2: counting the
number of occurernces of a given String in a file. Both of these classes 
implement a baseline and multithreaded implementation

In order to process our results, we will measure the time it took for the 
basline and multithreaded results to compute, and divide it to get a result 
for the improvement of the multithreaded process. The correctness condition
was tested by using a third party tool (wordcounter.io) for completing the different 
tasks, and then checking those numbers against the baseline implementation. We then
checked to see if our multithreaded result was the same as the baseline result.

In future versions of this program, we plan to add one or more classes with 
different functionalities in terms of file processing. This includes a class
which can find the number of distinct words in a file. We also hope to further
optimise the tasks we already have, in terms of time performance. 

TASK 1. Count the number of characters, words, and lines in one or more files.
- For this task, we aim to count the number of characters, words, and lines in a 
  text file. To do this, we will implement both a baseline implementation and 
  multithreading approach to the task of counting the different areas. For the 
  results, we will split this task into three separate subtasks (counting characters,
  counting words, and counting lines) that will be performed and measured separately.

  The baseline approach implements a rudimentary while loop approach to this problem
  by looping through the entire file using the nextLine function. The multithreaded
  approach converts the entire file into a string array using the readAllLines method
  and then has each thread work on a subsection of the string array.

  * NO working implementation for PROOF OF CONCEPT
    for the proof of concept implementation, we have the basic structure of the file
    reader and tests to be done using IO. However, there are still bugs to work out 
    in reading and processing the files in a way that is consitent and correct.

TASK 2. Find all occurences of a given string in one or more files.
- For this task, we will aim to find all the occurences of a given string in some text 
  by optimising the search processes using multithreading. To create a benchmark for 
  this task, I will compare our multithreading solution to a baseline implementation 
  which will simply read every string in sequence, and to the grep command. 

  * WORKING implementation for PROOF OF CONCEPT
    for the proof of concept implementation, the basic structure is implemented,
    and the multithreaded implementation gives consistent and correct resutls when
    compared with the baseline implementation. From here, we will continue to attempt
    to improve the optimization, and attempt to incorporate the grep command into 
    our implementation.

COMPILING and RUNNING:
to run this program please compile and execute Tester.java. The results will be 
displayed on the command line. 


Test cases:
1. Fitnessgram Pacer Test: small case
- Source: https://genius.com/Leger-and-lambert-the-fitnessgram-pacer-test-annotated
2. Multithreading Notes: medium case
- Source: https://willrosenbaum.com/teaching/2021s-cosc-273/notes/multithreading/
3. Bee Movie Screenplay: first large case
- Source: http://screenplaysandscripts.com/script_files/B/beemovie.pdf
4. Cars 2 Screenplay: second large case
- Source: https://imsdb.com/scripts/Cars-2.html

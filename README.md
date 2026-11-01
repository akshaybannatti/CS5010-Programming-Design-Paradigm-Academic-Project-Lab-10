# Objectives
 
The objectives of this lab are: 

   * Use the decorator pattern to dynamically add new behavior to existing code.

   * Use the decorator pattern for profiling.
 
# 2 Introduction
 
The decorator pattern can be used to enhance existing functionality or add new functionality to existing code, thus changing its behavior. As the decorator pattern uses composition instead of inheritance, it has the following advantages: 

   * Behavior can be changed on a per-object basis. Inheritance creates a new type, and affects all objects of that type.

   * Several changes in behavior can be combined. Doing so with inheritance is messy and creates many new classes.

   * Extra behavior can be added, and removed at runtime. This makes it possible to use existing objects, then add some behavior change and use them, then revert back to the original object, etc. dynamically.
 
One may want to change behavior of an object for many reasons. In this lab we will explore one such application: profiling. 

# 3 Problem
 
Some starter code has been provided to you. This code implements a pill counter. A pill counter is a simple object, not unlike a step counter. It is used to count the number of pills added to a bottle as part of a larger packaging control system. An implementation of a pill counter has been provided, that additionally appends all changes to it in a log file. It has been implemented this way so that even if the program crashes midway, the log file will be updated to the last successful operation. 

A test has been provided to you, as an illustration of how this object is being used. In reality, the code in the test is representative of how this object is used in conjunction with a system that interfaces with actual conveyer belts that is bottling pills. You may think of the usage this way: the machine that dispenses pills in a bottle is equipped with a sensor that senses each time pills are dropped into the bottle. The sensor sends an event to a system that uses the pill counter. 

You have inherited this code. Much of this code has evolved over a period of years. You notice that there are serious performance issues with it. This can be seen directly by running the provided test, and observing how long it takes to run. **This code (not the test) is part of a critical legacy system. So you have no power to change the code in any way. You cannot even see its source code!**. And yet, you must determine what is causing the performance issue, and a way to fix it. 

# 4 Profiling
 
Profiling is a useful tool to evaluate the performance of a program. In its simplest form, profiling is observing how much time and resources are consumed by parts of a program, to detect bottlenecks. Profiling is especially useful when code is used heavily or from so many places that it is difficult to accurately ''step through" it, or it is not always possible mimic its actual usage in a test. 

Most importantly profiling must be done transparently: you cannot change the code that you are profiling, otherwise you may not be profiling accurately. Notice that the debugger in IntelliJ is doing precisely this (but to aid debugging): it allows you to monitor/watch variables and add/remove breakpoints without changing any of your code. 

# 5 What to do
 
## 5.1 Question to ponder
 
Run the given test, and look at the generated log file but not the given implementation. What is your intuition for why the test runs so slowly? Share with the person next to you before moving on. 

You decide to employ a decorator to add profiling to the existing pill counter object. 

## 5.2 Part 1: Decorator
 
Write a decorator `PillCounterDecorator` for a pill counter. This decorator must implement the `PillCounter` interface and be composed by an object of it. Add a constructor that takes a `PillCounter` delegate. Finally, implement all the required methods by calling to the corresponding methods in the delegate. 

Write a test that mimics the given test, but uses the given pill counter implementation decorated by this decorator. The test should run exactly as the given test, because our decorator is a simple ''pass-through". 

## 5.3 Part 2: Monitoring the add count
 
Extend the above decorator in a new class `PillAddMonitor`. This class will monitor the number of times pills are added to a pill counter before it is reset (this would monitor how many times the pill counter is used while filling one bottle). The same pill counter may be reset many times, so this decorator saves all add counts. Each time the pill counter is reset, this new class will start a new count. Finally, add a new method to this class that returns the add counts in a list. 

Now write a test in `PillCounterTest` that uses this decorator in the same scenario as the given test. However this test should also print the list of add counts in the end. While printing is not part of testing, this will give you a quick view of what is happening. **Note that this test will take a bit longer than the original test, because in addition to bad performance, your test is also printing to console! This is OK for now, as you are just trying to find out what is happening.** 

## 5.4 Questions to ponder
 
Look at the generated log file, and then what your new test printed. Share your insights with the person next to you, and the TA. What is the reason for the bad performance, and how can you address it without changing the given implementation? 

## 5.5 Part 3: Batch adding
 
One way to speed things up may be to consolidate the calls to `addPill`. Write another new class `PillBatchAddCounter` that extends the original decorator. This class ''lazy-adds" pills: pills must be added only sometime before the pill count is retrieved, or the pill count is reset. 

Accordingly, this decorator will simply record how many pills are added when `addPill` is called, but not forward it to its delegate. When `getPillCount()` is called, first call the delegate's `addPill` method with the cumulative number of pills added, and then reset its pill add count. The `reset()` method should work in the same way. 

Write a test identical to the one provided to you. However this test should use the original pill counter decorated with the batch-adding decorator. This test should pass. 

## 5.6 Questions to ponder
 
Compare the runtime of your latest test with the original one. Do you see a performance difference? What fix would you recommend for the original system to improve performance permanently? 

# 6 Possible extensions
 
Combine your batch-adding decorator with the add-monitoring decorator in a way that shows you that the number of calls to `addPill` were reduced. 

Try to use the decorator pattern to separate the logging ability from the pill counting ability in the given code (this will require changing the given code).

# 7 Submission

Push your code to your repository for the lab. Be sure to run your own tests before pushing to the repo. 
The following files are required. Note you need to work in default (not named) packages.

- 'src/main/java/PillCounter.java' 
- 'src/main/java/LoggingPillCounter.java' 
- 'src/main/java/PillAddMonitor.java' 
- 'src/main/java/PillCounterDecorator.java' 
- 'src/main/java/PillBatchAddCounter.java' 
- 'src/test/java/PillCounterTest.java'
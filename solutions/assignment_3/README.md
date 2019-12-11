# Scala assignment

In this assignment, we work with the university lecture scheduling dataset, 
but using the Scala programming language.

You are given a dataset as a CSV file: `schedule.csv`.

It consists of 8 columns:

- Course title
- Course code
- Day of the week
- Start hour 
- Start minute
- End hour 
- End minute
- Room

## ✎ Counting

**Write a program part1.scala to count.**

- Count the number of distinct course codes in the file.
- Count the total distinct rooms in the file.
- The total number of lectures scheduled for CSCI courses.

    
Your program must be executed as:

	scala part1 schedule.csv

It must print one count per line.  
A sample output should be like as follows, assuming that there are
460 distinct courses, 130 rooms, and 72 entries corresponding
to CSCI courses:

> ```
> $ scala part1 schedule.csv
> 
> 460
> 130
> 72
> ```

## ✎ List schedules of courses.

**Write a program, part2.scala, that will list the _sorted_ schedules of courses.**

Your program must be executed as:

	scala part2 schedule.csv csci3055u csci4110u csci4100u

It must display all the **sorted** schedule for the courses.

**Note**:

- The program must be able to handle _multiple_ command line arguments.
- Each course code given in the argument does **not** contain space, and may be in either upper or lowercase.  However, the CSV file contains course code in uppercase with space.
Your program needs to handle the difference in course code formats.
- **You must sort** the output the schedules in an order by: (1) sort by day of week (M, T, W, R, F), and sort by starting
time (hour and minute), and finally by the course code.

Here is a sample output.

> ```
> $ scala part2 schedule.csv csci3055u csci4100u
> 
> Mobile Devices,CSCI 4100U,M,8,10,11,0,Simcoe Building J123-A
> Programming Languages,CSCI 3055U,T,12,40,14,0,University Building A1 UA2240
> Mobile Devices,CSCI 4100U,W,12,40,14,0,Simcoe Building J102
> Mobile Devices,CSCI 4100U,W,8,10,11,0,Simcoe Building J123-A
> Programming Languages,CSCI 3055U,R,11,10,12,30,University Building A1 UA2240
> Mobile Devices,CSCI 4100U,R,18,40,21,30,Simcoe Building J123-A
> Mobile Devices,CSCI 4100U,F,11,10,12,30,Simcoe Building J102
> Mobile Devices,CSCI 4100U,F,18,40,21,30,Simcoe Building J123-A
> ```

## ✎ Find free room in `University Building`

Write a program, part3.scala, that will find a free room with the room name that starts with `University Building` given the day of week, start time, and end time.

Your program must be executed as:

	scala part3 schedule.csv M 10:00 12:00

It must print a list of rooms which are available during this time.  
Namely, there is no class scheduled in the room after (and excluding) 10:00am and before (and excluding) 12:00pm on Monday.  Print one room per line.

The room names must be sorted alphabetically,
and any duplicates are to be removed.

Here is a sample output:

> ```
> $ scala part3 schedule.csv W 13:00 14:00
> 
> University Building A2 UA1420
> University Building A2 UA1540
> University Building A9 ENG1045
> University Building A9 ENG3035
> University Building A9 ENG3040
> University Building A9 ENG3045
> ```

## Submission

Submit your program files. Template code is provided under the `src` directory.

```
src
├── part1.scala
├── part2.scala
└── part3.scala
```

You can provide additional helper classes if necessary.

A Makefile is provided with the targets `compile` and `run`
to execute the scala files.

**The submission must be done through Github.**

## Marking

| Part   | Grade |
|--------|-------|
| Counting | 30 |
| List schedule | 30 |
| Find free room | 30 |
| Coding style and programming structure | 10 |


# Data processing with functional programming

In this assignment, you will be guided to perform data processing using functional programming.  During the process, we will touch on how to structure Clojure projects into different namespaces.

# Data processing

We provide a CSV file [1] that lists the cities in Canada along with their province and population.
We will perform a number of data processing tasks in Clojure.  The code will be organized as a Clojure project with
the following directory structure:

```
.
├── main.clj
└── src
    ├── ca_cities.clj
    ├── query_1.clj
    └── query_2.clj                     
```

There are _three_ namespaces 

- `ca-cities`, 
- `query-1` and 
- `query-2` 

as implemented by the files in `src/`.

# Complete the `ca_cities.clj`

    ; returns a file reader
    (defn reader []
        (io/reader "data/ca.csv"))
    
    ; Parse a string as a float
    (defn parse-float [s]
        (Float/parseFloat s))
    
    ; Parse a string as an integer
    (defn parse-int [s]
        (Integer/parseInt s))

## parse-record

You will need to implement a function that transforms a single
line into a city record.  

    ; Parse city record
    (defn parse-record [line]
        ...)
        
A city record is a hash-map of the form:

    {:name ...
     :lat ...
     :long ...
     :country ...
     :province ...
     :pop ...}

## cities

You will need to implement a function that returns all the city
records in the CSV file.  You function **must** ignore the first line.

    ; Returns a sequence of city records
    (defn cities []
        ...)

## city

You will need to implement a function that retrieves the city record
by its name.

    ; Returns a city record by name
    (defn city [name]
        ...)

## distance

You will need to implement a function that computes the distance
given two city records.  Since the data only provides the latitude and longitude
of cities, you need to make use of the _Haversine formula_: https://en.wikipedia.org/wiki/Haversine_formula

    (defn distance [city-record1 city-record2]
        ;; returns the distance in KM between the two cities
        ...)
        
**Note**:

- Use the Earth radius of: 6371 km
- Don't forget to convert the latitude and longitude to radians first before using the Haversine formula
        
# Complete `query_1.clj`

The query is to find all distinct pairs of cities that are at most 600 KM apart, and each with minimum of 500,000 in population.

You need to start a new namespace which requires the `ca-cities` namespace.

    (ns query-1
        (:require [ca-cities :as ca]))

Define a helper functions in `query-1` to determine if a city record is large.

    (defn large? [city]
        ...)
        
Define another helper function in `query-1` to determine if two cities are close.

    (defn close? [city1 city2]
        ...)
        
Finally, using functions in `ca` and the helper functions, implement the query.

    (defn closest-city-pairs []
        ...)
        
The function **must** return a sequence of vectors with:

    [name1 name2 distance]

where `name1 < name2`, and the distance between them is shown.

# Complete `query_2.clj`

In this query, we will compute the aggregate population in each province as a list
of `[province-name population]` sorted from the smallest province to the largest.

First, start with a namepsace `query-2` that requires `ca-cities`.

    (ns query-2
        (:require [ca-cities :as ca]))
        
## total-population

Define a function that aggregates the total population from a list of cities.

    (defn total-population [cities]
        ...)

## provincial-population

Define a function that returns a list of `[province-name population]` pairs
sorted from the smallest province to the most populated province.

    (defn provincial-population []
        ...)
        
# References
[1] https://simplemaps.com/data/ca-cities
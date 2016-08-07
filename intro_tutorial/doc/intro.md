# Introduction to intro_tutorial

This lein playground was used to work through an introduction to Clojure tutorial. The tutorial can be found [here](http://java.ociweb.com/mark/clojure/article.html).

To run the Clojure REPL issue the command `lein repl` in the root of this repo.

## Functional Programming
  - functional programming emphasizes the use of first class functions that are pure
  - **"pure functions"** are functions that return the same output given the same inputs and do not depend on state that can change over time
  - pure functions are easier to understand, debug, and test
  - pure functions have no side effects i.e. changing global state, performing I/O, etc.
  - state is maintained in the values of function parameters saved on the stack (often via recursion), no globals in the heap
  - side effects in practice are unavoidable so the key is to limit side effects, clearly identify them, and avoid scattering them throughout the code
  - **"first-class functions"** are functions that can be held in variables and passed around
  - **"higher-order functions"** are functions that accept other functions as arguments
  - data structures are often immutable to support concurrency

## Clojure Overview
  - Clojure is a dynamically-typed, functional programming language that runs on the JVM and provides interoperability with Java, a lisp dialect
  - ClojureScript compiles Clojure code to JavaScript
  - JVM provides many benefits including stability, perfomance, security, many libraries, etc.
  - each **"operation"** in Clojure is implemented as either a function, macro or special form
  - special forms are recognized by the Clojure compiler and not implemented in Clojure source code, a relatively small number exist and new ones cannot be implemented
  - Clojure provides lots of support for operating on **"sequences"**, sequences are created efficiently because they are persistant data structures
  - a sample function call of foo, passing arguments 1, 2 => `(foo 1 2)`
  - everything in lisp has this function call **"form"**
  - name functions using all lowercase, seperating words by hyphens e.g. `(foo-bar-baz 2)`
  - defining a function has form (function prints string):

  ```
    (defn hello [name]
      (println "Hello," name))
  ```
  - clojure uses lazy evaluation which supports efficiently creating infinte collections
  - Clojure code is processed in three phases: read-time (list DS), compile-time (byte-code) and run-time (code execution), functions invoked at run-time, macros expanded at compile-time

## Getting Started
  - Clojure code for your own library and application projects will typically reside in its own directory and will be managed by [Leiningen](http://leiningen.org/index.html)
  - create new project: `lein new my-project`, run REPL (first changing into the new my-project directory): `lein repl`, new app: `lein new app my-app`

## Clojure Syntax
  - data and code have the same representation: lists of lists
  - `(a b c)` is a call to function named `a`, passing arguments `b` and `c`
  - to make the statement data instead of code simply add a quote i.e. `'(a b c)`
  - special cases provide syntactic sugar but require the user knows what the cases are
  - lisp uses prefixes notation e.g. to add you do `(+ a b c)`
  - binary operators from other languages are Lisp functions that aren't restricted to two operands
  - Clojure uses parantheses where Java uses curly braces
  - characters are represented with `\` i.e. `\a`, `\tab`, etc.
  - more example Clojure code:
 
  ```
  (def vowel? (set "aeiou")) ; create set of characters

  (defn pig-latin [word] ; defines a function
    ; word is expected to be a string
    ; which can be treated like a sequence of characters.
    (let [first-letter (first word)] ; assigns a local binding
      (if (vowel? first-letter) ; first-letter is a character
        (str word "ay") ; then part of if
        (str (subs word 1) first-letter "ay")))) ; else part of if

  (println (pig-latin "red"))
  (println (pig-latin "orange"))
  ```
  - Clojure supports all common data types (bool, decimals, ints, strings, chars, etc.)
  - symbols are used to name things and names are scoped to a namespace
  - keywords begin with a colon and are used as unique identifiers i.e. keys in maps and enumerated values, `:red`, `:green`
  - write short functions, avoid deep nesting, acoid passing anonymous functions to other anonymous functions

## REPL
  - REPL stands for read-eval-print loop
  - to inititate the REPL use `lein repl`, `user=>` will be shown indicating you are in the default `user` namespace
  - to view documentation for a function, macro or namespace, enter `(doc name)`
  - to load and execute the forms in a source file, enter `(load-file "file-path")`, these files have a **.clj** extension

## Vars
  - Clojure provides bindings to vars, which are containers bound to mutable storage locations
  - function parameters are bound to vars that are local to the function
  - the `def` special form binds a value to a symbol
  - the `let` special form creates bindings to vars that are bound to the scope within the statement
  - the `binding` special macro gives new, thread-local values to existing global bindings throughout the scope's thread of execution

## Collections
  - Clojure provides the collection types list, vector, set and map, all of them are immutable, heterogeneous and persistent
  - Clojure collections are immutable, there are no functions that modify them, persistent data structures help efficiently create new collections
  - some collection functions include: `count`, `reverse`, `map`, `apply`, `first`, `last`, `filter`, `every?`, `some`

### Lists
  - lists are ordered collections of items, ideal when new items will be added to or removed from the front
  - lists are not efficient (O(n)) for finding items by index and there is no efficient way to change items by index
  - create list like: `(def xs '("ab" "bc" "cd"))`, `(some #(= % "cd") xs)` is `true`
  - `conj` returns a new list (takes list first), `cons` returns a new sequence (takes list second)
  - `peak` and `pop` can help lists function as stacks, they work at the head of the list

### Vectors
  - vectors are also ordered collections of items, ideal when new items will be added to or removed from the back
  - `conj` (adds at back) is more efficient than `cons` (adds at front) when operating on vectors
  - usage of vectors is preferred over lists due to `[ ]` syntax
  - `get` retrives elements by index and can optionally be supplied a default value (also available on maps) i.e. `(get xs 1 "unknown")`
  - `assoc` can be used to create a new vector, replacing a given element at a given index
  - `subvec` can be used to slice, `peek` and `pop` operate on the tail of vectors

### Sets
  - sets are collections of unique items, preferred over lists and vectors when duplicates are not allowed and items do not need to be maintained in the order in which they were added
  - Clojure supports sorted and unsorted sets, items in sorted sets must be comparable
  - sets can be used as functions of their items, will return the item or nil
  - other functions include `disj` for removal and the `clojure.set` namespace

### Maps
  - maps store associations between keys and their corresponding values where both can be any kind of object, keywords are often used for map keys
  - can use `get` or directly call map passing a key to retrieve values
  - other map functions include `keys`, `vals`, `contains?` (checks keys), `assoc` (replace/augment), `dissoc` (key removal), `doseq`, `select-keys`, `get-in` (traverse nested maps), `conj`
  - the `->` macro ("thread" macro) calls a series of functions passing the result of each on to the next (pipe), `-?>` also available to stop when `nil`
  - `reduce` takes a function of two args, optional value, and collection
  - `assoc-in` is available on maps for nested updating

## Defining Functions
  - use `defn` to create functions, `defn` takes name, optional doc string, vector of args, and body
  - use `declare` when you need a forward declaration of a function i.e. `(declare foo)`
  - functions defined with the `defn-` macro are private, only visible in the namespace in which they are defined
  - functions can take a variable number of parameters, optional parameters must appear at the end, gathered into a list, `(defn [a & xs] ...)`, `xs` is a list
  - often it is useful for a function body to call the same function with a different number of arguments in order to provide default values for some of them when defining multiple bodies/param lists
  - use `#(...)` or `(fn [...] ...)` to define anonymous functions, `fn` defined functions can have any number of expressions and use a name if recursion is desired
  - `#(...)` form can only have one expression, a single param can be referred to with `%`, multiple params by `%1`, `%2`, etc.
  - `defmulti` and `defmethod` macros are used together to define a multimethod (ad-hoc polymorphism)
  - `_` can be used as a placeholder for arguments a function doesn't use (callbacks for instance)
  - `comp` is used to compose funcitons, functions are called from right to left
  - `partial` can be used to achieve partial application of functions
  - `memoize` is avaiable to create a caching version of a function

## Java Interoperability
  - Clojure programs can use all Java classes and interfaces
  - classes in the java.lang package can be used without importing them, classes in other packages can be used by either specifying their package when referencing them or using the import function
  - constant example: `java.util.Calendar/APRIL`, static method example: `(Math/pow 2 4)`, class method example: `(def calendar (GregorianCalendar. 2008 Calendar/APRIL 16))`, instance method example: `(.get calendar Calendar/MONTH)`
  - the `doto` macro is used to invoke many methods on the same object
  - all Clojure functions implement both the [java.lang.Runnable](http://docs.oracle.com/javase/6/docs/api/java/lang/Runnable.html) interface and the [java.util.concurrent.Callable](http://docs.oracle.com/javase/6/docs/api/java/util/concurrent/Callable.html) interface
  - all exceptions thrown by Clojure code are runtime exceptions, `try`, `catch`, `finally`, and `throw` available

## Conditional Processing
  - `if` statement syntax: `(if condition then-expr else-expr)`
  - `when` and `when-not` macros provide alternatives to `if` when only one branch is needed
  - `if-let`, `when-let` also available
  - `condp` functions like the case statement, takes binary predicate, and value to act as second arg in predicate followed by a number of value expression pairs, returns first match or a default
  - `cond` macro takes any number of predicate expression pairs, returns corresponding result of first true predicate

## Iteration
  - the `dotimes` macro executes the expressions in its body a given number of times, can include an enumerating binding
  - the `while` macro executes the expressions in its body while a test expression evaluates to `true`
  - the `for` and `doseq` macros perform list comprehension:

  ```
    (def cols "ABCD")
    (def rows (range 1 4)) ; purposely larger than needed to demonstrate :while

    (doseq [col cols :when (not= col \B) ; cols consumed slower i.e. A1, A2, etc.
            row rows :while (< row 3)]
      (println (str col row)))
  ```

## Recursion
  - recursion occurs when a function invokes itself either directly or indirectly through another function that it calls, terminates at some base case (empty list, value becomes 0)
  - recursive calls can result in running out of memory if the call stack becomes too deep, Java doesn't currently support TCO and neither does Clojure
  - the `loop`/`recur` idiom turns what looks like a recursive call into a loop that doesn't consume stack space:
  
  ```
    (defn recur-map [f coll]
      "Apply a function to each item in a list, returning a new vector"
      (loop [c coll xs []]
        (if (empty? c)
          xs
          (recur (pop c) (conj xs (f (first c)))))))
  ```
  - the `recur` special form doesn't support mutual recursion where a function calls another function which calls back to the original function, `trampoline` (function) does support mutual recursion
  - `reduce` can be used in place of `recur`/`loop` in some cases but may not be as efficient

## Predicates
  - Clojure provides many functions that act as predicates, used to test a condition, return a value that can be interpreted as true or false
  - values `false` and `nil` are interpreted as `false`, value `true` and every other value, including zero `0`, are interpreted as `true`
  - many predicate functions that perform reflection, for obtaining information about an object other than its value (`list?`, `string?`, `vector?`, etc.)
  - usual suspects: `<`, `>`, `not=`, `=`, `==`, `and`, `or`, `not`, `nil?`, `true?`, `even?`, etc.

## Sequences
  - sequences are logical views of collections, many things can be treated as sequences
  - many Clojure functions return a lazy sequence, a sequence whose items can be the result of function calls that aren't evaluated until they are needed
  - some functions that return lazy sequences: `map`, `filter`, `take-while`, `concat`, etc.
  - many ways to force the evaluation of items in a lazy sequence, for example, calling `last` causes every item to be evaluated
  - `dorun`, `doall`, `doseq` force evaulation of all items, `for` cretes a new lazy sequence, `doseq` preferred when forcing eval is needed
  - lazy sequences make it possible to create infinite sequences:
  
  ```
    (defn consumer [seq]
      ; Since seq is a local binding, the evaluated items in it
      ; are cached while in this function and then garbage collected.
      (println (first seq)) ; evaluates (f 0)
      (println (nth seq 2))) ; evaluates (f 1) and (f 2)

    (consumer (map #(* % 2) (iterate inc 0))) ; can also use (range)
  ```

## Input/Output
  - the `clojure.java.io` library makes many uses of the `java.io` classes easier
  - predefined, special symbols `*in*`, `*out*` and `*err*` are set to **stdin**, **stdout** and **stderr** by default
  
  ```
    (binding [*out* (java.io.FileWriter. "my.log")]
      (println "This goes to the file my.log.")
      (flush))
  ```
  - the `print` function prints the string representation of any number of objects, with a space between each one, to the stream in the special symbol `*out*`, `println` adds a `\newline` character at end
  - `printf` is like `print` but consumes a format string

## Destructuring
  - destructuring can be used in the parameter list of a function or macro to extract parts of collections into local bindings:

  ```
    (defn foo [[n1 _ n3]] (+ n1 n3)) ; destructures list input, _ is a wildcard
  ```
  - ampersand can be used to capture remaining items in a collection e.g. `[[n1 n2 & rest]]`
  - use `:as` to retain reference to dereferenced collection e.g. `[[n1 _ n3 :as coll]]`
  - similarily can destructure maps (non-keywords are the local bindings of keyword values): `[{june :june july :july august :august :as all}]` or `[{:keys [june july august] :as all}]`

## Namespaces
  - Clojure groups things that are named by symbols in namespaces, include Vars, Refs, Atoms, Agents, functions, macros and namespaces themselves
  - symbols are used to assign names to functions, macros and bindings, partitioned into namespace
  - always a default namespace initially set to "user" (can access `clojure.core`), stored in symbol `*ns*`
  - the `ns` macro changes the current namespace and more including making all the symbols in the `clojure.core` namespace available in the new namespace
  - to access items not in the default namespace they must be namespace-qualified, done by preceding a name with a namespace name and a slash e.g. `clojure.string/join`
  - `require` function loads Clojure libraries, takes one or more quoted namespace names, names libraries must still be namespace-qualified e.g. `(require 'clojure.string) (clojure.string/join "$" [1 2])`
  - `alias` can be used to reduce length of namespace symbols
  - `refer` function makes all the symbols in a given namespace accessible in the current namespace without namespace-qualifying them, exception thrown if current namespaced symbol will be clobbered
  - `use` is a shortcut for the combo of `require` and `refer`
  - `ns` changes default namespace and supports `:require`, `:use`, `:import`, `:as`, `:only`:
  
  ```
    (ns com.ociweb.demo
      (:require [clojure.string :as su])
      ; assumes this dependency: [org.clojure/math.numeric-tower "0.0.1"]
      (:use [clojure.math.numeric-tower :only (gcd, sqrt)])
      (:import (java.text NumberFormat) (javax.swing JFrame JLabel)))
  ```
  - `create-ns` function creates a new namespace, but doesn't make it the default, `def` function defines a symbol in the default namespace with an optional initial value, `intern` function defines a symbol in a given namespace
  - `namespace` function returns the namespace of a given symbol or keyword, `all-ns` function returns a sequence of the currently loaded namespaces, `ns-interns` function returns a map containing all the symbols defined in a given, currently loaded namespace

### Fine Print
  - `Symbol` object has a `String` name and a `String` namespace name (called ns), but no value
  - A `Var` object has references to a `Symbol` object (called `sym`), a `Namespace` object (called `ns`) and an `Object` object which is its "root value" (called `root`)
  - `Namespace` objects have a reference to a `Map` that holds associations between `Symbol` objects and `Var` objects (named mappings)
  - in Clojure, the term **"interning"** typically refers to adding a `Symbol`-to-`Var` mapping to a `Namespace`

## Metadata
  - Clojure metadata is data attached to a symbol or collection that is not related to its logical value
  
  ```
    (def card2 ^{:bent true} card2) ; adds metadata at read-time
    (def card2 (with-meta card2 {:bent true})) ; adds metadata at run-time
    (println (meta card2)) ; -> {:bent true}
    (println (= card1 card2)) ; still same value despite metadata diff. -> true
  ```
  - `:tag` is a string class name or a Class object that describes the Java type of a Var or the return type of a function, can improve performance with "type hints"
  - some metadata is automatically attached to Vars by the Clojure compiler like `:ns`, `:name`, `:file`, `:macro`, etc.

## Macros
  - macros are used to add new constructs to the language, they are code that generates code at read-time
  - while functions always evaluate all their arguments, macros can decide which of their arguments will be evaluated
  - `if`, `and` and `or` are not implemented as functions because they need to "short-circuit"
  - `defmacro` is used to create macros:
  
  ```
    (defmacro around-zero [number negative-expr zero-expr positive-expr]
      `(let [number# ~number] ; so number is only evaluated once
        (cond
          (< (Math/abs number#) 1e-15) ~zero-expr 
          (pos? number#) ~positive-expr
          true ~negative-expr)))
  ```
  - back-quote (a.k.a. syntax quote) at the beginning of the macro def prevents everything inside from being evaluated unless it's unquoted, this means the contents will appear literally in the expansion, except items preceded by a tilde (in this case, `number`, `zero-expr`, `positive-expr` and `negative-expr`)
  - since macros don't evaluate their arguments, unquoted function names (`~`) can be passed to them and calls to the functions with arguments can be constructed
  - macro names cannot be passed as arguments to functions, macro calls are processed at read-time

## Concurrency
  - concurrency is a property of systems in which several computations are executing and overlapping in time, and potentially interacting with each other
  - primary challenge of concurrency is managing access to shared, mutable state
  - all data is immutable unless explicitly marked as mutable by using the reference types `Var`, `Ref`, `Atom` and `Agent`
  - the `future` macro runs a body of expressions in a different thread using one of the thread pools (`CachedThreadPool`) that are also used by `Agents`, useful for long running expressions whose results aren't needed immediately, result is obtained by dereferencing the object returned by `future`
 
  ```
    (def my-future (future (f-prime 2))) ; f-prime is called in another thread
  ```
  - `pmap` and other useful parallel processing functions are availble in `clojure.parallel`

## Reference Types
  - reference types are mutable references to immutable data, four references types in Clojure: **Vars**, **Refs**, **Atoms** and **Agents**
  - all typs hold any object, dereference with `deref` or `@`, support watchers which are **Agents** that get notified about specific value changes

### Vars
  - **Vars** are references that can have a root binding that is shared by all threads and can have a different value in each thread
  - providing value is optional, if no value Var is "unbound"
  - use `binding` or `set!` to create new thread local binding of an existing Var

### Refs
  - **Refs** are used to ensure that changes to one or more bindings are coordinated between multiple threads, can only be modified inside a transaction
  - if no exceptions are thrown before the end of the transaction is reached then changes to Refs made in the transaction are committed, the in-transaction changes become visible outside the transaction
  - if an exception is thrown from any code executed inside the transaction the transaction rolls back, the in-transaction changes are discarded
  - use actions and Agents to support side effects when using refs, action is sent once to an Agent to perform once transaction commits regardless of how many times transaction retried
  - create ref and keep access to it: `(def name (ref value))`
  - `dosync` macro starts a transaction that continues while the expressions in its body are evaluated
  - can only use `ref-set`, `alter`, and `commute` in transaction block
  - sample Ref **Validator**:

  ```
    ; Note the use of the :validator directive when creating the Ref
    ; to assign a validation function which is integer? in this case.
    (def my-ref (ref 0 :validator integer?))
  ```

### Atoms
  - **Atoms** provide a mechanism for updating a single value that is far simpler than the combination of Refs and STM, not affected by transactions
  - use `reset!`, `compare-and-set!` (verify safe update) and `swap!` (helpful wrapper around `compare-and-set!`) to change and update Atoms

### Agents
  - **Agents** are used to run tasks in separate threads that typically don't require coordination
  - run actions to update agent value, only one action at a time will be run on a given Agent, `agent` function creates a new Agent
  - the `send` function dispatches an action to an Agent and returns immediately instead of waiting for the action to complete
  - if the `send` or `send-off` functions are invoked from inside a transaction, the action isn't actually sent until the transaction commits
  - the `await` function takes any number of Agents and blocks the current thread until all actions that thread has dispatched to those Agents have completed (can't be used inside transaction)
  - calling `shutdown-agents` is necessary to allow the JVM to exit in an orderly manner because the threads in the Agent thread pools are not daemon threads

### Watchers
  - `add-watcher` and `remove-watcher` function have been removed, `add-watch` and `remove-watch` functions (work differently) have been added

## Compiling
  - Clojure source files executed as scripts are compiled to Java bytecode at runtime
  - follow steps outlined [here](http://java.ociweb.com/mark/clojure/article.html#Compiling) for proper compilation
  - AOT compiled Clojure functions can be called from a Java application if they are marked as static

## Automated Testing
  - primary automated testing framework for Clojure is the test library included in Clojure core, `clojure.test`:
  
  ```
    (use 'clojure.test)
    
    (deftest reverse-test
      (is (= [3 2 1] (reverse [1 2 3]))))
    
    (run-tests) ; Run all the tests in the current namespace.
  ```
  - `with-test` and `are` test helper macros also available for further testing support
  - Clojure also provides an `assert` macro that can throw exceptions
  - create fixtures to do set-up/tear-down, tow varieties: those that wrap each test, and those that wrap entire test run:
  
  ```
    (defn fixture-1 [test-function] ; Called once for each test
      ; Val of current test function is test-function
      ; Perform setup here.
      (test-function)
      ; Perform teardown here.
    )

    ; fix-1 set-up, fix-2 set-up, ONE TEST, fix-2 tear-down, fix-1 tear-down
    (use-fixtures :each fixture-1 fixture-2)
    ; fix-1 set-up, fix-2 set-up, ALL TESTS, fix-2 tear-down, fix-1 tear-down
    (use-fixtures :once fixture-1 fixture-2)
  ```
  - Clojure ships with its own suite of tests in its test subdirectory, `cd` to the directory containing the Clojure src and test directories and enter `ant test` to run them

## Editors and IDEs
  - Vim includes [VimClojure](http://www.vim.org/scripts/script.php?script_id=2501), a helpful plugin for Clojure syntax

## Desktop Apps
  - Clojure can be used to create Swing-based GUI applications

## Web Apps
  - popular web frameworks include [*Compojure*](http://github.com/weavejester/compojure/tree/master) and [*Ring*](https://github.com/ring-clojure/ring)

## Databases
  - use `clojure.java.jdbc` for help with managing database connections and running transactional queries

## Libraries
  - many additional helpful third-party libraries can be found at [Clojure Contrib](http://dev.clojure.org/display/doc/Clojure+Contrib)
 
 
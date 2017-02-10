# Off to wonderland

This repo was used as a playground while I worked through the book **Living Clojure**.

To begin a Clojure REPL use `lein repl` as the root of this repo.

## 1. The Structure of Clojure
  - Clojure code is made up of expressions that get evaluated to yield one final result
  - **literals** are the simplest expressions and evaluate to themselves i.e. `42`, `"hello"`, `1/3`
  - Clojure supports ratios (both num and denom MUST BE integers) and does not force decimal truncation i.e. `1/3`, `1/9`, etc.
  - ratios are reduced whenever possible i.e. `(+ 1/8 1/8)` => `1/4`, `4/2` => `2`, etc.
  -  in Clojure operators used prefix notation, they come before their arguments i.e. `(/ 1 3)`, `(+ 4 5)`, etc.
  -  **keywords** are symbolic identifiers whose names start with a colon and have special properties i.e. `:jam`, `:my-keyword`
  -  to create **chars** in Clojure use `\` followed by the character i.e. `\j`, `\a`, `\tab`, etc.
  -  Clojure simple literal types: integers, decimals, ratios, characters, strings, booleans, keywords, nil
  - `nil` represents the absence of a value in Clojure
  - Clojure expressions can be nested the inner expression is evaluated first i.e. `(+ 8 ( - 3 1))`

### Collections
  - Clojure supports multiple types of collections including **lists**, **vectors**, **maps**, **sets**
  - **lists** are collections of things that come in a given order, can be heterogenous
  - to create a **list** simply place a quote in front of a set of parantheses placing your data inside the parans i.e. `'(1 2 3)`, `(-3 \a :hi)`, etc.
  - commas can be used to seperate the elements in parans but it is **idiomatic** to exclude commas when creating lists
  - think of lists in two parts: **1.** the first element **2.** everything else (this model is useful for recursion)
  - use `first` to grab the head of a list, use `rest` to grab a sublist that does not contain the head
  - calling `first` on an empty list yields `nil`
  - use `cons` to build lists, the `cons` function takes two arguments, the first is an element we want to add to a list, the second is the list itself, `cons` adds elements to the **head** of the list i.e. `(cons 5 '())` => `'(5)`, `(cons 5 nil)` => `'(5)`, `(cons 2 (cons 1 '(0)))` => `'(2 1 0)`
  - the `list` function is also available for creating lists i.e. `(list 1 2 3)` => `'(1 2 3)`
  - lists work well when you want to work with the head of a list, accessing other elements requires efficient indexing, something lists do not offer
  - use **vectors** when you need fast index access to collection elements
  - to create a **vector** simply use square brackets i.e. `[:jar1 1 2 :jar2 "eggs"]`
  - `first` and `rest` work for vectors, the `nth` function provides fast index access to the nth element in the vector, `last` efficiently grabs the vectors tail
  - `nth` and `last` are availble on lists but take `O(n)` in the worst case as they have to start traversing from the head (`n` = num elements, lists are linked)
  - all Clojure collections (lists, vectors, etc.) are **immutable** and **persistant**
  - with immutability, when `cons`, etc. is performed on a collection the original collection is unmodified/unmutated, a new collection is created and returned by the function call
  - smart creation of these new collections is possible due to persistance and structural sharing (new collections are partly a shallow copy of the original)
  - `first`, `rest`, `last`, `count`, `conj`(add element most naturally - front for list, back for vector) all available on Clojure collections, `(conj '(1 2) 3 4)` => `'(4 3 1 2)`, `(conj [\a \b] \c \d)` => `[\a \b \c \d]`
  - **maps** are collections of key-value pairs in Clojure and are recognizable by their curly braces i.e. `{:jam1 "strawberry" :jam2 "rasberry"}` (Clojure ignores commas and treats them as white space but can be used if helpful)
  - the `get` function retrieves values out of maps by key, a default can be provided i.e. `(get {:a 1} :b "not found")` => `"not found"`
  - alternatively use a map key as a function to retrieve a value as this is considered more idiomatic (unless being explicit is most clear), again defaults can be provided i.e. `(:b {:b \c} nil)` => `\c`
  - `keys` function returns the keys of a map, `vals` function returns the values
  - use the `assoc` function to update key-value pairs in a map (remember a **new** collection is created efficiently as collections are immutable and persistant) i.e. `(assoc {:a 1} :b 2 :c 3)` => `{:a 1 :b 2 :c 3}`
  - the `dissoc` function removes key-calue pairs by key from a map i.e. `(dissoc {:a 1 :b 2 :c 3} :b :c)` => `{:a 1}`
  - the `merge` function can be used to combine maps i.e. `(merge {:a 1 :b 2} {:b 3 :c 4} {:c 5 :d 6})` => `{:a 1 :b 3 :c 5 :d 6}`
  - **sets** are useful when working with collections that contain no duplicate elements and can be recognized by `#{...}` (leading # before curly braces)
  - `#{1 1 2}` throws an error because duplicates were passed to a set
  - handy set functions can be found in `clojure.set` namespace and include `union`, `intersection`, `difference` i.e. `(clojure.set/difference #{1 2 3} #{2} #{1})` => `#{3}`
  - like maps `get` can be used on sets to retrieve elements (default can be provided), or can use the set or element itself as the function to retrieve elements i.e. `(:a {:b :c} 2)` => `2`
  - `contains?` function can be used to see if a set holds a specific element i.e. `(contains? #{1 2 3} 1)` => `true`
  - `conj` is used to add elements to the set, `disj` is used to remove elements i.e. `(disj #{1 2 3} 1 2)` => `#{3}`

### Lists are Clojure's Heart
  - `("apple" "banana")` => error trying to call strign as funciton, `'(+ 1 1)` => `'(+ 1 1)` (creates a list, does not execute function)
  - all Clojure **code** is **data**, all Clojure code is simply lists of data

### Symbols and Binding
  - Clojure **symbols** refer to values, when a symbol is evaluated it returns the thing it refers to
  - `def` allows for the naming of values so they can be referenced anywhere in code i.e. **global** variable(symbol binds value indirectly through a var)
  - `(def dev "Alice")` creates a var object in the default namespace (`dev` is the symbol, `"Alice"` is the value)
  - a fully qualified var name includes the namespace followed by a forward slash `/` and the var name i.e. `user/dev` (`user` is the namespace, `dev` is the var)
  - `def` creates global variables, if a **local** binding is desired use the `let` construct to make variables available only in the `let` body/context
  - bindings for the `let` expression are in vector form as pairs of symbols and values (code as data) i.e. `(let [a 1 b 2] (- b a))` => `1` (`a` and `b` do not exist outside of `let` assuming no global bindings, error thrown if referenced outside `let`)

### Creating Functions
  - `defn` is similar to `def` except that it creates vars for custom functions, `defn` takes 3 args: 1. function name 2. vector of function's params 3. function body
  - like built-in functions, custom defined functions are called with parans
  - when defining functions, the vector of params can be empty i.e. `(defn follow-me [] "where to?")`, `(follow-me)` => `"where to?"`
  - to create **anonymous** functions use `fn` (usually for functions that are very short lived) i.e. `(fn [] (str "hey" " there"))`, `((fn [] "a"))` => `"a"`
  - `defn` is syntactic sugar for using `def` to bind an anonymous function created with `fn` i.e. `(def foo (fn [] (+ 1 1)))` == `(defn foo [] (+ 1 1))`
  - `#(...)` can be used as short-hand when creating anonymous functions i.e. `(#(+ % 1) 2)` => `3`
  - use `%` inside the `#(...)` short-hand when only one arg passed in, `%1`,`%2`, etc. can be used to refer to the first, second, etc. function param when multiple are given i.e. `(#(str "uh" %1 %2) "kad" "man")` => `"uhkadman"`
  - **namespaces** are organized and controlled access to vars
  - when using `def` and `defn` the vars are created in the default `user` namespace
  - it is possible to create new namespaces and switch to them with `ns`, any vars created will now be created in the new namespace i.e. `(ns alice.favfoods)`
  - use the var `*ns*` to check the current namespace, `*` is called an *earmuff* and are used intentionally for things expecting rebindings
  - when creating a new var it is directly available or can be fully qualified i.e.(`*ns*` == `alice.favfoods`) `(defn foo [] 1)` => `(foo)` == `(alice.favfoods/foo)` == `1`
  - use `require` to load other namespaces from **libs**
  - first way to use `require` is by passing it a namespace, vars in the namespace are then accessible via fully qualified name i.e. `(require 'alice.favfoods)` (note leading `'`), `(alice.favfoods/foo)` == `1`
  - second way to use `require` is with an alias leveraging the `:as` keyword i.e. `(require '[alice.favfoods :as af])`, `(af/foo)` == `1`
  - it is common to use `require` nested in an `ns` call i.e. `(ns wonderland (:require '[alice.favfoods as af]))`
  - the last way to use `require` is with the `ns` call and `:refer` and `:all`, this will load all the symbols in a given namespace and make them directly accessible, this can be risky as it is hard to know where names come from and can cause collision i.e. `(ns wonderland (:require '[alice.favfoods :refer :all]))`
  - most times code will use `require` aliasing libs with `:as`, exceptions are tests where it is common to use `clojure.test` and the namespace subject directly
  - end chapter 1 example:

  ```
    (ns wonderland
      (:require '[clojure.set :as s])
    
    (defn common-fav-foods [foods1 foods2]
      (let [food-set1 (set foods1)
            food-set2 (set foods2)
            common-foods (s/intersection food-set1 food-set2)]
        (str "Common foods: " common-foods)))
    
    (common-fav-foods [:jam :jelly :butter]
                      [:apples :bananas :jam])
    ;; -> "Common foods: #{:jam}"
  ```

## 2. Flow and Functional Transformations
  - an **expression** is code that can be evaluated for a result i.e. `(first [1 2 3])`
  - a **form** is *valid* expression that can be evalauted (syntactically/logically sound) i.e. `(first [:a :b :c])` (syntax is correct)

### Controlling Flow with Logic
  - Clojure supports the boolean data types `true` and `false`
  - test if an expression is `true` with `true?`, and use `false?` to test if an expression is `false`
  - in Clojure  `nil` represents the absence of a value, to test for `nil` use `nil?`
  - use `not` to negate a a boolean value or check for `false` i.e `(not true)` == `false`, `nil` is treated as `false` with `not` i.e. `(not nil)` == `true`
  - `not` used with anything else that is not logically `false` will return `false` i.e. `(not "hi")` == `false`
  - use `=` to check if two values are the same i.e. `(= :drink :drink)` == `true`, `(= 1 "lo")` == `false`, `(= [1 2] (1 2))` == `true` 
  - use `not=` to check if two values are not the same i.e. `(not= 1 \a)` == `true`
  - `false` and `nil` are the only falsy values, `nil` is treated as logically `false`
  - use `empty?` to check if a vector or other collection contains no elements
  - in Clojure there are **collection** and **sequence** abstractions, **collections** are simply collections of elements like lists, vectors, and maps, **sequences** are walkable list abstractions for collections
  - the `seq` function returns a sequence on the collection it is given or `nil` if the collection is empty
  - the functions `first`, `last`, `conj` etc. are available on sequences
  - use the `seq` function instead of `(not (empty? coll))` to check if a collection is not empty (`nil` treated as `false`, non-empty list treated as `true`)
  - use `every?` to test if a predicate is `true` for every element in a collection i.e. `(every? odd? [1 3])` == `true`
  - when defining custom predicates be sure to end the function name in `?` as it is idiomatic to do so i.e. `(defn even? [x] (= (mod x 2) 0))`
  - use `not-any?` function to test if a predicate is `false` for all elements in a collection i.e. `(not-any? even? [1 3])` == `true`
  - the `some` function will return the first element's logical value for some given predicate or `nil` if all `false` i.e. `(some odd? [1 4])` == `true` (`1` is a truthy value)
  - useful to use a set as predicate to check if a sequence contains a certain element i.e. `(some #{4 5} [1 2 3 4 5])` == `4`, be careful as `(some #{false} [false])` == `nil`
  - the `if` function takes 3 parameters, the first is an expression that is the logical test, if `true` the second parameter is evaluated, if `false` the third is evaluated, use `if` when two possible things can happen depending on a logic test
  - use `if-let` when a logical value desires binding and logical testing (binding expression only for use conditionally) i.e. `(if-let [x (- 3 2)] x 0)` == `1`
  - the `when` function is used when an expression needs evaluation when a logic flag is `true` (no expression for a `false` flag, `nil` returned in the case of `false`) i.e. `(when (> 1 5) 10)` == `nil`
  - `when-let` works similarily to `if-let` except that it does not take an expression evaluated when the binding is false, `nil` returned instead i.e. `(when-let [x (> 2 1)] (str x " is true"))` == `"true is true"`
  - `cond` takes pairs of expressions and **order is important**, the first expression is logically evaluated, the second expression is returned if the first resulted in `true` (subsequent pairs are then **NOT** evaluated) i.e. `(let [x 5] (cond (= x 3) "three" (= x 5) "five" "default" "nil"))`
  - `"default"` is a truthy value and so serves as the 'default' expression in the above example, `nil` is returned if no defualt is provided and no pairs yield a match
  - use the `case` function when test expressions want to be compared on equality with **one** value i.e. `(let [x 5] (case x 3 "three" 5 "five" "nil"))` == `"five"` (`"nil"` is the default here as its a final single expression and not paired)
  - `case` **requires** a default value for the case when no match is made or an error will be thrown i.e. `(let [x 6] (case x 3 "three" 5 "five"))` => `IllegalArgumentException`

### Functions Creating Functions
  - use the `partial` function to create curried (partially applied) versions of functions i.e. `(partial + 3)` (an add 3 function)
  - use the `comp` function to compose functions, it takes any number of functions and executes them from right to left i.e. `(comp #(* % 3) (partial + 2))` (function that first adds 2 then multiplies by 3)

### Destructuring
  - **destructuring** is a technique that allows you to assign named bindings for elements in vectors and maps effectively breaking them up i.e. `(let [[r b] ["red" "blue"]] (str r " < " b))` == `"red < blue"`
  - destructuring handles nesting as well i.e. `(let [[r [b]] ["red" ["blue"]]] (str r " << " b))`
  - it is possible to maintain the original collection when destructuring using the `:as` keyword i.e. `(let [[r b :as coll] ["red" "blue"]] (str r " < " b " coll: " coll))` == `"red < blue coll: [\"red\" \"blue\"]"`
  - it is also possible to destructure maps extracting values by key i.e. `(let [{x :a y :b} {:a 1 :b 2}] (+ x y))` == `3`
  - `:as` works with maps and it is possible to provide default values for keys not present in a map being destructured i.e `(let [{x :a y :b :or {y 8} :as m} {:a 1}] (+ x y (m :a)))` == `10`
  - the `:keys` keyword can be used when destructuring maps to create local bindings by key name (very common) i.e. `(let [{:keys [a b]} {:a 1 :b 2}] (+ a b))` == `3`
  - it possible to destructure function parameters to when defining, leads to more clear and concise code i.e. `(defn adder [{:keys [a b]}] (+ a b))`

### Power of Laziness
  - Clojure supports **lazy sequences** i.e. `(take 5 (range))`, take care not to evaluate a lazy sequence to infinity, Clojure only evaluates what it needs
  - use `range`, `repeat`, `repeatedly`, `cycle` (repeats a collection's elements) to generate infinite lazy sequences i.e. `(take 10 (repeatedly #(rand-int 10)))`
  - `take` and `rest` help interarct with lazy infinite sequences i.e. `(take 5 (rest (cycle ["a" "b"])))`

### Recursion
  - recursive functions call themselves and in Clojure iterating collections is done recursively i.e.

  ```
    (defn reduce [f z xs]
      (if (empty? xs)
        z
        (reduce f (f z (first xs)) (rest xs))))
   ```
  - `loop` and `recur` are also available to provide a loop construct for use when coding (works much like recursion, body is executed until condition met)
  - `recur` is advantageous to use because it does not consume more stack space on subsequent "recursive" calls i.e. `(defn countdown [n] (if (= n 0) n (recur (- n 1))))` => avoids stack overflow, in general always use `recur` (only needs one stack, uses jump point)
  - functional programming is all about transforming incoming collections and returning new data structures

### The Functional Shape of Data Transformations
  - the shape of a `map` function call will be the same as the input collection (same number of elements, returned in a new collection)
  - the shape of a `reduce` function call can transform the original collection returning an entirely new data structure if desired
  - `map` takes a function and collection and returns the result of applying the function to each element in the input collection, returning a newly created collection i.e. `(map #(str %) [1 2 3])` == `'("1" "2" "3")`
  - `map` returns a lazy sequence so dealing with infinite sequences is possible i.e. `(take 3 (map #(str %) (range)))` == `'("0" "1" "2")`
  - a **pure function** is a function that always returns the same output given the same input and does no observable interacting with the outside world
  - a **side effect** is something that occurs in a function which makes a change to the observable world (mutating state, printing, logging, etc.)
  - use `doall` to force side effects when mapping over a collection i.e. `(def xs (doall (map #(println %) [1 2])))` (forces evaluation)
  - it is possible to pass multiple collections to `map` i.e. `(map #(+ %1 %2) [1 2] [3 4 5])` == `'(4 6)` (terminates with shortest collection)
  - `reduce` allows the shape of the input to transform i.e. `(reduce + [1 2 3])` == `6` (vector becomes an integer)
  - `reduce` takes a function of that takes parameters, an optional initial value and a collection, if no initial value is provided the first element in the collection is used as initial
  - it is not possible to run `reduce` on an infinite collection as the function runs until it consumes every element in the input collection
  - `filter` is another useful data transformation function that takes a predicate and a collection and returns a new collection containing the input elements that yield `true` when passed to the predicate i.e. `(filter (complement nil?) [1 nil 2 nil])` == `'(1 2)`
  - `remove` can also be used to filter elements with a predicate i.e. `(remove nil? '(nil 1 nil))` == `'(1)`
  - `for` is useful for enumerating a collection, retuning a lazy sequence while using a local binding in a body i.e. `(for [animal [:rabbit :mouse]] (name animal))` == `'("rabbit" "mouse")`
  - `for` can also be used with multiple collections for nested iteration as well as use the `:let` keyword to set new local bindings along with the locally bounded collection elements, use `:when` to use a flag for evaluation
  - `flatten` is useful for consuming nested collections and returning one single new collection i.e. `(flatten [1 [2 3] 4 [[5]]])` == `'(1 2 3 4 5)`
  - use `into` to create new data structures with a specified initial value i.e `(into (sorted-map) {:b 1 :c 2 :a 3})` == `{:a 3 :b 1 :c 2}`
  - `partition` function is useful for dividing collections into specified element count i.e. `(partition 4 [1 2 3 4 5 6 7 8 9])` == `'('(1 2 3 4) '(5 6 7 8))` (note `9` was dropped, use `partition-all` to keep all elements present in original collection)
  - use `partition-by` to split a collection based on a predicate i.e. `(partition-by #(= 2 %) [1 2 3 4])` == `'('(1) '(2) '(3 4))`

## 3. State and Concurrency

### Handling Real World State and Concurrency
  - Clojure and the functional style make dealing with concurrency much simpler due to purity
  - **atoms** store state that is *independent*, that is state that can be mutated independetly of any other state
  - create items like so: `(def who-atom (atom :caterpillar))`, to get at the value inside the atom *dereference* the atom's binding like so: `@who-atom` == `:caterpillar`
  - use `reset!` (idiomatic to append `!` to end of functions that mutate state) to change the value of the atom i.e. `(reset! who-atom :chrysalis)`
  - can also use `swap!` (think monadic bind) tht takes a transforming function and an atom and returns a new atom by applying the transformation function to the atom i.e. `(swap! (atom 6) #(+ % 1))` == `7`
  - the transformation function passed to `swap!` must be pure (free of side effects), Clojure retries the transformation function if another thread has mutated the original value it received in the atom (could mutate state multiple times if it has to retry)
  - the `future` form is available to utilize multiple threads and execute a new task in a background process i.e `(def a (atom 1)) (let [n 5] (future (dotimes [_ n] (swap! a inc))) (future (dotimes [_ n] (swap! a inc))))` => `@a` == `11`
  - use **refs** in Clojure when multiple things need to change in a coordinated fashion like transferring money between two bank accounts, **refs** allow coordinated shared state
  - refs are changed inside transactions made possible with **software transactional memory** (STM)
  - all actions on refs are atomic (all refs or none get updated), isolated (concurrent transactions do not effect one another), and consistent (validation of ref values) much like databases
  - create refs like so: `(def a (ref 1))`, use `alter` to change the state of a ref much like `swap!` but note `alter` must be used inside a transaction, keep `alter` side-effect free
  - use `dosync` function to execute a ref mutation inside a transaction i.e. `(dosync (alter a inc))`
  - can also use `commute` to mutate refs (much like `alter`) and limit retries in transactions
  - transactions that are very time consuming and involve many refs are more likely to be retried, consider using an atom containing a map of state over many refs
  - use `ref-set` to explicitly reset a refs value i.e. `(def x (ref 1)) (dosync (ref-set x (+ 3 4)))`
  - atoms are for independent synchronous changes, refs are for coordinated synchronous changes, **agents** are used for independent and asynchronous changes (work to be done but results not needed right away)
  - create agent like so: `(def who-agent (agent :caterpillar))`, _dereference_ an agent like so: `@who-atom` => `:caterpillar`
  - use the `send` function to transform an agent, `send` consumes an agent and a transformation function i.e. `(send (agent 1) #(+ % 1))`, the agent's value is updated **asynchronously**
  - `send` dispatches action to agent in a thread from the thread pool, only one action is processed at a time
  - use the `send-off` (similar to `send`, uses expandable thread pool instead of fixed) function when actions that may potentially be I/O blocking wish to be sent to an agent
  - errors can be processed by and sent to agents, use `reset-agent` to reset the state and clear errors for an agent
  - can also set agent error mode to `:continue` with `set-error-mode!`, agents can be supplied an error handler with `set-error-handler!` and won't need to be restarted when an exception is thrown inside an action
  - remember **atoms** are for _synchronous_, _uncoordinated_ work, **refs** are for _synchronous_, _coordinated_ work, and **agents** are for _asynchronous_, _uncoordinated_ work

## 4. Java Interop and Polymorphism
  - Clojure runs on the JVM and therefore has access to a rich set of Java based libraries, as well as its own

### Handling Interop with Java
  - Clojure uses the *new* and *dot* special forms to interact with Java classes in an idiomatic fashion
  - strings in Clojure are just instances of a Java class i.e. `(class "caterpillar")` == `java.lang.String`
  - use **dot** methods as follows: `(.indexOf "caterpillar" "pill")` == `5`
  - create new instances of Java classes with either `(new String "hello")` or `(String. "world")`
  - to import Java classes use the `:import` keyword with the `ns` macro i.e. `(ns caterpillar.network :import (java.net InetAddress))`
  - to execute static class methods use the forward slash `/` i.e. `(InetAddress/getByName "localhost")`
  - use the `doto` macro to perform a succession of methods on a particular Java instance i.e. `(doto (StringBuffer. "Hello") (.append ", ") (.append "world!") (.toString))`
  - other useful Java libraries and classes include support for UUIDs i.e. `(java.util.UUID/randomUUID)`

### Practical Polymorphism
  - use the `defmulti` and `defmethod` macros to dispatch a function on multiple types, `defmulti` is used to determine how the function dispatches
  - example:

  ```
    (defmulti who-are-you class) ;; dispatch on the single input's class

    (defmethod who-are-you java.lang.String [input]
      (str "String - " input))

    (defmethod who-are-you clojure.lang.Keyword [input]
      (str "Keyword - " input))

    (defmethod who-are-you java.lang.Long [input]
      (str "Number - " input))
  ```
  - any input that does not match a corresponding dispatch function will throw an exception, like calling `(who-are-you true)` => `IllegalArgumentException`
  - it is also possible to define custom functions that determine how multi-methods are dispatched
  - protocols can be employed to handle polymorphism with groups of functions i.e.

  ```
    (defprotocol BigMushroom
      (eat-mushroom [this]))

    (extend-protocol BigMushroom
      java.lang.String
      (eat-mushroom [this]
        (str (.toUpperCase this) " mmmm tasty!"))

      clojure.lang.Keyword
      (eat-mushroom [this]
        (case this
          :grow "Eat the right side"
          :shrink "Eat the left side"))

      java.lang.Long
      (eat-mushroom [this]
        (if (< this 3)
          "Eat the right side to grow"
          "Eat the left side to shrink")))
  ```
  - use the `defrecord` macro to define new data types, all fields in the new class are passed to the macro i.e. `(defrecord Mushroom [color height])`
  - it is possible to extend protocols inside a `defrecord` body, new types you create can easily build interfaces this way
  - protocol practical example: persisting data records to various types of data persistance layers i.e. SQL/NoSQL DB, S3, FTP
  - when a new type's fields are not needed, or they don't have them (**unstructred data**) use the `deftype` macro which functions similar to `defrecord` but doesn't define class fields
  - protocols should be used sparingly, in most cases a regular function (consumes map inputs) or a multi-method can be sufficient, it is easy t move from maps to records later if needed

## 5. How to Use Clojure Libraries and Projects
  - until now code snippets have been small and executed in a stand-alone REPL, this chapter will explore building Clojure source files with the lein build tool

### Creating Projects with Leiningen
  - leiningen is the most popular and common tool to use when building and managing a project and its dependencies
  - create a new project via `lein new serpent-talk`
  - files/folders in new `serpent-talk` directory include `LICENSE`, `README.md`, `doc/intro.md`, `project.clj`, `resources`, `src/serpent_talk/core.clj`, `test/serpent_talk/core_test.clj`
  - it is good practice to rename the core files leiningen automatically generates (changing to `talk.clj` and `talk_test.clj`)
  - leiningen auto generates a function called `foo` as a placeholder in the single source file it creates
  - filenames **always** use underscores (for Java compilation compatability) and namespaces **always** use hyphens
  - test files auto generated for us refer all (`:refer :all`) of the `clojure.test` namespace and the one auto generated source file's (`talk.clj` in this case) namespace
  - `deftest` defines a test function, `testing` is used in conjunction with `deftest` to provide context for tests, `is` provides the assertion being tested
  - run tests with the terminal command `lein test`
  - the `target` directory that is generated when running tests contains information about compiled classes
  - project configuration resides in the top level `project.clj` file, a set of default config is auto generated

### Dependency Management with Leiningen
  - project dependencies are specified in the `:dependecies` metadata inside `project.clj`, by default `[org.clojure/clojure "1.8.0"]` is included (version may vary)
  - `org.clojure` is a group id, `clojure` is an artifact id, `1.8.0` is the version
  - leiningen will check popular open source repos for package dependencies such as clojars and maven central
  - you can use the terminal command `lein deps :tree`to view a list of all project dependencies
  - _snake case_ uses underscores `this_is_snake_case`, _kebab case_ uses hyphens `this-is-kebab-case`, _camel case_ uses capitals `ThisIsCamelCase`
  - visit **https://clojars.org/** to view and search through the packages offered on clojars, **http://search.maven.org/** to view and search maven repo
  - to run Clojure projects from the command line we need to define a `-main` function as the entry point
  - to run projects use the terminal command `lein run -m <desired-namespace>` (remember the namespace needs to define an entrypoint called `-main`)
  - `lein run` without specifiying a namespace is possible when the `:main` meta data in `project.clj` is provided

## 6. Communication with `core.async`
  - 

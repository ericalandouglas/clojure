# The Joy of Clojure

# Part 1: Foundations

## Chapter 1: Clojure Philosphy
- Clojure utilizes software transactional memory, agents, distinction between identity and value types, arbitrary polymorphism, and functional programming
- Clojure runs on the JVM leveraging much of the existing infrastructure

### The Clojure Way
- Clojure aims for **simplicity**, **freedom to focus**, and **empowerment** which in turn aim for *pure functions, conciseness, expressiveness, direct interop, practicality, seperation of concerns,* __clarity, consistency__

#### Simplicity
- Clojure offers powerful tools while not adding incidental complexity (more complex then necessary, non-essential)
- Clojure sticks to the bare essentials to strip away complexity, pure functions versus classes

#### Freedom to focus
- Clojure aims to be both simple (no type decalarations, inheritance hierarchies, etc) and expressive (lends itself to DSLs) to keep productive and focused
- Clojure embraces dynamic systems (redefinitions can happen at run time)

#### Empowerment
- Clojure aims to allow the programmer to build interesting and useful applications, to be practical
- direct access to Java classes and methods is available (no impractical wrappers)
- JVM offers maturity, speed, wide adoption, lots of libraries and tools, and Clojure interops seemlessly with it

#### Clarity
- Clojure provides immutable locals and persistent collections to eliminate many single and multi-threaded issues
- Clojure strives for seperation of concerns (values *from* identifiers, function namespaces *from* type namespaces, hierarchy of names *from* data and functions, data objects *from* functions, interface declarations *from* function implementations)

#### Consistency
- Clojure aims for consistent syntax and data structures
- consistency of syntax is about the similarity in form between related concepts (`for` and `doseq`)
- Clojure data structures are used to hold elements of applications, code is data
- the rich set of functions clojure offers its persistent collections is still available everywhere this way

### Why a(nother) Lisp?
#### Beauty
- Lisp has very few primitive forms (7) and is very expressive and empowering
- by requiring only seven functions and two special forms simplicity is gained

#### Why all the parentheses?
- Clojure has a simpler syntax (just lots of parentheses) which makes writing macros and DSLs easier among other benefits
- function calls look like the following: `(foo 1)` `(.method thing 2)` `(+ 1 (* 2 3))`
- even mathematical operations are called in the same fashion and do not use infix form (can do anything other Clojure functions can)
- Lisp syntax doesn't concern itself with operator precedence, functions are simply evaluated from innermost to out, left to right
- Lisp syntax facilitates viewing functions as operating on collections by default
- besides the standard REPL, Clojure also expands with macros and compiles when working in the REPL
- the form `(+ 1 2)` can be interpreted in Clojure as either a function call or a list (distinction)
- Clojure syntax reads just like a set of list data structures that can themselves be operated on, "code is data"
- Lisps can manipulate their own structure and behavior giving them them the quality of *homoiconicity*

### Functional Programming
#### A workable definition of functional programming
- functional programming's core currency is a procedure/function
- functional programming facilitates the application and composition of functions (which are first class citizens)

#### The implications of functional programming
- the functional mindset attacks problems through the use/composition of verbs (functions) instead of nouns (classes)

### Why Clojure isn't especially object-oriented
- Clojure arose in part from the frustration experienced when tackling problems of concurrency in an object-oriented domain

#### Defining terms
- *time* - the relative moments when events occur
- *identity* - the properties associated with an entity, both static and changing, singular or composite
- *state* - a snapshot of an entity's properties
- classic OOP conflates state and identity known as *mutable state*, no willingness to preserve historical snapshots
- Clojure focuses on immutability, expounds that most of what is meant by *objects* are *values*
- *value* - an object's constant representitive amount, magnitude, or epoch
- Clojure isolates value change to its reference types which provide a level of indirection to an identity that can be used to obtain consistent (not always current) state

#### Imperative "baked in"
- *Imperative programming* dominates the programming language landscape currently, with OO being the preferred flavor
- OOP does not lend itself to concurrency because there are no gaurantees an object's value(s) contain expected values

#### Most of what OOP gives, Clojure provides
- polymorphism is available in Clojure via protocols
- *polymorphism* - the ability of a function or method to perform different actions depending on the type of its arguments or a target object
- *protocols* - allow for the attachment of a set of behaviors to any number of existing types and classes (including natives), similar to *mix-in*, *interface* in Java
- *the expression problem* - the desire to implement a set of abstract methods for a concrete class without changing code that defines either
- OOP provides inheritance but only when you control the concrete class definition
- *monkey patching* - adding new methods to an existing concrete class after its definition (can be done in Scala, JavaScript, C#)
- protocols define a set of logically grouped functions to which data-type abstractions must adhere, this *abstraction-oriented programming* is key to successfully building large scale apps
- Clojure provides namespaces and the `defn-` macro to support *encapsulation*
- can also locally bind functions or other values with `let` to narrow entities' scoping more
- OOP tends to promote the use of classes creating a "kingdom of nouns" that is often too restrictive for proper polymorphism
- math is about mapping a domain to a range via function application which is how Clojure implementations work

## Chapter 2: Drinking from the Clojure firehose
- will cover scalars, collections, functions, vars, locals, loops, blocks, quoting, host library interop, exceptional circumstances, namespace modules
- a REPL can be used to run Clojure code more interactively and starts up in the default `user` namespace

### Scalars: the base data types
- the scalar data types include floats, ints, strings, characters, bools, rationals, symbols, keywords, regex patterns

#### Numbers
- a number can consist of only the digits `0-9`, and optionally a decimal point `.`, a sign `+` or `-`, and an `e` for exponential notation
- numbers can also include a trailing `M` to denote arbitrary precision or trailing `N` to denote arbitrary size as well as take hexidecimal or octal form

#### Integers
- Clojure integers represent the entire number line and can be very large (granted memory availability), `4`, `-9909`, `+10`
- most integers are primitive Java longs, larger integers are instances of the BigInt class (denoted with trailing `N`)
- integers can be decimal, octal, hexidecimal, etc. `[127 0x7F 0177 32r3V 2r01111111]` (all numbers are 127 in decimal)

#### Floating-point numbers
- decimal expansion can take arbitrary precision in Clojure
- valid floating point numbers: `1.17`, `+4.3256`, `-2.`, `10.7e-3`, `32e4`

#### Rationals
- rationals can serve as a compact and concise value over floating points i.e. `1/3`
- like the classic form, rationals are represnted with a numerator and denominator in Clojure i.e. `22/7`, `-10/3`
- rationals simplify when possible e.g `100/4` resolves to the integer `25`

#### Symbols
- symbols are objecs in their own right but are used to represent other values i.e. `(def x 42)`
- typically used to refer to function parameters, locals, globals, and Java classes

#### Keywords
- keywords **always** evaluate to themselves, the `:` is part of the literal syntax and not part of the keyword's actual name
- valid keywords: `:2`, `:bumble`, `:?`

#### Strings
- any sequence of characters enclosed in a set of double quotes, including new lines i.e. `"hello\nworld"`

#### Characters
- characters are written using the literal syntax prefixed with `\` i.e. `\A`, `\u0042` (unicode), `\\` (backslash)

### Putting things together: collections
#### Lists
- literal lists are written with parantheses i.e. `(foo x y)`, `foo` resolves to function, macro, or special operator
- *form* - any Clojure object meant to be evaluated, including but not limited to lists, vectors, maps, numbers, keywords, and symbols
- *special form* - a form with special syntax or special evaluation rules that typically aren’t implemented using the base Clojure forms i.e. the `.` (dot) operator for Java method interop
- in Clojure the empty list is `'()` (not a falsy value) instead of `nil` (a falsy value)

#### Vectors
- store a series of values like lists, but have literal syntax that uses square brackets i.e. `[1 2]`
- vectors evaluate each item in order, no function of macro is performed on the vector itself
- like lists they are type heterogenous, empty vector is `[]` (truthy value)

#### Maps
- store unique keys and one value per key (same idea as *dictionary*, *hashes*)
- literal syntax uses curly bracers i.e. `{:a 1 :b 2}` (commas optional, treated like white space in Clojure)
- like vectors, each key's value is evaluated before being stored in the map but no gaurantees on processing order
- maps can have any type for **both** keys and values, the empty map `{}` is a truthy value

#### Sets
- store zero or more unique items, literal syntax: `#{1 \a :apple}`, `#{}` != `nil`

### Making things happen: calling functions
- functions are first class and so can be used and treated just like any other value in Clojure
- function calling syntax uses prefix notation (as is the norm in lisps)
- prefix notation allows any number of operands per operator where as infix only allows for two operands at a time
- all constructs, operators, macros, and functions are formed using prefix, fully paranthesized notation (providing flexibility)

### Vars are not variables
- a *var* is named by symbol and holds a single value, value can be changed at runtime
- `def` is the most common way to create vars in Clojure i.e. `(def x 3)` creates a *root binding* (all threads start with the root binding which is their associated value in the abscense of a thread-bound value
- can also defer defining a value for vars to individual threads and simply make a declaration i.e. `(def y)`
- theoretically you can implement any algorithm with just funcitons and vars and some languages leave you with only these two *"constructs"*

### Functions
#### Anonymous functions
- unnamed functions can be created with a *special form* (a Clojure expression that’s part of the core language but not created in terms of functions, types, or macros)
- anonymous functions are helpful in certain situations but it is often preferred to name most functions for clarity
- examples: `(#(+ 7 %) 3)` == `10`, `((fn [x] (println x)) 1)`

#### Creating named functions with `def` and `defn`
- can use either `def` or `defn` special forms to create functions
- `defn` is more convienent/concise i.e. `(defn foo [x] (println x))` vs `(def foo (fn [x] (println x)))`, a documentation string can optionally be passed to `defn`

#### Functions with multiple arities
- *arity* - difference in argument count that a function accepts
- `defn` can take any number of argument/body pairs granted the argument count in each pair is unique (unique arity per body)
- to create one body that takes any arity use the `&` symbol followed by symbols or *destructuring* forms (argument symbols before the `&` are still bound one to one)
- additional args after `&` are aggregated into a sequence and bound to the symbol following `&`

#### In-place functions with `#()`
- `#()` is used as shorthand notation for anonymous functions and is *reader feature*
- *reader feature* - analogous to C++ preprocessor directives, signify that some form should be replaced with another form at read time
- `#()` is essentially a `fn` form and the two can be used interchangably
- use the `%` symbol to refer to arguments inside the `#()` form e.g. `(defn make-list #(list %1 %&))` (can use `%` in place of `%1`)

### Locals, loops, and blocks
#### Blocks
- use the `do` form to create a block and execute a sequence of statements
- all expressions are evaluated but only the last is returned (middle expressions are often used for side-effects)

#### Locals
- no concept of local variables in Clojure, only immutable locals
- the scope and values of locals are defined with the `let` form (starts witha. vector containing any number of local bindings)
- `let` (sometimes referred to as *implicit do*) like `do` can contain any number of expressions in vector and body, all will be evaluated but only the last is returned
- destructuring is also available in when defining the bindings for locals in the vector of a `let` form

#### Loops
- functional languages prefer to perform iterations using tail recursion, Clojure is no exception
- Clojure's special form `recur` is used for performing tail recursion (**MUST** be in tail position), if a recursive function takes multiple arguments, so must the `recur` form
- the `loop` form can be used to set up a new target point for recursion inside a function (instead of recursing back to the top of the function), can take multiple bindings in its vector
- *tail position* - a form whose value may be the return value for the entire enclosing expression e.g. `(fn [x] (if (pos? x) x (- x))` => the `if` form is in tail-position because its value is the return value for the entire function

### Preventing thing from happening: quoting
- Clojure has two quoting forms: quote and syntax-quote, primary way to include literal scalars and composites in code *without* evaluating them

#### Evaluation
- when a collection is evaluated in Clojure each item in the collection is evaluated first e.g. `(cons 1 [2 3])` => `1` and `[2 3]` are evaluated and then whole `cons` expession can be evaluated
- symbols, when evaluated, resolve to a local, var, or Java class name and literal scalars evaluate to themselves, like vectors and maps
- lists, `()` inherently call functions or trigger special operations

#### Quoting
- the `quote` special operator (called the same way a function is via list) prevents its args from being evaluated e.g. `(quote (identity 1))` == `(identity 1)`, **NOT** `1`
- the argument passed to the `quote` operator can be arbitrarily complex/nested
- the most common use case of the `quote` form is so to use a literal list as a data collection without having Clojure call a function e.g. `(2 3)` is treated as a function call and will throw an exception
- the short-hand syntax for creating a list literal uses a single quote instead of the `quote` operator, be weary that nothing is evaluated inside the list e.g. `'(1 2 3)` is a list literal, `'(1 (+ 2 3))` != `'(1 5)` (addition IS NOT evaluated like it would be in a vector)
- quoting the empty list is not required in Clojure `'()` == `()`
- the syntax quote in Clojure is written as a single back-quote, ` prevents its arguments and sub-forms from being evaluated (ideal for constructing collections to be used as code)
- symbols can begin with namespace and slash (*qualified*) and the syntax-quote automatically qualifies an unqualified symbol e.g. `+ == clojure.core/+ (uses current namespace if symbol or var does not yet exist

#### Unquote
- use an *unquote* when you want some of a quoted expression's sub/constituent forms to be evaluated, denoted with `~` (unquote operator) e.g. ```
`(+ 10 ~(* 2 3)) ;=> (clojure.core/+ 10 6)
```

- the following form is illegal as Clojure treats `2` as a function when it is not:
```
`(1 ~(2 3)) ;=> ILLEGAL
```
- the following is a valid Clojure form:
```
`(1 ~'(2 3)) ;=> (1 (2 3))
```

#### Unquote-splicing
- unquote splicing is used to unpack sequences and splice it into a resulting set,operator is `@` e.g.
```
`(1 ~@'(2 3)) ;=> (1 2 3)
```

#### Auto-gensym
- to get a unique symbol (for function, `let` binding or similar) use a `#` at the end of a quoted symbol e.g.
```
`potion# ;=> potion__1260__auto__
```

### Using host libraries via interop
- the interaction between Clojure and Java is known as *interop*, Clojure is symbiotic toits host and offers its many useful features

#### Accessing static class members (Clojure only)
- static class members in Clojure are accessed similarily to a syntax that is like accessing a namespace-qualified var e.g. `Math/sqrt`
- by default all classes in the root `java.lang` package are available for immediate use (limited to Clojure as ClojureScript runs as JavaScript)

#### Creating instances
- to create an instance of a Java class include a `.` at the end of the class name to invoke its constructor (being sure to pass the correct constructor args) e.g. `(java.awt.Point. 1 0)`
- in ClojureScript when referencing global or or core JavaScript types, include a `js` prefix e.g. `js/Date.` is date JS constructor

#### Accessing instance members with the `.` operator
- to access public instance variables, proceed the variable with `.-` e.g. `(.-x (java.awt.Point. 1 0)) ;=> 1`
- to access instance methods the dot form is followed by an instance arg and an additional arg when applicable e.g. `(.divide (java.math.BigDecimal. "42") 2M) ;=> 21M` (`2M` denotes an arbitrarily precise numeric value)

#### Setting instance fields
- use the `set!` function to modify an instance field e.g. `(set! (.-x (java.awt.Point. 1 0)) 15)` (first arg is the instance member access form including `.-`)

#### The `..` macro
- use the `..` operator to perform a chain of operations to an instance of a Java class e.g. `(.. (java.util.Date.) toString (endsWith "2017"))` (note the omittance of the `.` on each chained method arg)
- in Clojure it is preferred to use the `->` and `->>` operators over the `..` operator as the arrow/thread operators work when performing interop or working in native Clojure

#### The `doto` macro
- use the `doto` operator to streamline Java instance mutation operations

#### Defining classes
- Clojure provides the `reify` and `deftype` macros to facilitate the realization of Java interfaces as well as `proxy` (used for more on the fly changes) and `gen-class` (generate statically named classes) macros

### Exceptional circumstances
- whether performing interop or working with strict Clojure the preferred way to handle exceptions is by using the the `throw` and `catch` forms (much like Java and JavaScript)

#### Throwing and catching
- throw execpetions like so: `(throw (Exception. "An exception."))`
- can include multiple `catch` forms to handle a variety of different types of exceptions when writing a `try` form (`catch` forms live inside the `try` form)
- Clojure doesn't adhere to checked-exception requirements like Java, preceed core JavaScript errors with `js` when attempting to catch them in ClojureScript `catch` forms

### Modularizing code with namespaces
#### Creating namespaces using `ns`
- the `ns` macro is used to declare new namespaces e.g. `(ns my.new.ns)`, using this in the REPL changes the REPL namespace to be the newly created one
- the `*ns*`var provided by Clojure is available and will refer to the current working namespace, any var created belongs to the namespace currently bound to `*ns*`

#### Loading other namespaces with `:require`
- use the `:require` keyword with the `ns` macro to tell Clojure to load pre-existing namespaces into the current working module/REPL e.g. `(ns my.ns (:require [clojure.set :as set]))`
- namespaces such as `clojure.set` can not be referenced independently like Java classes (`java.lang.String`), Clojure namespaces can only be used as qualifiers e.g. `clojure.set/difference`
- the convention for Clojure namespaces is `my.ns` and for Java classes `my.Class`, like `clojure.set` (namespace) and `java.lang.String` (class) above

#### Loading and creating mappings with `:refer`
- use the `:refer` directive inside a `:require` directive with the `ns` macro to avoid having to fully qualify vars and symbols in another namespace
- to make set `difference` available in `my.ns` without full qualification (other vars in `clojure.set` still need full qualification) do `(ns my.ns (:require [clojure.set :refer [difference]]))`
- `:refer :all` can be used to refer all vars/symbols in a namespace but it is generally recomended to be explicit about what you want to refer/include to help mitigate name clashing, unclear code, etc.

#### Creating mappings with `:refer`
- to refer an already loaded namespace's symbols and vars the `:refer` directive is available to the `ns` form e.g. `(ns my.ns (:refer clojure.set))`
- a `:rename` directive is available to this `:refer` (as well as `:require`) directive to help rename referred vars/symbols e.g. `(ns my.ns (:refer clojure.set :rename {union onion}))`

#### Loading Java classes with `:import`
- to refer to a Java class in unqualified form use the `:import` directive with the `ns` macro e.g. `(ns my.ns (:import [java.util HashMap]))`
- fully qualified Java classes are always available in Clojure without the need of importing, classes in the `java.lang` package are automatically imported and so are safe for unqualified use

## Chapter 3: Dipping your toes in the pool
- chapter will cover truthiness, nil punning, destructuring, using REPL for expirementation
- *truthiness* refers to the idea of the distinction between values into those that are considered logically `true` and those that are logically `false`
- *nil punning* refers to the idea of referring to an empty sequence as `nil`
- *destructuring* refers to the idea of pulling apart collection types and binding their constituent parts as new individual values

### Truthiness
- Clojure has one boolean context: the `if` form, `and`, `or`, `when`, and other macros that expect booleans are simply built on top of `if`

#### What's truth?
- every value is considered `true` to `if` except for `nil` and `false`, that means the empty string `""`, the empty list `[]`, etc. are truthy

#### Don't create `Boolean` objects
- never use the Java `Boolean` class and `Boolean.` constructor to make a new instance of a bool
- `true` (`Boolean/TRUE`) and `false` (`Boolean/FALSE`) already cover the only two possible instances of the Boolean class
- use the Boolean class static method `valueOf` to parse strings into bools e.g. `(Boolean/valueOf "false")` == `false`

#### `nil` versus `false`
- use `nil?` and `false?` operators if it is required to differentiate between `nil` and `false`

### Nil pun with care
- use the `seq` collection to nil pun a list (essentially `nil` check) e.g. `(seq [])` == `nil`, `(seq [1 2])` == `(1 2)`
- `(when (seq s) ..)` is the preferred way to check if collection is empty
- the `rest` operator never returns `nil`, only the empty list `()` or a list with elements, using `seq` with rest will result in a `nil` value when fed the empty list `()`
- in Clojure it is generally assumed `seq` hasn't been called on function args, function implementations should use `seq` for collection processing where appropriate (recursion, more generic handling, etc.)
- generally use `do`, `doseq`, etc. to perform side-effects and often return `nil`

### Destructuring
- *destructuring* - allows you to positionally bind locals based on an expected form for a composite data structure, use a collection of names in a binding form where there would normally be a single name (functions' arg lists are examples of binding forms)
- similar to pattern matching in Haskell but doesn't allow for conditional binding (full featured pattern matching in Clojure can be accomplished with the help of `core.match`)

#### Destructuring with a vector
- example: `(let [[a b c] [1 2 3]] (+ a b c))`, `a`, `b`, and `c` are part of a vector binding form and can be used as vars in the `let` body
- **positional destructuring** is only available with vectors and the `java.util.regex.Matcher` class (for destructuring capture groups, consider `re-find` as well), and things that satisfy the `CharSequence` protocol, sets and maps are not guaranteed to have a specific order
- an `&` can be used when destructuring a vector to pack any remaining elements into a (possibly lazy) `seq` e.g. `(let [[a & rest] [1 2 3]] rest)`
- the `:as` feature can be used with vector destructuring to locally bind the entire original collection (must come after `&` one exists in the binding form and at the end of the destructuring vector) e.g. `(let [[a & rest :as all] [1 2 3]] all)`

#### Destructuring with a map
- example: `(let [{:keys [a b c]} {:a 1 :b 2 :c 3}] (+ a b c))`, `a`, `b`, and `c` local bindings exactly map to the respective keys present in the map used in the binding form (`:keys` directive is preferred for its simplicity)
- use the `:strs` directive if a map contains strings for keys, `:syms` for symbols (`:keys`, `:strs`, `:syms` can be used in any order and combo), `:as` directive can be used to locally bind the original destructured map
- if a destructured key has no value it will normally be assigned `nil`, the `:or` directive can be used to provide default values for missing keys e.g. `(let [{:keys [a] :or {a 2}} {:b 1}] a)`
- map destructuring techniques also work with vectors, usually in the context of passing keyword arguments to functions (can associatively destructure collections by index as well)

#### Destructuring in function parameters
- function parameters that are collections can be destructured in the parameter list e.g. `(defn foo [[a b]] (+ a b))`, `(defn foo2 [{:keys [a b]}] (+ a b))`
- `&` is used in function argument list to support multiple arities and function bodies, not necessarily to catch the "rest" of the params

#### Destructuring vs. accessor methods
- Clojure prefers to use maps and vectors coupled with destructuring to create data abstractions instead of creating new classes to manage data and methods

### Using the REPL to experiment
- use the REPL to experiment with new libraries/features as well as test/verify function implementations
- use the `for` macro to iterate over a collection, performing actions and collecting the results, supports `:let` and `:when` forms
- whenever Clojure encounters what looks like a Java class name it will attempt to resolve it as a class, this allows for behavior such as calling `.getMethods` directly on a class
- the function `javadoc` in the `clojure.java.javadoc` namespace can be used to retrieve API docs online about Java classes and objects
- when an exception is thrown in the REPL it is stored in the var `*e` and can introspected and used for debugging e.g. `(.printStackTrace *e)`, or `(pst)`
- it is perfectly acceptable to redefine functions and implementations in the REPL
- it is important to try small pieces of code at a time in the REPL so going down the wrong path is not as time consuming

# Part 2: Data types

## Chapter 4: On scalars
- chapter covers understanding precision, trying to be rational, when to use keywords, symbolic resolution, regular expressions - the second problem
- a *scalar data type* represents a singlular value of one of the following types: number, symbol, keyword, string, character
- because of Clojure's symbiotic relationship to its host (namely the JVM) certain situations require care when using the scalar types due to potential nuances invoked by host semantics

### Understanding precision
- numbers in Clojure are as precise as they need to be given enough memory, for instance Pi can be calculated to a billion places of precision (rarely need large accurate precision in practice)

#### Truncation
- *truncation* - refers to limiting accuracy for a floating-point number based on a deficiency in the corresponding representation
- Clojure truncates floating-point numbers by default so if high precision is required then explicit type casting must be used by including a trailing `M`

#### Promotion
- Clojure is able to detect when overflow occurs and promote the value to the proper numerical rerpresentation required for storage
- Clojure will automatically convert numbers to the Java classes `BigInt`, `Double`, `Long` accordingly when performing arithmetic (for example when adding), etc.

#### Overflow
- normally Clojure automatically promotes arithematic results when 32 bit Integer overflow occurs but not in cases when dealing with numeric operations on primitive types e.g. `(+ Long/MAX_VALUE Long/MAX_VALUE) ;=> java.lang.ArithmeticException: integer overflow`
- normally it is smart to check for and handle overflow if it is possible, in cases where you want to enable use unchecked operations e.g. `unchecked-add`

#### Underflow
- *underflow* - the inverse of overflow where a number is so small it collapses to zero, occurs only with floating-point numbers

#### Rounding errors
- rounding errors are dangerous because they can propogate and build up over time
- Clojure was built to handle exact decimal representations instead of using approximations but care should still be shown around rounding errors when interacting with a Java library
- in Clojure any calculation that involves even a single double will result in a double

### Trying to be rational
- Clojure has a core data type for *rational numbers*, fractions consisting of an arbitrary-precision integer numerator and denominator e.g. `22/7`
- Clojure supports arbitrary precision in both numerator and denominator so that any accuracy can be retained

#### Why be rational?
- arbitrary decimal operations can be easily corrupted especially when interacting with Java libraries
- Java use the `BigDecimal` class for arbitrarily large decimals but this is still subject to potential errors as it can only store 32 bits to the right of a decimal
- at some point the time may come when a decimal will always be rounded i.e. `2/3`, it is usually best to use rationals for arbitrary decimal precision accuracy

#### How to be rational
- use the function `rational?` to check whether a given number is a rational type, use `rationalize` to convert a number to a rational type
- avoid using Java library math functions that don't return `BigDecimal` and avoid rationalizing Java floats or doubles
- use `numerator` and `denominator` functions to get the constituent parts of a rational number

#### Caveats of rationality
- rational numbers trade speed for accuracy, calculations on rational numbers won't be as performant as floats or doubles
- may be wise to avoid rational calculations is speed is the highest priority in your program

### When to use keywords
- *keywords* - self-evaluating types that are prefixed by one or more colons e.g. `:a-keyword`, `::also-a-keyword` (two colons denotes a *qualified keyword*)

#### Applications of keywords
- keywords *always* refer to themselves whereas symbols don't e.g. the keyword `:magma` has the value `:magma` (a symbol `magma` refers to any legal value or reference in Clojure, not itself)
- keywords are almost always used as map keys in Clojure code and can be supplied as the second argument to a `get` call
- when a keyword is in the function position of a form it is treated as a function, can take map as argument to look up value in map corresponding to the keyword
- keywords can also be used to enumerate a list of values to provide visual delineation in code (ideal for dispatch values in multi-methods)
- keywords are also used as directives in functions, forms, and macros (`:keys`, `:as`, etc.) and in `cond` to signify the catch all case (`:else`)

#### Qualifying your keywords
- when Clojure sees a double colon before a keyword it assumes the programmer wants the keyword qualified or prefixed e.g. `::apple` is qualified to the active namespace (`:user/apple`)
- the prefix used in qualified namespaces is generated by the Clojure reader and does not actually represent a namespaced var/symbol, a qualified keyword is still a keyword
- qualified keywords are just keywords that have an arbitrary prefix used for clarity around namespace specification and qualification (perhaps if a function is performing varying namespace functionality)

### Symbolic resolution
- symbols in Clojure are roughly analogous to identifiers in many other languages, words that refer to other things, provide a symbol in exchange for a value
- symbols can be directly referred to using the `symobol`, `quote`, functions or the `'` special operator
- symbols are not unique based solely on name, symbols that share names do not share the same instance object e.g. `(identical? 'goat 'goat) ;=> false`, `(= 'goat 'goat) ;=> true`
- Clojure does not make two identically named symbols the same object to support varying metadata between the two instances

#### Metadata
- the `with-meta` function which consumes an object and a map and returns a new object, used to attach meta data to an object (to perhaps help differentiate between two identically named symbol instances/objects)
- use the `meta` function to obtain an object's meta data, keywords can not hold meta data because equally named keywords are the same object

#### Symbols and namespaces
- like keywords, symbols **do not** belong to any namespaces, can be namespaced qualified with `resolve` or ` (qutoing a symbol)
- a symbol's qualification is a characteristic of evaluation, not necessarily inherent in the symbol itself

#### Lisp-1
- Clojure is known as a Lisp-1, it uses the same name resolution for function and value bindings
- in Lisp-2 name resolution is performed differently depending on the context of the symbol, be it in function-call position or function-argument position
- because Clojure resolves function and value names the same it is possible to shadow existing functions with other locals and vars
- take care when naming symbols and Lisp-1 can lend itself to cleaner implementations (avoiding nuanced symbol lookups)

### Regular expressions - the second problem
- use regex as a powerful and compact tool to find specific patterns in string text
- Java's regex engine is reasonably powerful, supports Unicode, reluctant quantifiers, and look-around clauses

#### Syntax
- Clojure supports literal regex expressions like `#"example regex"`, which produces a compiled regex object that can be used with Java interop or direct Clojure regexp specific functions
- literal regex expressions are compiled at read time to `java.util.regex.Pattern` instances, it is not necessary to escape special characters in regex literals
- regex literals support various flags that must be declared at the start e.g. `#"(?i)yo"` sets case insensitivity for the pattern

#### Regular-expression functions
- the `re-seq` function is Clojure's regex workhorse, returns lazy seq of all matches in string, used for testing string matches and finding all matches e.g. `(re-seq #"\w+" "one-two/three") ;=> ("one" "two" "three")`
- *capturing group* - subsegments accessible via the returned match object, when used in literal regex expression, returned seq from `re-seq` contains vector elements **NOT** string elements

#### Beware of mutable matchers
- Java's regex engine includes a `Matcher` object that mutates in a non-thread-safe way, use matchers at your own risk, best to avoid whenever possible

## Chapter 5: Collection types
- chapter covers persistence, sequences, and complexity, vectors: creating and using them in all their varieties, lists: Clojure's code form data structure, how to use persistent queues, persistent sets, thinking in maps, finding the position of items in a sequence
- Clojure provides a rich set of composite/collection types: vectors, lists, queues, sets, and maps, vectors and maps are used in the widest variety

### Persistence, sequences, and complexity
#### "You keep using that word. I do not think it means what you think it means."
- main stream use of the word *persistent* may often refer to persisting data in some data storage layer, in Clojure it refers to immutable in-memory collections with specific properties
- Clojure's persistent collections allow for the preservation of historical versions of its state and promises that all versions will have the same update and lookup complexity guarantees
- Clojure's implementations of persistence is efficient, they share structural elements from one version of a persistent structure to another
- each instance of a collection in Clojure is immutable and efficient

#### Sequence terms and what they mean
- a *sequential* collection is one that holds a series of values without reordering them, one of three broad categories of collections along with sets and maps
- a *sequence* is a sequential collection that represents a series of values that may or may not exist yet, can be lazy and empty
- Clojure provides a simple *seq* API consisting of the functions `first` and `rest`, `first` returns `nil` when fed an empty sequence, `rest` returns an empty sequence when no items remain, **never** `nil`
- a *seq* is any object that implements the seq API supporting `first` and `rest`, seq invariant lends itself to concurrent/parallel iteration
- `seq` is available for use on a wide variety of collection-like objects, often calling `seq` on a collection returns a new seq object for navigating that collection
- `seq` returns `nil` when given an empty collection and never an empty collection, functions that promise to return seqs like `next` work the same
- collection partitions help derive equality semantics, two collections in different partitions will never be equal, few collections are actually *seqs*, several are *sequential* like vectors and lists
- if two sequentials have the same values in the same order, the `=` operator returns `true`, `(= #{1 2 3} [1 2 3]) ;=> false`
- many Lisps build their data types on the *cons-cell abstraction*, an elegant two-element structure (a dotted pair of a `car`/`first` and `cadr`/`rest`) akin to a linked list, known as *sequence* abstraction/protocol in Clojure
- functions that return *seqs* in Clojure include `map`, `filter`, `for`, `doseq`, `keys` and all return `nil` when applicable

#### Big-O
- algorithmic complexity is a system for describing the *relative* space and time costs of algorithms, typically described using *Big-O notation*, `O(n)` is read as *order n* and is known as *linear time*
- in practice it is most helpful to focus on the *expected case* of an algorithm instead of the *best case* or *worst case*
- Big-O doesn't concern itself with small workloads and constant factors (eventually no matter how big the constant factor `O(1)` will overtake `O(n)`)
- in Clojure common to see `O(log32 n)` (persistent hash trie) and `O(log2 n)` (sorted structures, accessing elements)

### Vectors: creating and using them in all their varieties
- 


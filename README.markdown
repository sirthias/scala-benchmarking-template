### Scala Micro-Benchmarking Template ###
  
This is an SBT template project for creating micro benchmarks for scala code snippets.
It's not much more than a simple wrapper around [Caliper][1], an open-source library for properly
running benchmark code on the JVM (written by some guys at Google).

Manually writing benchmarks for the JVM that actually measure what you intend to measure is much harder than it
initially appears. There are quite a few [rules][2] you need to keep in mind, so it's best to rely on a framework
that takes care of the details and let's you focus on the code relevant to your application.
[Caliper][1] provides just this framework and this project makes it easily accessible for Scala developers.

#### How to create your own Scala micro-benchmark

1. Git-clone this repository:

        $ git clone git://github.com/sirthias/scala-benchmarking-template.git my-benchmark

2. Change directory into your clone:

        $ cd my-benchmark

3. Launch [SBT](http://code.google.com/p/simple-build-tool):

        $ sbt

4. Get dependencies:

        > update

5. Run the existing benchmark:

        > run

6. Start hacking on `src/main/scala/org/example/Benchmark.scala`

  
As a simple example the project already contains a small benchmark testing the performance of `foreach`ing over a
Scala `Array` against a simple `while` loop as well as a specialized, custom `for` loop replacement implementation.
In order to run your own benchmark code simply replace the respectively marked code snippets with your own.

  [1]: http://code.google.com/p/caliper/
  [2]: http://wikis.sun.com/display/HotSpotInternals/MicroBenchmarks

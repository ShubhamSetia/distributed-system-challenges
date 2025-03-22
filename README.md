# Distributed Systems Challenges

This repository contains my solutions to the [Fly.io Distributed Systems Challenges](https://fly.io/dist-sys/).
This project uses [Maelstrom](https://github.com/jepsen-io/maelstrom) as the testing framework for the distributed system implementations.

## Table of Contents

- [Distributed Systems Challenges](#distributed-systems-challenges)
  - [Table of Contents](#table-of-contents)
  - [Prerequisites](#prerequisites)
  - [Challenges](#challenges)
    - [1. Echo Server](#1-echo-server)
      - [Building and Running the Echo Server](#building-and-running-the-echo-server)


## Prerequisites

- [Clojure](https://clojure.org/guides/getting_started)
- [Leiningen](https://leiningen.org/) (Clojure build tool)
- [Maelstrom](https://github.com/jepsen-io/maelstrom)

## Challenges

### 1. Echo Server

The first challenge is to implement a simple echo server that responds to each request with the same body as the request.

This challenge demonstrates the basic structure of a Maelstrom node and how to handle messages in a distributed system.

#### Building and Running the Echo Server

To build and run the echo server challenge:

1. Navigate to the echo server directory:
   ```
   cd echo/echo_server
   ```

2. Build the project using Leiningen:
   ```
   lein uberjar
   ```

3. Return to the root directory:
   ```
   cd ../..
   ```

4. Run the Maelstrom test for the echo workload:
   ```
   ./maelstrom-bin/maelstrom test -w echo --bin ./echo/run-echo.sh --node-count 1 --time-limit 10
   ```

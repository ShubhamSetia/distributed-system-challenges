# Echo Server Challenge

## Description
This Echo server receives JSON messages on STDIN and responds with the same message content via STDOUT. The server handles two types of messages:
- `init` messages: Sets up the node ID and acknowledges initialization
- `echo` messages: Echoes back the received message content

The server is an implementation of the first challenge in the [Fly.io Distributed Systems Challenges](https://fly.io/dist-sys/1/).

## Building the Project
```bash
# Clean the project
lein clean

# Run tests
lein test

# Build the uberjar
lein uberjar
```

## Running with Maelstrom
To run the Echo server with Maelstrom:

```bash
# From the repository root
./maelstrom-bin/maelstrom test -w echo --bin ./challenges/01-echo-server/run-echo.sh --node-count 1 --time-limit 5
```

## Project Structure
- `src/echo_server/core.clj`: Main implementation with message handling logic
- `test/echo_server/core_test.clj`: Tests for the message handling functions
- `run-echo.sh`: Script to run the server with the compiled JAR

## License

Copyright © 2025

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

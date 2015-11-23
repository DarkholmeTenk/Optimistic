# Optimistic

An implementation of an agent that plays Kalah, for COMP34120.

## Building

Builds are automated at http://darkcraft.io:8080 but are behind a login wall.

```sh
$ gradle build
```

Legacy build tools (won't compile the tests):

```sh
$ make build
```

## Code Overview

Read this if you want to quickly get to grips with what's what.

- `kalah.AgentBootstrap` contains the main method
- `kalah.Controller` controls the flow of operations, receiving messages via the
  `kalah.engine.Listener` class.
- `kalah.game.board` contains classes that represent that state of a game, which
  the agent will use to make its decisions.
- `kalah.engine.Speaker` is a class used by `kalah.Controller` to broadcast the
  decisions made by the `Agent`.

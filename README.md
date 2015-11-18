# Optimistic

An implementation of an agent that plays Kalah, for COMP34120.

## Code Overview

Read this if you want to quickly get to grips with what's what.

- `kalah.AgentBootstrap` contains the main method
- `kalah.Agent` acts as the brain of the agent, receiving messages via the
  `kalah.engine.Listener` class.
- `kalah.game.board` contains classes that represent that state of a game, which
  the agent will use to make its decisions.
- `kalah.engine.Speaker` is a class used by `kalah.Agent` to broadcast its
  decisions.
- `kalah.engine.message` contains classes that represent messages that the
  agent can send or receive.

package controller;

import java.io.IOException;

/**
 * This interface represents a Command.
 */
public interface Command {
  /**
   * Executes the action encapsulated by the given command.
   *
   * @throws IOException if an I/O error occurs.
   */
  void execute() throws IOException;
}


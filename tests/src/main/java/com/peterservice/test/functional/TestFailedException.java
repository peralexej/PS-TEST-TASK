package com.peterservice.test.functional;

import org.junit.runner.notification.Failure;

import java.util.List;

/**
 * Обработка сообщения об ошибке
 */
public class TestFailedException extends Exception {
  private List<Failure> failures;

  /**
   * Instantiates a new Test failed exception.
   */
  public TestFailedException() {
  }

  /**
   * Instantiates a new Test failed exception.
   *
   * @param failures the failures
   */
  public TestFailedException(List<Failure> failures) {
    this.failures = failures;
  }

  @Override
  public String getMessage() {
    StringBuilder msg = new StringBuilder();
    for (Failure failure : failures) {
      msg.append("\n");
      msg.append(failure.getDescription());
      msg.append("\n");
      msg.append(failure.getMessage());
      msg.append("\n\n");
    }
    return msg.toString();
  }
}

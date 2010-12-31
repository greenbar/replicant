// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import static junit.framework.Assert.*;

public class MockBaseMethodMocker<ReturnValue> extends BaseMethodMocker<ReturnValue> {
  
  public MockBaseMethodMocker(Mock mock, String method) {
    super(mock, method, null, null, null);
  }

  public boolean equals(Object that) {
    return this == that;
  }

  private List<Object>          expectArgument;
  private Response<ReturnValue> expectResult;

  public void onCallToExpectReturn(Response<ReturnValue> response) {
    this.expectResult = response;
  }
  
  public Response<ReturnValue> expect(List<Object> arguments) {
    this.expectArgument = arguments;
    return expectResult;
  }
  
  public void assertCalledExpect(List<Object> arguments) {
    assertEquals(arguments, expectArgument);
  }
  
  
  private List<Object>  callArgument;
  private ReturnValue   callResult;
  
  public void onCallToCallReturn(ReturnValue returnValue) {
    this.callResult = returnValue;
  }
  
  public ReturnValue call(List<Object> arguments) throws TestingException {
    callArgument = arguments;
    return callResult;
  }
  
  public void assertCalledCall(List<Object> arguments) {
    assertEquals(arguments, callArgument);
  }
  
  
  private List<Object>      assertCalledArgument;
  private RuntimeException  assertCalledException;
  
  public void onCallToAssertCalledThrow(RuntimeException exception) {
    assertCalledException = exception;
  }
  
  public void assertCalled(List<Object> arguments) {
    assertCalledArgument = arguments;
    throw assertCalledException;
  }
  
  public void assertCalledAssertCalled(List<Object> arguments) {
    assertEquals(arguments, assertCalledArgument);
  }
  
  
  private List<Object>      assertNotCalledWithArgumentsArgument;
  private RuntimeException  assertNotCalledWithArgumentsException;
  
  public void onCallToAssertNotCalledWithArgumentsThrow(RuntimeException exception) {
    assertNotCalledWithArgumentsException = exception;
  }
  
  public void assertNotCalled(List<Object> arguments) {
    assertNotCalledWithArgumentsArgument = arguments;
    throw assertNotCalledWithArgumentsException;
  }
  
  public void assertCalledAssertNotCalledWithArguments(List<Object> arguments) {
    assertEquals(arguments, assertNotCalledWithArgumentsArgument);
  }
  
  
  private List<Object>      assertOnlyCalledArgument;
  private RuntimeException  assertOnlyCalledException;
  
  public void onCallToAssertOnlyCalledThrow(RuntimeException exception) {
    assertOnlyCalledException = exception;
  }
  
  public void assertOnlyCalled(List<Object> arguments) {
    assertOnlyCalledArgument = arguments;
    throw assertOnlyCalledException;
  }
  
  public void assertCalledAssertOnlyCalled(List<Object> arguments) {
    assertEquals(arguments, assertOnlyCalledArgument);
  }
  
  
  private RuntimeException assertCalledOnceException;
  
  public void onCallToAssertCalledOnceThrow(RuntimeException exception) {
    assertCalledOnceException = exception;
  }
  
  public void assertCalledOnce() {
    throw assertCalledOnceException;
  }
  
  
  private RuntimeException assertNotCalledException;
  
  public void onCallToAssertNotCalledThrow(RuntimeException exception) {
    assertNotCalledException = exception;
  }
  
  public void assertNotCalled() {
    throw assertNotCalledException;
  }
  
  
  private RuntimeException assertExpectedCallsMadeException;
  
  public void onCallToAssertExpectedCallsMadeThrow(RuntimeException exception) {
    assertExpectedCallsMadeException = exception;
  }
  
  public void assertExpectedCallsMade() {
    throw assertExpectedCallsMadeException;
  }
  
  
  private RuntimeException assertExpectationsMetException;
  
  public void onCallToAssertExpectationsMetThrow(RuntimeException exception) {
    assertExpectationsMetException = exception;
  }
  
  public void assertExpectationsMet() {
    throw assertExpectationsMetException;
  }
  
}
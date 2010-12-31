// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

final class Call {

  private final Object                  mock;
  private final String                  methodName;
  private final List<? extends Object>  arguments;

  public Call(Object mock, String methodName, List<? extends Object> arguments) {
    this.mock = mock;
    this.methodName = methodName;
    this.arguments = arguments;
  }
  
  public String toString() {
    String argumentsString                = arguments.toString();
    String argumentsStringWithoutBrackets = argumentsString.substring(1, argumentsString.length() - 1);
    return mock + "." + methodName + '(' + argumentsStringWithoutBrackets + ')';
  }
  
  public boolean equals(Object object) {
    if (!(object instanceof Call))
      return false;
    
    Call that = (Call) object;
    return this.mock.equals(that.mock) && 
      this.methodName.equals(that.methodName) && 
      this.arguments.equals(that.arguments);
  }

  public int hashCode() {
    return methodName.hashCode();
  }

}

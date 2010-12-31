// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class MappedResponderTest extends TestCase {

  private Call call1;
  private Call call2;
  private Result<A> call1Result;
  private Result<A> call2Result;
  
  public void setUp() throws Exception {
    call1 = new Call(new Mock(), "method1", list(1));
    call2 = new Call(new Mock(), "method2", list(1, 2));
    
    call1Result = new TestResult<A>();
    call2Result = new TestResult<A>();
  }
  
  public void testResponder() throws Exception {
    MappedResponder<A> responder = new MappedResponder<A>();
    
    responder.addResponseFor(call1, call1Result);
    responder.addResponseFor(call2, call2Result);
    
    Result<A> result1 = responder.resultFor(call1);
    Result<A> result2 = responder.resultFor(call2);
    Result<A> result3 = responder.resultFor(call1);
    
    assertEquals(call1Result, result1);
    assertEquals(call2Result, result2);
    assertEquals(call1Result, result3);
  }
  
  public void testResponderWhenNoResponseHasBeenAddedForCall() throws Exception {
    MappedResponder<A> responder = new MappedResponder<A>();
    responder.addResponseFor(call1, call1Result);
    responder.addResponseFor(call2, call2Result);
    final Call unknownCall = new Call(new Mock(), "unknown", list());
    
    try {
      responder.resultFor(unknownCall);
      fail("Expected an UnknownResponseException");
    } catch (UnknownResponseException e) {
      Map<Call, Result<A>> results = map();
      results.put(call1, call1Result);
      results.put(call2, call2Result);
      Set<Call> callsWithResponses = set(call1, call2);
      StringBuffer expectedMessage = 
        new StringBuffer("There is no response for " + unknownCall + ".\nThere are responses for: ");
      for (Call call : callsWithResponses)
        expectedMessage.append("\n  ").append(call);
      assertEquals(expectedMessage.toString(), e.getMessage());
    }
    
  }
  
  public void testEquality() throws Exception {
    MappedResponder<A> responder1 = new MappedResponder<A>();
    
    assertEquals(false, responder1.equals(null));
    assertEquals(false, responder1.equals("not a Responder"));
    assertEquals(true,  responder1.equals(responder1));
    
    MappedResponder<A> responder2 = new MappedResponder<A>();
    responder2.addResponseFor(call1, call1Result);
    assertEquals(false, responder1.equals(responder2));
    
    responder1.addResponseFor(call1, call1Result);
    assertEquals(true,  responder1.equals(responder2));
    
    responder2.addResponseFor(call2, call2Result);
    responder1.addResponseFor(call2, call1Result);
    assertEquals(false, responder1.equals(responder2));
    
    Responder<A> responder3 = new Responder<A>() {
      public void addResponseFor(Call call, Result<A> response) {
      }
      public Result<A> resultFor(Call call) {
        return null;
      }
    };
    assertEquals(false, responder1.equals(responder3));
  }
  
  private static class TestResult<ReturnValue> implements Result<ReturnValue> {
    public ReturnValue returnValue() {
      return null;
    }
  }
  
}

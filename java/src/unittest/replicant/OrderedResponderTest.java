// Copyright 2009 Kiel Hodges
package replicant;

import java.util.*;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;
import static replicant.collections.CollectionFunctions.*;

public class OrderedResponderTest extends TestCase {

  private static final Mock   MOCK   = new Mock();
  private static final String METHOD = "method";
  
  private Call call1;
  private Call call2;
  private Call call3;
  private Call call4;
  private Result<A> call1Result;
  private Result<A> call2Result;
  
  public void setUp() throws Exception {
    call1 = new Call(MOCK, METHOD, list(1));
    call2 = new Call(MOCK, METHOD, list(2));
    call3 = new Call(MOCK, METHOD, list(3));
    call4 = new Call(MOCK, METHOD, list(4));
    
    call1Result   = new TestResult<A>();
    call2Result   = new TestResult<A>();
  }
  
  public void testResponder() throws Exception {
    OrderedResponder<A> responder = new OrderedResponder<A>();
    
    responder.addResponseFor(call1, call1Result);
    responder.addResponseFor(call2, call2Result);
    Result<A> result1 = responder.resultFor(call1);
    Result<A> result2 = responder.resultFor(call2);
    
    assertEquals(call1Result, result1);
    assertEquals(call2Result, result2);
  }
  
  public void testResponderWhenTooManyCallsAreMade() throws Exception {
    OrderedResponder<A> responder = new OrderedResponder<A>();
    
    responder.addResponseFor(call1, call1Result);
    responder.addResponseFor(call2, call2Result);
    responder.resultFor(call1);
    responder.resultFor(call2);
    
    try {
      responder.resultFor(call3);
      fail("Expected an UnknownResponseException");
    } catch (UnknownResponseException e) {
      Map<Call, Result<A>> responses = map();
      responses.put(call1, call1Result);
      responses.put(call2, call2Result);
      assertEquals("There is no response for " + call3 + " because responses have been exhausted.", 
                   e.getMessage());
    }
  }
  
  public void testResponderWithAnUnexpectedCall() throws Exception {
    OrderedResponder<A> responder = new OrderedResponder<A>();
    
    responder.addResponseFor(call1, call1Result);
    responder.addResponseFor(call2, call2Result);
    responder.addResponseFor(call3, call2Result);
    responder.addResponseFor(call4, call2Result);
    responder.resultFor(call1);
    responder.resultFor(call2);
    
    try {
      responder.resultFor(call4);
      fail("Expected an UnknownResponseException");
    } catch (UnknownResponseException e) {
      Map<Call, Result<A>> responses = map();
      responses.put(call1, call1Result);
      responses.put(call2, call2Result);
      assertEquals("The next response is for " + call3 + ", not for " + call4 + '.', 
                   e.getMessage());
    }
  }
  
  public void testEquality() throws Exception {
    OrderedResponder<A> responder1 = new OrderedResponder<A>();
    
    assertEquals(false, responder1.equals(null));
    assertEquals(false, responder1.equals("not a Responder"));
    assertEquals(true,  responder1.equals(responder1));
    
    OrderedResponder<A> responder2 = new OrderedResponder<A>();
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

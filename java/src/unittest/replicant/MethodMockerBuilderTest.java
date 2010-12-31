// Copyright 2009 Kiel Hodges
package replicant;

import junit.framework.*;
import replicant.testing.testobjects.TestValue.*;

public class MethodMockerBuilderTest extends TestCase {

  private static final Mock         MOCK          = new Mock();
  private static final String       METHOD        = "method";
  
  private TestResponsePolicy     responsePolicy;
  private MethodMockerBuilder<X> builder;
  
  public void setUp() throws Exception {
    responsePolicy = new TestResponsePolicy();
    TestReturnPolicy returnPolicy = new TestReturnPolicy(responsePolicy);
    builder = new MethodMockerBuilder<X>(MOCK, METHOD, returnPolicy);
  }
  
  public void testBuilderWithDefaults() throws Exception {
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new MappedResponder<X>(), responsePolicy, new LenientExpectationEnforcer());
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMockerMappingResponses() throws Exception {
    MethodMockerBuilder<X> builder2 = builder.mappingResponses();
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new MappedResponder<X>(), responsePolicy, new LenientExpectationEnforcer());
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMockerOrderingResponses() throws Exception {
    MethodMockerBuilder<X> builder2 = builder.orderingResponses();
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new OrderedResponder<X>(), responsePolicy, new LenientExpectationEnforcer());
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMockerEnforcingExpectationsWithGivenExpecter() throws Exception {
    TestExpecter expecter = new TestExpecter();
    
    MethodMockerBuilder<X> builder2 = builder.enforcingExpectationsWith(expecter);
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new MappedResponder<X>(), responsePolicy, expecter);
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMockerEnforcingExpectationsStrictly() throws Exception {
    MethodMockerBuilder<X> builder2 = builder.enforcingExpectationsStrictly();
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new MappedResponder<X>(), responsePolicy, new StrictExpectationEnforcer());
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMockerMappingResponsesEnforcingExpectationsWithGivenExpecter() throws Exception {
    TestExpecter expecter = new TestExpecter();
    
    MethodMockerBuilder<X> builder2 = builder.mappingResponses().enforcingExpectationsWith(expecter);
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new MappedResponder<X>(), responsePolicy, expecter);
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMappingResponsesMockerEnforcingExpectationsStrictly() throws Exception {
    MethodMockerBuilder<X> builder2 = builder.mappingResponses().enforcingExpectationsStrictly();
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new MappedResponder<X>(), responsePolicy, new StrictExpectationEnforcer());
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingMockerOrderingResponsesEnforcingExpectationsWithGivenExpecter() throws Exception {
    TestExpecter expecter = new TestExpecter();
    MethodMockerBuilder<X> builder2 = builder.orderingResponses().enforcingExpectationsWith(expecter);
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new OrderedResponder<X>(), responsePolicy, expecter);
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  public void testBuildingOrderingResponsesMockerEnforcingExpectationsStrictly() throws Exception {
    MethodMockerBuilder<X> builder2 = builder.orderingResponses().enforcingExpectationsStrictly();
    
    BaseMethodMocker<X> baseMethodMocker = 
      new BaseMethodMocker<X>(MOCK, METHOD, new OrderedResponder<X>(), responsePolicy, new StrictExpectationEnforcer());
    assertEquals(new MethodMocker<X>(MOCK, METHOD, baseMethodMocker),            builder2.<X>withUntypedArgs());
    assertEquals(new MethodMocker0<X>(MOCK, METHOD, baseMethodMocker),           builder2.<X>with0Args());
    assertEquals(new MethodMocker1<X,A>(MOCK, METHOD, baseMethodMocker),         builder2.<X,A>with1Arg());
    assertEquals(new MethodMocker2<X,A,B>(MOCK, METHOD, baseMethodMocker),       builder2.<X,A,B>with2Args());
    assertEquals(new MethodMocker3<X,A,B,C>(MOCK, METHOD, baseMethodMocker),     builder2.<X,A,B,C>with3Args());
    assertEquals(new MethodMocker4<X,A,B,C,D>(MOCK, METHOD, baseMethodMocker),   builder2.<X,A,B,C,D>with4Args());
    assertEquals(new MethodMocker5<X,A,B,C,D,E>(MOCK, METHOD, baseMethodMocker), builder2.<X,A,B,C,D,E>with5Args());
  }
  
  private static final class TestReturnPolicy implements ReturnPolicy {
    private final TestResponsePolicy responsePolicy;
    
    public TestReturnPolicy(TestResponsePolicy responsePolicy) {
      this.responsePolicy = responsePolicy;
    }

    public ResponsePolicy<?> responsePolicy() {
      return responsePolicy;
    }
  }

  private static class TestResponsePolicy implements ResponsePolicy<X> {
    public StandardResponse<X> createExpectedResponse(Call call) {
      return null;
    }
    public Result<X> resultFor(Call call, Responder<X> responder) throws UnknownResponseException {
      return null;
    }
  }

  private static class TestExpecter implements ExpectationEnforcer {
    public void expect(Call call) {
    }
    public void call(Call call) throws AssertionFailedError {
    }
    public void assertExpectationsMet() throws AssertionFailedError {
    }
  }
}

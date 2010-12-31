// Copyright 2009 Kiel Hodges
package replicant.sample2;

import replicant.*;
import static replicant.Replicant.*;

public class MockWidgetPainter implements WidgetPainter {

  public MockWidgetPainter(ExpectationEnforcer expectationEnforcer) {
    paint = voidMethodMocker(this, "paint").
      mappingResponses().
      enforcingExpectationsWith(expectationEnforcer).
      with1Arg();
  }

  public void paint(Widget widget) {
    paint.call(widget);
  }
  
  public final MethodMocker1<Void, Widget> paint; 
  
}

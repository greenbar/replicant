// Copyright 2009 Kiel Hodges
package replicant.sample3;

import replicant.*;
import static replicant.Replicant.*;

public class MockWidgetPainter implements WidgetPainter {

  public MockWidgetPainter(Expecter expecter) {
    paint = voidMethodMocker(this, "paint").
      mappingResponses().
      enforcingExpectationsWith(expecter).
      with1Arg();
  }

  public void paint(Widget widget) {
    paint.call(widget);
  }
  
  public final MethodMocker1<Void, Widget> paint; 
  
}

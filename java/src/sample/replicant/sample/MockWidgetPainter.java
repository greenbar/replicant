// Copyright 2009 Kiel Hodges
package replicant.sample;

import replicant.*;
import static replicant.Replicant.*;

public class MockWidgetPainter implements WidgetPainter {

  public void paint(Widget widget) {
    paint.call(widget);
  }
  
  public final MethodMocker1<Void, Widget> paint = voidMethodMocker(this, "paint").mappingResponses().with1Arg();
  
}

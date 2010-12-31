// Copyright 2009 Kiel Hodges
package replicant.sample;

import replicant.*;
import static replicant.Replicant.*;

public class MockWidgetRepository implements WidgetRepository {

  
  public Widget findWidget(int widgetId) {
    return findWidget.call(widgetId);
  }
  
  public final MethodMocker1<Widget, Integer> findWidget = 
    methodMocker(this, "findWidget").mappingResponses().with1Arg();
  
  
  
  public void storeWidget(Widget widget) {
    storeWidget.call(widget);
  }

  public final MethodMocker1<Void, Widget> storeWidget = 
    voidMethodMocker(this, "storeWidget").enforcingExpectationsStrictly().mappingResponses().with1Arg();
  
}

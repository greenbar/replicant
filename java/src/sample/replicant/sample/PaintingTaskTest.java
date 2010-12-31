// Copyright 2009 Kiel Hodges
package replicant.sample;

import junit.framework.*;
import replicant.*;

// TODO Add a sample of an unexpected ResponseException 
public class PaintingTaskTest extends TestCase {

  private static final Widget WIDGET1 = new Widget(1);
  private static final Widget WIDGET2 = new Widget(2);
  private static final Widget WIDGET3 = new Widget(3);
  
  private PaintingTask         paintingTask;
  private MockRequestQueue     requestQueue;
  private MockWidgetPainter    painter;
  private MockWidgetRepository widgetRepository;

  public void setUp() throws Exception {
    requestQueue      = new MockRequestQueue();
    painter           = new MockWidgetPainter();
    widgetRepository  = new MockWidgetRepository();
    paintingTask      = new PaintingTask(requestQueue, painter, widgetRepository);
  }
  
  public void testTask() throws Exception {
    requestQueue.nextRequest.expect().returning(new Request(17));
    requestQueue.nextRequest.expect().returning(new Request(42));
    requestQueue.nextRequest.expect().returning(new Request(37));
    requestQueue.nextRequest.expect().returning(null);
    widgetRepository.findWidget.expect(17).returning(WIDGET1);
    widgetRepository.findWidget.expect(42).returning(WIDGET2);
    widgetRepository.findWidget.expect(37).returning(WIDGET3);
    widgetRepository.storeWidget.expect(WIDGET1).checking(thatPainterPainted(WIDGET1));
    widgetRepository.storeWidget.expect(WIDGET2).checking(thatPainterPainted(WIDGET2));
    widgetRepository.storeWidget.expect(WIDGET3).checking(thatPainterPainted(WIDGET3));
    
    paintingTask.run();
    
    widgetRepository.storeWidget.assertExpectedCallsMade();
    widgetRepository.storeWidget.assertCalled(WIDGET1);
    widgetRepository.storeWidget.assertCalled(WIDGET2);
    widgetRepository.storeWidget.assertCalled(WIDGET3);
  }

  private Check thatPainterPainted(final Widget widget) {
    return new Check() {
      public void check() {
        painter.paint.assertCalled(widget);
      }
    };
  }
  
}

// Copyright 2009 Kiel Hodges
package replicant.sample2;

import static replicant.Replicant.*;

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
  private ExpectationEnforcer  expectationEnforcer;

  public void setUp() throws Exception {
    expectationEnforcer = strictExpectationEnforcer();
    requestQueue      = new MockRequestQueue(expectationEnforcer);
    painter           = new MockWidgetPainter(expectationEnforcer);
    widgetRepository  = new MockWidgetRepository(expectationEnforcer);
    paintingTask      = new PaintingTask(requestQueue, painter, widgetRepository);
  }
  
  public void testTask() throws Exception {
    requestQueue.nextRequest.expect().returning(new Request(17));
    widgetRepository.findWidget.expect(17).returning(WIDGET1);
    painter.paint.expect(WIDGET1);
    widgetRepository.storeWidget.expect(WIDGET1);
    
    requestQueue.nextRequest.expect().returning(new Request(42));
    widgetRepository.findWidget.expect(42).returning(WIDGET2);
    painter.paint.expect(WIDGET2);
    widgetRepository.storeWidget.expect(WIDGET2);
    
    requestQueue.nextRequest.expect().returning(new Request(37));
    widgetRepository.findWidget.expect(37).returning(WIDGET3);
    painter.paint.expect(WIDGET3);
    widgetRepository.storeWidget.expect(WIDGET3);
    
    requestQueue.nextRequest.expect().returning(null);
    
    paintingTask.run();
    
    expectationEnforcer.assertExpectationsMet();
  }

}

// Copyright 2011 Kiel Hodges
package sample2

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

import replicant._

@RunWith(classOf[JUnitRunner])
class PaintingTaskTest extends junit.JUnit3Suite with ShouldMatchers {

  private val widget1: Widget = Widget(1);
  private val widget2 = Widget(2);
  private val widget3 = Widget(3);

  @Test def testPaintTask {
    val requestQueue     = new MockRequestQueue
    val painter          = new MockWidgetPainter
    val widgetRepository = new MockGenericRepository[Widget]
    val paintingTask     = new PaintingTask(requestQueue, painter, widgetRepository);

    val requests = scala.collection.mutable.Queue(Some(Request(17)), Some(Request(42)), Some(Request(37)), None)
    requestQueue.method.nextRequest.response = { requests.dequeue }
    widgetRepository.method.findById(17).response = widget1;
    widgetRepository.method.findById(42).response = widget2;
    widgetRepository.method.findById(37).response = widget3;
    widgetRepository.method.store(widget1).response = { painter.method.paintWidget(widget1).assertCalled() };
    widgetRepository.method.store(widget2).response = { painter.method.paintWidget(widget2).assertCalled() };
    widgetRepository.method.store(widget3).response = { painter.method.paintWidget(widget3).assertCalled() };

    paintingTask.run
    
    widgetRepository.method.store.assertAllResponsesUsed()
  }
  
}

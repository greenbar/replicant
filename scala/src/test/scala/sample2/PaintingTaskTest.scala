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

  private val widget1 = Widget(1)
  private val widget2 = Widget(2)
  private val widget3 = Widget(3)

  @Test def testPaintTask {
    val requestQueue     = new MockRequestQueue
    val painter          = new MockWidgetPainter
    val widgetRepository = new MockGenericRepository[Widget]
    val paintingTask     = new PaintingTask(requestQueue.testDouble, painter.testDouble, widgetRepository.testDouble)

    val requests = scala.collection.mutable.Queue(Some(Request(17)), Some(Request(42)), Some(Request(37)), None)
    requestQueue.nextRequest.response = { requests.dequeue }
    widgetRepository.findById(17).response = widget1
    widgetRepository.findById(42).response = widget2
    widgetRepository.findById(37).response = widget3
    widgetRepository.store(widget1).response = { painter.paintWidget(widget1).assertCalled() }
    widgetRepository.store(widget2).response = { painter.paintWidget(widget2).assertCalled() }
    widgetRepository.store(widget3).response = { painter.paintWidget(widget3).assertCalled() }

    paintingTask.run
    
    widgetRepository.store.assertAllResponsesUsed()
  }
  
}

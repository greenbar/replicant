// Copyright 2011 Kiel Hodges
package sample

import org.scalatest._
import org.scalatest.matchers._
import org.scalatest.junit.JUnitRunner
import org.junit._
import org.junit.runner._
import testing._

import replicant._

@RunWith(classOf[JUnitRunner])
class PaintingTaskTest extends junit.JUnit3Suite with ShouldMatchers {

  import scala.collection.mutable

  private val widget1 = Widget(1)
  private val widget2 = Widget(2)
  private val widget3 = Widget(3)

  @Test def testPaintTask {
    val requestQueue     = new MockRequestQueue
    val painter          = new MockWidgetPainter
    val widgetRepository = new MockGenericRepository[Widget]
    val paintingTask     = new PaintingTask(requestQueue, painter, widgetRepository)

    val requests = mutable.Queue(Some(Request(17)), Some(Request(42)), Some(Request(37)), None)
    requestQueue.nextRequest.expect { 
      requests.dequeue 
    }
    widgetRepository.findById.expect(17) {widget1}
    widgetRepository.findById.expect(42) {widget2}
    widgetRepository.findById.expect(37) {widget3}
    for (widget <- List(widget1, widget2, widget3))
      widgetRepository.store.expect(widget) { painter.paintWidget.assertCalled(widget) }
    
    paintingTask.run
    
    widgetRepository.store.assertExpectationsMet
  }
  
  @Test def testOrderingResponses {
    val queue = new MockRequestQueue
    val mockedQueue: RequestQueue = queue
    val requests = mutable.Queue(Some(Request(1)), Some(Request(2)), Some(Request(3)), None)
    queue.nextRequest.expect {
      requests.dequeue
    }
    mockedQueue.nextRequest should equal(Some(Request(1)))
    mockedQueue.nextRequest should equal(Some(Request(2)))
    mockedQueue.nextRequest should equal(Some(Request(3)))
    mockedQueue.nextRequest should equal(None)
    intercept[java.util.NoSuchElementException] { mockedQueue.nextRequest }
  }
  
  class Mocker2[Args1, Args2, Result](mock: Any, methodName: String) {
    def assertCalled(args1: Args1)(args2: Args2) {}
    def expect(args1: Args1)(args2: Args2)(response: => Result) {}
    def apply(args1: Args1)(args2: Args2): Result = {throw new RuntimeException("not implemented")}
  }
    
  private trait Foo {
    def add(x: Int, y: Int)(z: Int): Int
  }
  
  private class MockFoo extends Foo { mock =>
    import replicant._
    object method {
      val add = Mocker[((Int, Int), Int), Int](mock, "add")
    }
    def add(x: Int, y: Int)(z: Int): Int = method.add( (x, y), (z) ) 
  }

  @Test def testMultipleParameterLists {
    val mock = new MockFoo
    mock.method.add.expect( (10, 5), (1) ) { 101 }
    mock.method.add.expect( (10, 5), (2) ) { 102 }
    mock.method.add.expect( (20, 3), (1) ) { 201 }
    val add10 = mock.add(10, 5) _
    val add20: Int => Int = mock.add(20, 3)
    
    add10(1) should equal(101)
    add10(2) should equal(102)
    add20(1) should equal(201)
    
    mock.method.add.assertCalled( (10, 5), (1) )
  }
  
}
// Copyright 2011 Kiel Hodges
package sample2

import replicant._
import experiment1._

class MockWidgetPainter extends XMockController[WidgetPainter] { self =>

  val testDouble: WidgetPainter = new WidgetPainter {
    def paintWidget(widget: Widget) { self.paintWidget(widget).response }
  }

  val paintWidget = method("paintWidget", testDouble.paintWidget _)
  
}

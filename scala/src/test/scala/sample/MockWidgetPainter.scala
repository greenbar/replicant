// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockWidgetPainter extends MockController[WidgetPainter] { self =>

  val mock: WidgetPainter = new WidgetPainter {
    def paintWidget(widget: Widget) { self.paintWidget(widget) }
  }

  val paintWidget = method("paintWidget", mock.paintWidget _)

}

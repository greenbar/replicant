// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockWidgetPainter extends MockMinder[WidgetPainter] { minder =>

  val mock: WidgetPainter = new WidgetPainter {
    def paintWidget(widget: Widget) { minder.paintWidget(widget) }
  }

  val paintWidget = method("paintWidget", mock.paintWidget _)

}

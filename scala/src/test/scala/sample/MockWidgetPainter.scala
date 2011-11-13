// Copyright 2011 Kiel Hodges
package sample

import replicant._

class MockWidgetPainter extends MockController[WidgetPainter] { controller =>

  val mock: WidgetPainter = new WidgetPainter {
    def paintWidget(widget: Widget) { controller.paintWidget(widget) }
  }

  val paintWidget = method("paintWidget", mock.paintWidget _)

}

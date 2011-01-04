// Copyright 2011 Kiel Hodges
package sample

class MockWidgetPainter extends WidgetPainter { mock =>
  object method {
    import replicant._
    val paintWidget = Mocker[Widget, Unit](mock, "paintWidget")
  }
  def paintWidget(widget: Widget) = method.paintWidget(widget)
}

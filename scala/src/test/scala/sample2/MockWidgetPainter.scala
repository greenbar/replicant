// Copyright 2011 Kiel Hodges
package sample2

class MockWidgetPainter extends WidgetPainter { mock =>
  object method {
    import replicant._
    val paintWidget: ArgList[Widget, Result[Unit]] = Replicant.with1ArgList(mock, "paintWidget")
  }
  def paintWidget(widget: Widget) = method.paintWidget(widget).response
}

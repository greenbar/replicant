package sample

class MockWidgetRepository extends WidgetRepository { mock =>
  object method {
    import replicant._
    val storeWidget = Mocker[Widget, Unit](mock, "storeWidget")
    val findWidget  = Mocker[Long, Widget](mock, "findWidget")
  }
  def storeWidget(widget: Widget) = method.storeWidget(widget)
  def findWidget(widgetId: Long)  = method.findWidget(widgetId)
}

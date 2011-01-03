package sample

trait WidgetRepository {
  def storeWidget(widget: Widget): Unit
  def findWidget(widgetId: Long): Widget
}


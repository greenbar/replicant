// Copyright 2011 Kiel Hodges
package sample

class PaintingTask(requestQueue: RequestQueue, painter: WidgetPainter, widgetRepository: WidgetRepository) {

  def run {
    def requests: Stream[Option[Request]] = requestQueue.nextRequest #:: requests 
    for (Some(request) <- requests.takeWhile(_.isDefined)) {
      val widget = widgetRepository.findWidget(request.widgetId)
      painter.paintWidget(widget)
      widgetRepository.storeWidget(widget)
    }
  }
  
}


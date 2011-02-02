// Copyright 2011 Kiel Hodges
package sample2

class PaintingTask(
  requestQueue: RequestQueue, 
  painter: WidgetPainter, 
  widgetRepository: GenericRepository[Widget]
) {

  def run {
    def requests: Stream[Option[Request]] = requestQueue.nextRequest #:: requests 
    for (Some(request) <- requests.takeWhile(_.isDefined)) {
      val widget = widgetRepository.findById(request.widgetId)
      painter.paintWidget(widget)
      widgetRepository.store(widget)
    }
  }
  
}


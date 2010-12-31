// Copyright 2009 Kiel Hodges
package replicant.sample3;

public class PaintingTask {

  private final RequestQueue     requestQueue;
  private final WidgetPainter    painter;
  private final WidgetRepository repository;

  public PaintingTask(RequestQueue requestQueue, WidgetPainter painter, WidgetRepository widgetRepository) {
    this.requestQueue = requestQueue;
    this.painter      = painter;
    this.repository   = widgetRepository;
  }

  public void run() {
    while (true) {
      Request request = requestQueue.nextRequest();
      if (request == null)
        break;
      Widget  widget  = repository.findWidget(request.widgetId());
      painter.paint(widget);
      repository.storeWidget(widget);
    }
  }
  
}

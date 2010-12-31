// Copyright 2009 Kiel Hodges
package replicant.sample;

public interface WidgetRepository {

  Widget findWidget(int widgetId);
  void storeWidget(Widget widget);
  
}

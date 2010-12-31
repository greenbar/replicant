// Copyright 2009 Kiel Hodges
package replicant.sample2;

public interface WidgetRepository {

  Widget findWidget(int widgetId);
  void storeWidget(Widget widget);
  
}

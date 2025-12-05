package com.grupp5.agila_schemalggare.utils;

import java.time.LocalDateTime;

public interface DynamicController {
  void updateView();
  void setCurrentDate(LocalDateTime date);
}

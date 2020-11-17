package com.thoughtworks.capability.gtb;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 对任意日期获取下一个工作日, 不考虑节假日
 *
 * @author itutry
 * @create 2020-05-15_17:20
 */
public class Practice2 {

  public static LocalDate getNextWorkDate(LocalDate date) {
    final int ONE_DAY = 1;
    final int FRIDAY_DAY_OF_WEEK = 4;
    final int DAYS_OF_WEEK = 7;
    int nexDayOfWeek = date.getDayOfWeek().ordinal() + ONE_DAY;
    int dayBetweenNowAndNextWorkDay = ONE_DAY;
    while (nexDayOfWeek > FRIDAY_DAY_OF_WEEK) {
      dayBetweenNowAndNextWorkDay++;
      nexDayOfWeek++;
      nexDayOfWeek %= DAYS_OF_WEEK;
    }
    return date.plusDays(dayBetweenNowAndNextWorkDay);
  }
}

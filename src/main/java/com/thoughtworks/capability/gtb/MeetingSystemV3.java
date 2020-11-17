package com.thoughtworks.capability.gtb;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 脑洞会议系统v3.0
 * 1.当前会议时间"2020-04-01 14:30:00"表示伦敦的本地时间，而输出的新会议时间是芝加哥的本地时间
 * 场景：
 * a:上个会议是伦敦的同事定的，他在界面上输入的时间是"2020-04-01 14:30:00"，所以我们要解析的字符串是伦敦的本地时间
 * b:而我们在当前时区(北京时区)使用系统
 * c:我们设置好新会议时间后，要发给芝加哥的同事查看，所以格式化后的新会议时间要求是芝加哥的本地时间
 * 2.用Period来实现下个会议时间的计算
 *
 * @author itutry
 * @create 2020-05-19_18:43
 */
public class MeetingSystemV3 {

    public static void main(String[] args) {
        String timeStr = "2020-04-01 14:30:00";
        ZoneId londonZoneId = ZoneId.of("Europe/London");
        ZoneId pekingZoneId = ZoneId.of("Asia/Shanghai");
        ZoneId chicagoZoneId = ZoneId.of("America/Chicago");

        // 根据格式创建格式化类
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 从字符串解析得到会议时间
        LocalDateTime meetingTime = LocalDateTime.parse(timeStr, formatter);
        ZonedDateTime londonMeetingTime = ZonedDateTime.of(meetingTime, londonZoneId);
        LocalDateTime pekingMeetingTime = londonMeetingTime.withZoneSameInstant(pekingZoneId).toLocalDateTime();

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(pekingMeetingTime)) {
            Period delayDateTime = Period.between(pekingMeetingTime.toLocalDate(), now.toLocalDate());
            pekingMeetingTime = pekingMeetingTime.plus(delayDateTime);
            if (now.isAfter(pekingMeetingTime)) {
                pekingMeetingTime = pekingMeetingTime.plusDays(1);
            }

            // 格式化新会议时间
            LocalDateTime chicagoMeetingTime = ZonedDateTime.of(pekingMeetingTime, pekingZoneId)
                    .withZoneSameInstant(chicagoZoneId)
                    .toLocalDateTime();
            String showTimeStr = formatter.format(chicagoMeetingTime);
            System.out.println(showTimeStr);
        } else {
            System.out.println("会议还没开始呢");
        }
    }
}

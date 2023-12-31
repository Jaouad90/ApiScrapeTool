package com.a1.apiscraper.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TimeInterval {

    @Id
    @GeneratedValue
    private Long Id;

    private String intervalName;

    @Transient
    private List<LocalTime> timeList = new ArrayList<>();

    public List<LocalTime> getTimeList() {
        LocalDate date = LocalDate.of(2014, 1, 1); // arbitrary date
        LocalDateTime tsp = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        switch (intervalName) {
            case "Halfuur":
                do {
                    tsp = tsp.plus(Duration.ofHours(0).plusMinutes(30));
                    LocalTime localTime = tsp.toLocalTime();
                    timeList.add(localTime);
                } while (date.equals(tsp.toLocalDate()));
                return timeList;
            case "uur":
                do  {
                    tsp = tsp.plus(Duration.ofHours(0).plusMinutes(60));
                    LocalTime localTime = tsp.toLocalTime();
                    timeList.add(localTime);
                } while (date.equals(tsp.toLocalDate()));
                return timeList;
            case "6 uur":
                do  {
                    tsp = tsp.plus(Duration.ofHours(0).plusMinutes(360));
                    LocalTime localTime = tsp.toLocalTime();
                    timeList.add(localTime);
                } while (date.equals(tsp.toLocalDate()));
                return timeList;

        }
        return  timeList;
    }
}

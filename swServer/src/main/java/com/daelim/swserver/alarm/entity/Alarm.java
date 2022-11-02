package com.daelim.swserver.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_alarm")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {

    @Id
    private String alarmId;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private int days;
    @Column(nullable = false)
    private boolean isRepeat;

    //ManyToOne
    //@JoinColumn(name = "USER_ID")
    //private User user;

}

package com.hackaton.book.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Status {
    private String status;
    private Long numberBooking;
    private int rankBooking;
}

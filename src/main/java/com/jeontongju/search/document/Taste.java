package com.jeontongju.search.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Taste {

    private Long sour;
    private Long sweet;
    private Long scent;
    private Long carbonation;
    private Long body;

}

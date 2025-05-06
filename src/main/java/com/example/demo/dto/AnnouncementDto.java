package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDto {
    private Long id;
    private Integer addressId;//*
    private String itemNumber;//*
    private String announcementName;//*
    private String type;
    private LocalDateTime publishTime;//*
    private LocalDateTime openTime;
    private String fileAcquireEndTime;
    private String fileName;
    private String resultType;
    private String noticeUrl;//*
    private String flag;
    private String insertTime;
    private String labels;
    private String city;//*
}

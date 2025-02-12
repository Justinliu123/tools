package com.example.demo.dto;

import lombok.Data;

@Data
public class AnnouncementDto {
    private Integer id;
    private Integer addressId;//*
    private String itemNumber;//*
    private String announcementName;//*
    private String type;
    private String publishTime;//*
    private String openTime;
    private String fileAcquireEndTime;
    private String fileName;
    private String resultType;
    private String noticeUrl;//*
    private String flag;
    private String insertTime;
    private String labels;
    private String city;//*
}

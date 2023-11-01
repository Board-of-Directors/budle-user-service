package ru.nsu.fit.directors.userservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class ResponseOrderDto {
    private Long id;
    private Integer guestCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time endTime;
    private Integer status;
    private Long establishmentId;
    private String guestName;
    private String image = null;
}

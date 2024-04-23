package com.cqupt.software_9.vo;

import com.cqupt.software_9.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notificationtest {
    private Notification notification;
    private String uid;
    private String username;
}

package com.siukatech.poc.react.backend.parent.business.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
//@EqualsAndHashCode(callSuper = true)
public class UserDto {
    private String id;
    private String loginId;
    private String name;
    private String publicKey;
//    private String privateKey;
    private String createdBy;
    private LocalDateTime createdDatetime;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDatetime;
}


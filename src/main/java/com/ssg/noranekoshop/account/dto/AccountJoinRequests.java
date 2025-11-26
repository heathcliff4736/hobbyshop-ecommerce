package com.ssg.noranekoshop.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountJoinRequests {
    private String loginId;
    private String loginPw;
    private String name;
    private String phone;
    private String address;
}

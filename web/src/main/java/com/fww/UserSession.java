package com.fww;

import lombok.Data;

import java.util.*;

/**
 * @author Administrator
 * @date 2018/04/29 18:24
 */
@Data
public class UserSession {
    private static final long serialVersionUID = 1139273785761972085L;
    private String token;
    private Integer userId;
    private String userName;
    private Date loginTime;
    private String status;
    private String mobile;
    private String email;
    private String IDCardNo;
    private String account;
    private List<String> dataAuthority;
    private Integer isPerson;
    private Integer orgId;
    private String orgCode;
    private String orgName;
    private Integer roleId;
    private String roleName;
    private Set<String> roles;
    private Set<String> permissions;
    private List<Map<String, String>> parentOrgList = new ArrayList();
    private String dataExportAuthority;
    private String dataExportConfigure;
}

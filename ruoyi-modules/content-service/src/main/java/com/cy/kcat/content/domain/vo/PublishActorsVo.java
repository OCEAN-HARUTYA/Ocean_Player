package com.cy.kcat.content.domain.vo;

import lombok.Data;

@Data
public class PublishActorsVo {
    private Long actorId;
    private String actorName;
    private String roleName;
    private Integer roleType;
    private Boolean isNewActor;
    private Integer sortOrder;
}

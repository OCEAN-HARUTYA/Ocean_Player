package com.cy.kcat.content.domain.vo;

import com.cy.kcat.content.domain.Episodes;
import com.cy.kcat.content.domain.bo.EpisodesBo;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class DramaPublishVo {
    private DramasVo drama;
    private List<Long> categories;
    private List<Long> tags;
    private List<PublishActorsVo> actors;
    private List<EpisodesBo> episodes;
}

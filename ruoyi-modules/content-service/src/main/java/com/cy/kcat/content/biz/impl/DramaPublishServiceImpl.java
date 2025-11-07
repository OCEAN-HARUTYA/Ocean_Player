package com.cy.kcat.content.biz.impl;

import com.cy.kcat.content.biz.DramaPublishService;
import com.cy.kcat.content.domain.*;
import com.cy.kcat.content.domain.bo.EpisodesBo;
import com.cy.kcat.content.domain.vo.DramaPublishVo;
import com.cy.kcat.content.domain.vo.DramasVo;
import com.cy.kcat.content.domain.vo.PublishActorsVo;
import com.cy.kcat.content.mapper.ActorsMapper;
import com.cy.kcat.content.mapper.DramaCategoriesMapper;
import com.cy.kcat.content.mapper.DramasMapper;
import com.cy.kcat.content.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class DramaPublishServiceImpl implements DramaPublishService {
    @Autowired
    DramasMapper dramasMapper;
    @Autowired
    DramaCategoriesService dramaCategoriesService;
    @Autowired
    IDramasService dramasService;
    @Autowired
    DramaTagsService dramaTagsService;
    @Autowired
    DramaActorsService dramaActorsService;
    @Autowired
    ActorsMapper actorsMapper;
    @Autowired
    IEpisodesService episodesService;
    @Autowired
    DramaCategoriesMapper  dramaCategoriesMapper;

    /**
     * 单表多个操作，和多表操作都应该加事务
     *
     * @param dramaPublishVo
     * @return
     */
    @Transactional
    @Override
    public Long publishDrama(DramaPublishVo dramaPublishVo) {

        //1.从Vo解析出短剧信息，保存数据库，生成短剧ID
        Dramas dramas = buildDramaEntity(dramaPublishVo);
        dramasMapper.insert(dramas);
        //dramasService.insertByBo(dramas);
        //拿到主键ID 自增
        Long dramaId = dramas.getDramaId();
        log.info("短剧保存完成:{}", dramas);
        //=============所有数据都要给数据库保存短剧ID===============
        //2.保存短剧和分类的关联关系
        //处理所有的分类ID,变为数据库能存的 DTO
        List<DramaCategories> dramaCategories = buildDramaCategoryEntity(dramaPublishVo, dramaId);
        dramaCategoriesService.saveBatch(dramaCategories);
        log.info("分类保存完成:{}", dramaCategories);
        //3.保存短剧和标签的关联关系
        List<DramaTags> dramaTagsEntity = getDramaTagsEntity(dramaPublishVo, dramaId);
        dramaTagsService.saveBatch(dramaTagsEntity);
        log.info("分类保存完成:{}", dramaTagsEntity);
        //4.保存演员数据
        //  1.数据库已有演员，只需要保存关联关系
        //  2.数据库没有的演员， 先保存演员，再保存关系。
        List<PublishActorsVo> actors = dramaPublishVo.getActors();
        for (PublishActorsVo actor : actors) {
            if (actor.getActorId() != null && actor.getActorId() > 0) {
                //如果是已经有的演员
                DramaActors dramaActors = getDramaActors(dramaId, actor.getActorId(), actor);
                dramaActorsService.save(dramaActors);
            } else {
                //如果是新演员 先保存演员，再保存关系
                //构建演员
                Actors actorsEntity = ActorsEntity(actor);
                //保存演员。
                actorsMapper.insert(actorsEntity);
                // 构建演员和短剧关系
                Long actorId = actorsEntity.getActorId();
                DramaActors dramaActors = getDramaActors(dramaId,actorId,actor);
                dramaActorsService.save(dramaActors);
            }
        }

        //5.保存剧集数据
        List<EpisodesBo> episodes = dramaPublishVo.getEpisodes();
        for (EpisodesBo episode : episodes) {
            episode.setDramaId(dramaId);
            episodesService.insertByBo(episode);

        }
        return dramaId;
    }

    private static DramaActors getDramaActors(Long dramaId, Long actor, PublishActorsVo actor1) {
        DramaActors dramaActors = new DramaActors();
        dramaActors.setDramaId(dramaId);
        dramaActors.setActorId(actor);
        dramaActors.setRoleName(actor1.getRoleName());
        dramaActors.setRoleType(actor1.getRoleType());
        dramaActors.setSortOrder(0);
        dramaActors.setCreateTime(new Date());
        return dramaActors;
    }

    //创建新演员
    private static Actors ActorsEntity(PublishActorsVo actor) {
        Actors actorsEntity = new Actors();

        actorsEntity.setActorName(actor.getActorName());
        actorsEntity.setActorImg("");
        actorsEntity.setActorInfo("");
        actorsEntity.setHeight(0L);
        actorsEntity.setWeight(0L);
        actorsEntity.setConstellation("");
        actorsEntity.setNationality("");
        actorsEntity.setDramaCount(0L);
        actorsEntity.setFansCount(0L);
        actorsEntity.setIsHot(0L);
        actorsEntity.setStatus(0L);
        actorsEntity.setSearchValue("");
        actorsEntity.setCreateDept(0L);
        actorsEntity.setCreateBy(0L);
        actorsEntity.setCreateTime(new Date());
        actorsEntity.setUpdateBy(0L);
        actorsEntity.setUpdateTime(new Date());
        return actorsEntity;
    }


    private static List<DramaTags> getDramaTagsEntity(DramaPublishVo dramaPublishVo, Long dramaId) {
        List<Long> tags = dramaPublishVo.getTags();
        List<DramaTags> dramaTagsEntity = tags.stream().map(
            val -> {

                DramaTags dramaTags = new DramaTags();
                dramaTags.setDramaId(dramaId);
                dramaTags.setTagId(val);
                dramaTags.setCreateBy(0L);
                dramaTags.setUpdateBy(0L);
                dramaTags.setCreateDept(0L);
                dramaTags.setCreateTime(new Date());
                return dramaTags;
            }
        ).toList();
        return dramaTagsEntity;
    }


    private static List<DramaCategories> buildDramaCategoryEntity(DramaPublishVo dramaPublishVo, Long dramaId) {
        List<Long> categories = dramaPublishVo.getCategories();
        List<DramaCategories> list = categories.stream().map(
            val -> {
                DramaCategories dramaCategories = new DramaCategories();
                dramaCategories.setDramaId(dramaId);
                dramaCategories.setCategoryId(val);
                dramaCategories.setIsPrimary(0);
                dramaCategories.setCreateTime(new Date());
                return dramaCategories;
            }
        ).toList();
        return list;
    }

    private static Dramas buildDramaEntity(DramaPublishVo dramaPublishVo) {
        Dramas dramasEntity = new Dramas();
        DramasVo drama = dramaPublishVo.getDrama();
        //属性对拷
        BeanUtils.copyProperties(drama, dramasEntity);
        dramasEntity.setFollowCount(0L);
        dramasEntity.setLikeCount(0L);
        dramasEntity.setCommentCount(0L);
        dramasEntity.setPlayCount(0L);
        dramasEntity.setCreateTime(new Date());
        dramasEntity.setUpdateTime(new Date());
        return dramasEntity;
    }
}

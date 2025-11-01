package com.cy.kcat.content.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.cy.kcat.content.domain.bo.EpisodesBo;
import com.cy.kcat.content.domain.vo.EpisodesVo;
import com.cy.kcat.content.domain.Episodes;
import com.cy.kcat.content.mapper.EpisodesMapper;
import com.cy.kcat.content.service.IEpisodesService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 剧集管理Service业务层处理
 *
 * @author Ocean_Yang
 * @date 2025-11-01
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EpisodesServiceImpl implements IEpisodesService {

    private final EpisodesMapper baseMapper;

    /**
     * 查询剧集管理
     *
     * @param episodeId 主键
     * @return 剧集管理
     */
    @Override
    public EpisodesVo queryById(Long episodeId){
        return baseMapper.selectVoById(episodeId);
    }

    /**
     * 分页查询剧集管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 剧集管理分页列表
     */
    @Override
    public TableDataInfo<EpisodesVo> queryPageList(EpisodesBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<Episodes> lqw = buildQueryWrapper(bo);
        Page<EpisodesVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的剧集管理列表
     *
     * @param bo 查询条件
     * @return 剧集管理列表
     */
    @Override
    public List<EpisodesVo> queryList(EpisodesBo bo) {
        LambdaQueryWrapper<Episodes> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Episodes> buildQueryWrapper(EpisodesBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Episodes> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(Episodes::getEpisodeId);
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), Episodes::getTitle, bo.getTitle());
        return lqw;
    }

    /**
     * 新增剧集管理
     *
     * @param bo 剧集管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(EpisodesBo bo) {
        Episodes add = MapstructUtils.convert(bo, Episodes.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setEpisodeId(add.getEpisodeId());
        }
        return flag;
    }

    /**
     * 修改剧集管理
     *
     * @param bo 剧集管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(EpisodesBo bo) {
        Episodes update = MapstructUtils.convert(bo, Episodes.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Episodes entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除剧集管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}

package com.juliy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juliy.entity.Talk;
import com.juliy.model.dto.ConditionDTO;
import com.juliy.model.dto.TalkDTO;
import com.juliy.model.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 说说服务接口
 * @author juliy
 * @date 2023/5/23 11:43
 */
public interface TalkService extends IService<Talk> {

    /**
     * 查看后台说说列表
     * @param condition 条件
     * @return {@link PageResult<TalkAdminVO>} 说说列表
     */
    PageResult<TalkAdminVO> listTalksAdmin(ConditionDTO condition);

    /**
     * 查看首页说说
     * @return 首页说说
     */
    List<TalkHomeVO> listTalksHome();

    /**
     * 查看说说列表
     * @return 说说列表
     */
    PageResult<TalkVO> listTalks();

    /**
     * 添加说说
     * @param talk 说说
     */
    void addTalk(TalkDTO talk);

    /**
     * 删除说说
     * @param talkId 说说id
     */
    void deleteTalk(Integer talkId);

    /**
     * 修改说说
     * @param talk 说说
     */
    void updateTalk(TalkDTO talk);

    /**
     * 编辑说说
     * @param talkId 说说id
     * @return 说说
     */
    TalkAdminInfoVO editTalk(Integer talkId);

    /**
     * 查看说说
     * @param talkId 说说id
     * @return 说说
     */
    TalkVO getTalkById(Integer talkId);

    /**
     * 上传说说图片
     * @param file 文件
     * @return 说说图片地址
     */
    String saveTalkImage(MultipartFile file);

}
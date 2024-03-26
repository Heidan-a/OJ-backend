package com.jinlong.OJ.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinlong.OJ.model.dto.question.QuestionQueryRequest;
import com.jinlong.OJ.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.jinlong.OJ.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.jinlong.OJ.model.entity.Question;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinlong.OJ.model.entity.User;
import com.jinlong.OJ.model.vo.QuestionSubmitVO;
import com.jinlong.OJ.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author yang2
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2023-11-17 21:17:23
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 提交问题
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit,User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}

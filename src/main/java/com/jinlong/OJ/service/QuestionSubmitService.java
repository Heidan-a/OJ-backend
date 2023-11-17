package com.jinlong.OJ.service;

import com.jinlong.OJ.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jinlong.OJ.model.entity.User;

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


}

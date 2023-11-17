package com.jinlong.OJ.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinlong.OJ.common.ErrorCode;
import com.jinlong.OJ.exception.BusinessException;
import com.jinlong.OJ.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.jinlong.OJ.model.entity.Question;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.jinlong.OJ.model.entity.User;
import com.jinlong.OJ.model.enums.QuestionStatesEnum;
import com.jinlong.OJ.model.enums.QuestionSubmitLanguageEnum;
import com.jinlong.OJ.service.QuestionService;
import com.jinlong.OJ.service.QuestionSubmitService;
import com.jinlong.OJ.service.QuestionSubmitService;
import com.jinlong.OJ.mapper.QuestionSubmitMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author yang2
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2023-11-17 21:17:23
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{
    @Resource
    QuestionService questionService;
    /**
     * 提交问题
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        Long questionId = questionSubmitAddRequest.getQuestionId();
        String language = questionSubmitAddRequest.getLanguage();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if(languageEnum == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"编程语言错误");
        }
        if (question  == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已点赞
        long userId = loginUser.getId();
        // 每个用户串行点赞
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmit.getCode());
        questionSubmit.setLanguage(questionSubmit.getLanguage());
        questionSubmit.setStates(QuestionStatesEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if(!save){
            throw new  BusinessException(ErrorCode.SYSTEM_ERROR,"数据插入失败");
        }
        return questionSubmit.getId();
    }


}





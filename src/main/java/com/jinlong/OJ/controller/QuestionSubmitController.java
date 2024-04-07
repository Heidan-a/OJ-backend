package com.jinlong.OJ.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinlong.OJ.annotation.AuthCheck;
import com.jinlong.OJ.common.BaseResponse;
import com.jinlong.OJ.common.ErrorCode;
import com.jinlong.OJ.common.ResultUtils;
import com.jinlong.OJ.constant.UserConstant;
import com.jinlong.OJ.exception.BusinessException;
import com.jinlong.OJ.model.dto.question.QuestionQueryRequest;
import com.jinlong.OJ.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.jinlong.OJ.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.jinlong.OJ.model.entity.Question;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.jinlong.OJ.model.entity.User;
import com.jinlong.OJ.model.vo.QuestionSubmitVO;
import com.jinlong.OJ.service.QuestionSubmitService;
import com.jinlong.OJ.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子点赞接口
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return 提交id
     */
    @PostMapping("/")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }


    /**
     * 分页获取题目提交列表（仅管理员能查看全部，用户自己能查看自己的代码和答案）
     *
     * @param questionSubmitQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

}

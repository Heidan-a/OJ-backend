package com.jinlong.OJ.judge;

import java.util.Date;

import cn.hutool.json.JSONUtil;
import com.jinlong.OJ.common.ErrorCode;
import com.jinlong.OJ.exception.BusinessException;
import com.jinlong.OJ.judge.codeSandBox.CodeSandBox;
import com.jinlong.OJ.judge.codeSandBox.CodeSandBoxFactory;
import com.jinlong.OJ.judge.codeSandBox.CodeSandBoxProxy;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jinlong.OJ.judge.strategy.DefaultJudgeStrategy;
import com.jinlong.OJ.judge.strategy.JudgeContext;
import com.jinlong.OJ.model.dto.question.JudgeCase;
import com.jinlong.OJ.model.dto.question.JudgeConfig;
import com.jinlong.OJ.model.dto.questionsubmit.JudgeInfo;
import com.jinlong.OJ.model.entity.Question;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.jinlong.OJ.model.enums.JudgeInfoMessageEnum;
import com.jinlong.OJ.model.enums.QuestionSubmitLanguageEnum;
import com.jinlong.OJ.model.enums.QuestionSubmitStatusEnum;
import com.jinlong.OJ.model.vo.QuestionSubmitVO;
import com.jinlong.OJ.service.QuestionService;
import com.jinlong.OJ.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${codesandbox.type}")
    String type;

    @Resource
    QuestionService questionService;
    @Resource
    QuestionSubmitService questionSubmitService;
    @Resource
    JudgeManager judgeManager;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1.传入id，得到题目以及提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "请求数据不存在");
        }
        long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "请求数据不存在");
        }
        //2.如果题目提交状态为判题中，就不执行
        if (questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.RUNNING.getValue()))
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        //3.更改判题的状态为判题中，防止重复判题
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "系统错误");
        }
        //4.调用沙箱执行代码
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        codeSandBox = new CodeSandBoxProxy(codeSandBox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> list = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputlist = list.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .language(language)
                .code(code)
                .inputList(inputlist)
                .build();
        ExecuteCodeResponse response = codeSandBox.executeCode(executeCodeRequest);
        List<String> outputlist = response.getOutputList();
        //5.根据沙箱的执行结果，设置题目的判题状态和信息
        //判题逻辑
        //输入输出数量是否一直
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(response.getJudgeInfo());
        judgeContext.setInputlist(inputlist);
        judgeContext.setOutputlist(outputlist);
        judgeContext.setJudgeCaselist(list);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        //将判题信息转化为字符串
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "系统错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionSubmitId);
        return questionSubmitResult;

    }
}

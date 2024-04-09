package com.jinlong.OJ.judge.codeSandBox.impl;
import java.util.List;
import com.jinlong.OJ.model.dto.questionsubmit.JudgeInfo;

import com.jinlong.OJ.judge.codeSandBox.CodeSandBox;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jinlong.OJ.model.enums.JudgeInfoMessageEnum;
import com.jinlong.OJ.model.enums.QuestionSubmitStatusEnum;

public class exampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse response = new ExecuteCodeResponse();
        response.setOutputList(inputList);
        response.setMessage("测试成功");
        response.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        response.setJudgeInfo(judgeInfo);
        return response;
    }
}

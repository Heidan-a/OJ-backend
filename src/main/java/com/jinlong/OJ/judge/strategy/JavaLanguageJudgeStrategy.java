package com.jinlong.OJ.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.jinlong.OJ.model.dto.question.JudgeCase;
import com.jinlong.OJ.model.dto.question.JudgeConfig;
import com.jinlong.OJ.model.dto.questionsubmit.JudgeInfo;
import com.jinlong.OJ.model.entity.Question;
import com.jinlong.OJ.model.enums.JudgeInfoMessageEnum;

import java.util.List;

/**
 * 判题策略
 */
public class JavaLanguageJudgeStrategy implements JudgeStrategy {
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = judgeContext.getJudgeInfo();
        List<String> inputlist = judgeContext.getInputlist();
        List<String> outputlist = judgeContext.getOutputlist();
        List<JudgeCase> judgeCaselist = judgeContext.getJudgeCaselist();
        Question question = judgeContext.getQuestion();
        JudgeInfoMessageEnum messageEnum = JudgeInfoMessageEnum.ACCEPTED;

        //得到判题信息相应
        //判题得到的数据
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        //判题配置
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        JudgeInfo judgeInfoResponse = new JudgeInfo();
        judgeInfoResponse.setMemory(memory);
        judgeInfoResponse.setTime(time);
        judgeInfoResponse.setMessage(messageEnum.getValue());

        if (outputlist.size() != inputlist.size()) {
            messageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            judgeInfoResponse.setMessage(messageEnum.getValue());
            return judgeInfoResponse;
        }
        //依次判断值是否一致
        for (int i = 0; i < outputlist.size(); i++) {
            JudgeCase judgeCase = judgeCaselist.get(i);
            if (judgeCase.getOutput().equals(outputlist.get(i))) {
                messageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                judgeInfoResponse.setMessage(messageEnum.getValue());
                return judgeInfoResponse;
            }
        }
        long needMemoryLimit = judgeConfig.getMemoryLimit();
        long needTimeLimit = judgeConfig.getTimeLimit();
        long JAVA_PROGRAM_TIME_COST = 10000L;
        //判断是否超出
        if (memory > needMemoryLimit) {
            messageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(messageEnum.getValue());
            return judgeInfoResponse;
        }
        if (time - JAVA_PROGRAM_TIME_COST > needTimeLimit) {
            messageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            judgeInfoResponse.setMessage(messageEnum.getValue());
            return judgeInfoResponse;
        }
        judgeInfoResponse.setMessage(messageEnum.getValue());
        return judgeInfoResponse;
    }
}

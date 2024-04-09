package com.jinlong.OJ.judge;

import com.jinlong.OJ.judge.strategy.DefaultJudgeStrategy;
import com.jinlong.OJ.judge.strategy.JavaLanguageJudgeStrategy;
import com.jinlong.OJ.judge.strategy.JudgeContext;
import com.jinlong.OJ.judge.strategy.JudgeStrategy;
import com.jinlong.OJ.model.dto.questionsubmit.JudgeInfo;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

@Service
public class JudgeManager {
    JudgeInfo doJudge(JudgeContext judgeContext){
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if("java".equals(language)){
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}

package com.jinlong.OJ.judge.strategy;

import com.jinlong.OJ.model.dto.question.JudgeCase;
import com.jinlong.OJ.model.dto.questionsubmit.JudgeInfo;
import com.jinlong.OJ.model.entity.Question;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文，用于传递策略参数
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputlist;

    private List<String> outputlist;

    private List<JudgeCase> JudgeCaselist;

    private Question question;
    private QuestionSubmit questionSubmit;
}

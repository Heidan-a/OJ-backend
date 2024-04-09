package com.jinlong.OJ.judge;

import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeResponse;
import com.jinlong.OJ.model.entity.QuestionSubmit;
import com.jinlong.OJ.model.vo.QuestionSubmitVO;

/**
 * 判题服务
 */
public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}

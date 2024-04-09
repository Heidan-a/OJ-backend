package com.jinlong.OJ.judge.codeSandBox;

import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeResponse;

public interface CodeSandBox {
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}

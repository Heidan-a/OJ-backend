package com.jinlong.OJ.judge.codeSandBox.impl;

import com.jinlong.OJ.judge.codeSandBox.CodeSandBox;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeRequest;
import com.jinlong.OJ.judge.codeSandBox.model.ExecuteCodeResponse;

public class remoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return null;
    }
}

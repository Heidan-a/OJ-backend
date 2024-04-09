package com.jinlong.OJ.judge.codeSandBox;

import com.jinlong.OJ.judge.codeSandBox.impl.exampleCodeSandBox;
import com.jinlong.OJ.judge.codeSandBox.impl.remoteCodeSandBox;
import com.jinlong.OJ.judge.codeSandBox.impl.thirdCodeSandBox;

public class CodeSandBoxFactory {
    public static CodeSandBox newInstance(String type){
        switch (type){
            case "example":
                return new exampleCodeSandBox();
            case "third":
                return new thirdCodeSandBox();
            case "remote":
                return new remoteCodeSandBox();
            default:
                return new exampleCodeSandBox();
        }
    }
}

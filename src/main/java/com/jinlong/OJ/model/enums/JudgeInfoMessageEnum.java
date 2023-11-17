package com.jinlong.OJ.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传业务类型枚举
 */
public enum JudgeInfoMessageEnum {


    ACCEPTED("accepted", "java"),
    WRONG_ANSWER("Wrong answer", "Wrong answer"),
    COMPILE_ERROR("Compile error", "编译错误"),
    MEMORY_LIMIT_EXCEEDED("Memory limit exceeded", "内存溢出"),
    TIME_LIMIT_EXCEEDED("Time limit exceeded", "时间溢出"),
    PRESENTATION_ERROR("Presentation error", "表示错误"),
    WAITING("Waiting", "等待中"),
    OUTPUT_LIMIT_EXCEEDED("Output limit exceeded", "输出溢出"),
    DANGEROUS_OPERATION("Dangerous operation", "危险操作"),
    RUNTIME_ERROR("Runtime error", "运行错误"),
    SYSTEM_ERROR("System Error", "系统错误");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}

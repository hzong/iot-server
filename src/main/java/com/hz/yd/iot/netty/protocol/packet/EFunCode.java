package com.hz.yd.iot.netty.protocol.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname EFunCode
 * @Description TODO
 * @Date 2021/12/1 21:15
 * @Created by hzong
 */
public enum EFunCode {
    FN_35("35", "35"),
    FN_35_RES("35_RES", "35_RES"),
    FN_A1("E1", "E1"),
    FN_E1("A1", "A1"),
    NONE("0", "NONE");
    private String code;//功能代码
    private String describe;//描述

    EFunCode(
            String code, String
            describe) {
        this.code = code;
        this.describe = describe;
    }

    public String getCode
            () {
        return code;
    }

    public String getDescribe
            () {
        return describe;
    }

    private static final Map<String, EFunCode> hash = new HashMap<String, EFunCode>(EFunCode.values().length);

    static {
        for (EFunCode templateType : EFunCode.values()) {
            hash.put(templateType.getCode(), templateType);
        }
    }

    /**
     * 功能描述: 转换代码<br>
     */
    public static EFunCode parseCode
    (String
             code, EFunCode
             def) {
        EFunCode val = hash.get(code);
        if (val != null) {
            return val;
        }
        return def;
    }

    /**
     * 功能描述: 转换代码<br>
     */
    public static EFunCode parseCode
    (String
             code) {
        return parseCode(code, EFunCode.NONE);
    }

    public static boolean codeEq
            (EFunCode
                     eqCode, EFunCode... codes) {
        boolean isEq = true;
        for (EFunCode code : codes) {
            if (eqCode == code && code != null) {
                isEq &= true;
            } else {
                isEq = false;
                break;
            }

        }
        return isEq;
    }


    public static boolean codeIn
            (EFunCode... codes) {
        boolean result = true;
        EFunCode temp;
        for (EFunCode code : codes) {
            temp = parseCode(code.getCode());
            if (temp == EFunCode.NONE) {
                result = false;
                break;
            }
            result &= true;
        }
        return result;
    }
}

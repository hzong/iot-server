package com.hz.yd.iot.service;

import com.hz.yd.iot.netty.protocol.packet.EFunCode;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname CMDFunManager
 * @Description TODO
 * @Date 2021/12/1 22:35
 * @Created by hzong
 */
public class CMDFunManager {
    public static final CMDFunManager INST = new CMDFunManager();


    private static final Map<EFunCode, ICMDService> cmdService = load();

    private static Map<EFunCode, ICMDService> load() {
        Map<EFunCode, ICMDService> cmdService = new ConcurrentHashMap<EFunCode, ICMDService>();
        cmdService.put(EFunCode.FN_35, new CMDFun35Service());
        cmdService.put(EFunCode.FN_35_RES, new CMDFun35ResService());
        cmdService.put(EFunCode.FN_A1, new CMDFunA1Service());
        cmdService.put(EFunCode.FN_E1, new CMDFunE1Service());
        return cmdService;
    }

    public void handle(EFunCode funCode, Object obj) {
        ICMDService icmdService = cmdService.get(funCode);
        if (icmdService == null) {
            return;
        }
        icmdService.handle(obj);
    }

}

package com.nua.faw.bean;


import com.xuhao.didi.core.iocore.interfaces.ISendable;

import java.nio.charset.Charset;

/**
 * Created by Tony on 2017/10/24.
 */

public class MsgDataBean implements ISendable {
    private String content = "";

    public MsgDataBean(String content) {
        this.content = content;
    }

    @Override
    public byte[] parse() {
        byte[] body = content.getBytes(Charset.defaultCharset());
        return body;
    }
}

package com.uphyca.robots.core;

import java.io.InputStream;

/**
 * 
 * RS303MR/RS304MD 取扱説明書 Ver1.13 より.
 *
 * Flagsでサーボにリターンパケットの要求をした時に、サーボから送られるパケットです。
 * 
 * <dl>
 * 
 * <dt>パケット構成</dt>
 * <dd>[Header][ID][Flag][Address][Length][Count][Data][Sum]</dd>
 *
 * </dl>
 *
 * TODO 続き書く
 */
public class ReturnPacket extends Packet {

    public static ReturnPacket map(InputStream in) {

        return null;
    }

    ReturnPacket() {
    }
}

/*
 * Copyright (C) 2012 uPhyca Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package com.uphyca.robots.core;

import static com.uphyca.robots.core.PacketUtils.a;
import static com.uphyca.robots.core.PacketUtils.updateChecksum;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * RS303MR/RS304MD 取扱説明書 Ver1.13 より.
 * 
 * サーボにコマンドを送ったり、サーボからデータを受信したりする際のデータのかたまりを『パケット』と呼びます。
 * パケットは次の三種類に分類され、それぞれに異なる書式になっています。
 * <dl>
 * 
 * <dt>ショートパケット</dt>
 * <dd>一つのサーボに対して、メモリーマップのデータを送信するときに使用するパケットです。</dd>
 * 
 * <dt>ロングパケット</dt>
 * <dd>複数のサーボに対して、メモリーマップのデータを一度に送信できるパケットです。</dd>
 * 
 * <dt>リターンパケット</dt>
 * <dd>サーボにリターンパケットの要求をした時にサーボから送られてくるパケットです。</dd>
 * </dl>
 */
public abstract class Packet implements Sendable {

    protected static final int SUM_OFFSET = /*header*/2 + /*ID*/1 + /*Flag*/1 + /*Address*/1 + /*Length*/1 + /*Count*/1;

    private byte[] header;
    private byte id;
    private byte flag;
    private byte address;
    private byte length;
    private byte count;
    //private byte[] data;
    private List<Sendable> data;

    private boolean lengthSet;

    Packet() {
        data = new ArrayList<Sendable>();
    }

    /**
     * @return the header
     */
    public byte[] header() {
        return header;
    }

    /**
     * @return the id
     */
    public byte id() {
        return id;
    }

    /**
     * @return the flag
     */
    public byte flag() {
        return flag;
    }

    /**
     * @return the address
     */
    public byte address() {
        return address;
    }

    /**
     * @return the length
     */
    public byte length() {
        return length;
    }

    /**
     * @return the count
     */
    public byte count() {
        return count;
    }

    /**
     * @return the data
     */
    public List<Sendable> data() {
        return data;
    }

    /**
     * @param header the header to set
     */
    void setHeader(byte[] header) {
        this.header = header;
    }

    /**
     * @param id the id to set
     */
    void setId(byte id) {
        this.id = id;
    }

    /**
     * @param flag the flag to set
     */
    void setFlag(byte flag) {
        this.flag = flag;
    }

    /**
     * @param address the address to set
     */
    void setAddress(byte address) {
        this.address = address;
    }

    /**
     * @param length the length to set
     */
    void setLength(byte length) {
        this.length = length;
    }

    /**
     * @param count the count to set
     */
    void setCount(byte count) {
        this.count = count;
    }

    /**
     * @param data the data to set
     */
    void setData(List<Sendable> data) {
        this.data = data;
    }

    /**
     * @param lengthSet the lengthSet to set
     */
    void setLengthSet(boolean lengthSet) {
        this.lengthSet = lengthSet;
    }

    /**
     * @return
     */
    boolean isLengthSet() {
        return lengthSet;
    }

    public void send(OutputStream out) throws IOException {
        byte[] rawData = Bytes.of(data);
        int dataSize = rawData.length;
        int end = SUM_OFFSET + dataSize + 1;
        byte[] b = new byte[end];
        a(b, 0, header);
        a(b, 2, id);
        a(b, 3, flag);
        a(b, 4, address);
        // lengthが明示的にセットされた場合は、その値を使う.
        a(b, 5, lengthSet ? length : dataSize);
        a(b, 6, count);
        a(b, 7, rawData);
        updateChecksum(b, 0, end);
        out.write(b, 0, end);
    }
}

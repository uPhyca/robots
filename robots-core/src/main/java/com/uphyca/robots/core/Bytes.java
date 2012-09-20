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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * byte型変数を扱うためのユーティリティメソッドを定義するクラス.
 */
public abstract class Bytes {

    /**
     * byte配列型引数 src の長さを返す.
     * 
     * @param src
     * @return
     */
    public static int length(byte[] src) {
        return src == null ? 0 : src.length;
    }

    /**
     * {@link ByteArrayOutputStream} を作成する.
     * 
     * @return
     */
    public static ByteArrayOutputStream stream() {
        return new ByteArrayOutputStream();
    }

    /**
     * {@link ByteArrayInputStream} を作成する.
     * 
     * @param src
     * @return
     */
    public static ByteArrayInputStream stream(int... src) {
        return new ByteArrayInputStream(Bytes.of(src));
    }

    /**
     * int型引数 i byte型として返す.
     * 
     * @param i
     * @return
     */
    public static final byte of(int i) {
        return (byte) (i & 0xff);
    }

    /**
     * int配列型の引数 src をbyte配列型として返す.
     * 
     * @param src
     * @return
     */
    public static final byte[] of(int... src) {
        byte[] dest = new byte[src.length];
        for (int i = 0, len = src.length; i < len; ++i) {
            dest[i] = of(src[i]);
        }
        return dest;
    }

    /**
     * boolean配列型 src をビットフラグとしてbyte型にして返す.
     * 
     * @param src
     * @return
     */
    public static byte of(boolean... src) {
        byte dest = 0x00;
        for (int i = 0, len = src.length; i < len; ++i) {
            boolean bit = src[i];
            if (bit)
                dest |= (0x01 << (len - i - 1));
        }
        return dest;
    }
}

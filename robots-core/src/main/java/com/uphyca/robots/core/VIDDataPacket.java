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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * RS303MR/RS304MD 取扱説明書 Ver1.13 P.22 より.
 * 
 * ロングパケットの[VID][Data]の組.
 * 
 * <dl>
 * 
 * <dt>
 * VID
 * </dt>
 * <dd>
 * データを送信する個々のサーボのIDを表します。VIDとDataが一組でサーボの数分のデータを送信します。
 * </dd>
 * 
 * <dt>
 * Data
 * </dt>
 * <dd>
 * メモリーマップに書き込むサーボ一つ分のデータです。VIDとDataが一組でサーボの数分のデータを送信します。
 * </dd>
 * 
 * </dl>
 * [VID]
 * 
 *
 */
class VIDDataPacket implements Sendable {

    private final ShortPacket data;

    public static VIDDataPacket data(ShortPacket newData) {
        return new VIDDataPacket(newData);
    }

    private VIDDataPacket(ShortPacket newData) {
        data = newData;
    }

    @Override
    public void send(OutputStream out) throws IOException {
        ByteArrayOutputStream buffer = Bytes.stream();
        buffer.write(new byte[] { data.id() });
        for (Sendable o : data.data()) {
            o.send(buffer);
        }
        out.write(buffer.toByteArray());
    }
}

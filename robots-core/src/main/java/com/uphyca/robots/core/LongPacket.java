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

/**
 * 
 * RS303MR/RS304MD 取扱説明書 Ver1.13 より.
 * 
 * 複数のサーボに対して、メモリーマップのデータを一度に送信できるパケットです。
 * ただし、送信できるメモリーマップのアドレスとデータの長さは、全てのサーボに対して同一となります。
 * 
 * TODO 続き書く
 */
public class LongPacket extends Packet {

    public static final class Builder {

        private final LongPacket packet = new LongPacket();

        private Builder() {
        }

        public LongPacket.Builder address(byte newAddress) {
            packet.setAddress(newAddress);
            return this;
        }

        public LongPacket.Builder address(int newAddress) {
            return address(Bytes.of(newAddress));
        }

        public LongPacket.Builder length(byte newLength) {
            packet.setLength(newLength);
            packet.setLengthSet(true);
            return this;
        }

        public LongPacket.Builder length(int newLength) {
            return length(Bytes.of(newLength));
        }

        public LongPacket.Builder data(ShortPacket... newData) {
            for (ShortPacket o : newData) {
                packet.data()
                      .add(VIDDataPacket.data(o));
                packet.setCount(Bytes.of(packet.count() + 1));
            }
            return this;
        }

        public LongPacket build() {
            return packet;
        }

    }

    private static LongPacket.Builder builder() {
        return new LongPacket.Builder();
    }

    LongPacket() {
        setHeader(Bytes.of(0xFA, 0xAF));
    }

    public static LongPacket.Builder address(byte newAddress) {
        return builder().address(newAddress);
    }

    public static LongPacket.Builder address(int newAddress) {
        return builder().address(newAddress);
    }

    public static LongPacket.Builder length(byte newLength) {
        return builder().length(newLength);
    }

    public static LongPacket.Builder length(int newLength) {
        return builder().length(newLength);
    }

    public static LongPacket.Builder data(ShortPacket... newData) {
        return builder().data(newData);
    }
}

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
import static com.uphyca.robots.core.PacketUtils.calcChecksum;

import java.io.IOException;
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

    private static final byte[] RETURN_PACKET_HEADER = Bytes.of(0xFD, 0xDF);
    private static final byte RETURN_PACKET_HEADER_UPPER = RETURN_PACKET_HEADER[0];
    private static final byte RETURN_PACKET_HEADER_LOWER = RETURN_PACKET_HEADER[1];

    public static final class Builder {
        private final ReturnPacket packet = new ReturnPacket();

        private Builder() {
        }

        public ReturnPacket.Builder header(InputStream newHeader) throws IOException {
            waitForValidHeader(newHeader);
            packet.setHeader(RETURN_PACKET_HEADER);
            return this;
        }

        private void waitForValidHeader(InputStream newHeader) throws IOException {

            byte[] buffer = new byte[1];

            while (true) {
                newHeader.read(buffer);

                if (buffer[0] != RETURN_PACKET_HEADER_UPPER) {
                    continue;
                }
                newHeader.read(buffer);
                if (buffer[0] == RETURN_PACKET_HEADER_LOWER) {
                    break;
                }
            }
        }

        public ReturnPacket.Builder id(InputStream newId) throws IOException {
            byte[] buffer = new byte[1];
            newId.read(buffer);
            packet.setId(buffer[0]);
            return this;
        }

        public ReturnPacket.Builder flag(InputStream newFlag) throws IOException {
            byte[] buffer = new byte[1];
            newFlag.read(buffer);
            packet.setFlag(buffer[0]);
            return this;
        }

        public ReturnPacket.Builder address(InputStream newAddress) throws IOException {
            byte[] buffer = new byte[1];
            newAddress.read(buffer);
            packet.setAddress(buffer[0]);
            return this;
        }

        public ReturnPacket.Builder length(InputStream newLength) throws IOException {
            byte[] buffer = new byte[1];
            newLength.read(buffer);
            packet.setLength(buffer[0]);
            return this;
        }

        public ReturnPacket.Builder count(InputStream newCount) throws IOException {
            byte[] buffer = new byte[1];
            newCount.read(buffer);
            packet.setCount(buffer[0]);
            return this;
        }

        public ReturnPacket.Builder data(InputStream newData) throws IOException {
            byte[] buffer = new byte[packet.length()];
            newData.read(buffer);
            packet.data()
                  .add(RawPacket.data(buffer));
            return this;
        }

        public ReturnPacket.Builder sum(InputStream newSum) throws IOException {
            byte[] buffer = new byte[1];
            newSum.read(buffer);

            byte sumExpected = sum();
            byte sumActual = buffer[0];

            if (sumExpected != sumActual) {
                IOException ioe = new IOException("incorrect checksum");
                ioe.fillInStackTrace();
                throw ioe;
            }

            return this;
        }

        private byte sum() throws IOException {
            byte[] data = new byte[SUM_OFFSET + packet.length()];

            a(data, 0, packet.header());
            a(data, 2, packet.id());
            a(data, 3, packet.flag());
            a(data, 4, packet.address());
            a(data, 5, packet.length());
            a(data, 6, packet.count());
            a(data, 7, Bytes.of(packet.data()));

            byte sum = calcChecksum(data, 0, data.length + 1);
            return sum;
        }

        public ReturnPacket build() {
            return packet;
        }
    }

    private static ReturnPacket.Builder builder() {
        return new ReturnPacket.Builder();
    }

    ReturnPacket() {
    }

    public static ReturnPacket.Builder header(InputStream in) throws IOException {
        return builder().header(in);
    }
}

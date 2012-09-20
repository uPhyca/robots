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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.uphyca.robots.core.Bytes;
import com.uphyca.robots.core.ShortPacket;

public class ShortPacketTest {

    /**
     * パケットが正しく送信されること.
     * 
     * RS303MR/RS304MD 取扱説明書 Ver1.13 P.18 [Sum]の例より.
     */
    @Test
    public void testThatPacketChecksumIsValid() throws IOException {

        // Given
        ShortPacket packet = ShortPacket.header(0xFA, 0xAF)
                                        .id(0x01)
                                        .flag(0x00)
                                        .address(0x1E)
                                        .length(0x02)
                                        .count(0x01)
                                        .data(0x00, 0x00)
                                        .build();

        ByteArrayOutputStream out = Bytes.stream();

        // When
        packet.send(out);

        // Then
        assertThat(out.toByteArray(), is(Bytes.of(0xFA, 0xAF, 0x01, 0x00, 0x1E, 0x02, 0x01, 0x00, 0x00, 0x1C)));
    }

    /**
     * [Len] = 00, [Data] = なしのパケットが正しく送信されること.
     * 
     * RS303MR/RS304MD 取扱説明書 Ver1.13 P.19 - ビット6:フラッシュROMへ書き込みの例より.
     */
    @Test
    public void testThatPackeWithoutDatatSent() throws IOException {

        // Given
        ShortPacket packet = ShortPacket.header(0xFA, 0xAF)
                                        .id(0x01)
                                        .flag(0x40)
                                        .address(0xFF)
                                        .length(0x00)
                                        .count(0x00)
                                        .build();

        ByteArrayOutputStream out = Bytes.stream();

        // When
        packet.send(out);

        // Then
        assertThat(out.toByteArray(), is(Bytes.of(0xFA, 0xAF, 0x01, 0x40, 0xFF, 0x00, 0x00, 0xBE)));
    }

    /**
     * メモリーマップデータ任意アドレス指定のパケットが正しく送信されること.
     * 
     * RS303MR/RS304MD 取扱説明書 Ver1.13 P.21 - (2)メモリーマップデータ任意アドレス指定(専用パケット)の例より.
     */
    @Test
    public void testThatPackeMemoryMapDataOrbitalAddressSent() throws IOException {

        // Given
        ShortPacket packet = ShortPacket.header(0xFA, 0xAF)
                                        .id(0x01)
                                        .flag(true, true, true, true)
                                        .address(0x2A)
                                        .length(0x02)
                                        .count(0x00)
                                        .build();

        ByteArrayOutputStream out = Bytes.stream();

        // When
        packet.send(out);

        // Then
        assertThat(out.toByteArray(), is(Bytes.of(0xFA, 0xAF, 0x01, 0x0F, 0x2A, 0x02, 0x00, 0x26)));
    }

}

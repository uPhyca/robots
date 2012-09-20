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
package com.uphyca.robots.core.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import com.uphyca.robots.core.Bytes;
import com.uphyca.robots.core.ReturnPacket;

public class ReturnPacketTest {

    /**
     * パケットが正しく復元されること.
     * 
     * RS303MR/RS304MD 取扱説明書 Ver1.13 P.39 No.48/No.49 現在負荷(2バイト、Hex表記、Read)の例より.
     */
    @Test
    public void testThatReturnPacketDecoded() throws IOException {

        // Given
        ByteArrayInputStream in = Bytes.stream(0xFD, 0xDF, 0x01, 0x00, 0x2A, 0x12, 0x01, 0x4E, 0xFB, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0xBA, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x32);

        // When
        ReturnPacket packet = ReturnPacket.header(in)
                                          .id(in)
                                          .flag(in)
                                          .address(in)
                                          .length(in)
                                          .count(in)
                                          .data(in)
                                          .sum(in)
                                          .build();

        // Then
        assertThat(packet.header(), is(Bytes.of(0xFD, 0xDF)));
        assertThat(packet.id(), is(Bytes.of(0x01)));
        assertThat(packet.flag(), is(Bytes.of(0x00)));
        assertThat(packet.address(), is(Bytes.of(0x2A)));
        assertThat(packet.length(), is(Bytes.of(0x12)));
        assertThat(packet.count(), is(Bytes.of(0x01)));
        assertThat(Bytes.of(packet.data()), is(Bytes.of(0x4E, 0xFB, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0xBA, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)));
        //assertThat(packet.sum(), is(Bytes.of(0x32)));
    }

    /**
     * パケットが正しく復元されること.
     * 
     * RS303MR/RS304MD 取扱説明書 Ver1.13 P.39 No.48/No.49 現在負荷(2バイト、Hex表記、Read)の例より.
     * 
     * チェックサムが不正な場合、例外を投げること.
     */
    @Test(expected = IOException.class)
    public void testThatThrowsReturnPacketDecodedFail() throws IOException {

        // Given
        byte invalidSum = 0x66;
        ByteArrayInputStream in = Bytes.stream(0xFD, 0xDF, 0x01, 0x00, 0x2A, 0x12, 0x01, 0x4E, 0xFB, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0xBA, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, invalidSum);

        // When
        ReturnPacket packet = ReturnPacket.header(in)
                                          .id(in)
                                          .flag(in)
                                          .address(in)
                                          .length(in)
                                          .count(in)
                                          .data(in)
                                          .sum(in)
                                          .build();

    }

}

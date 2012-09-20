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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.uphyca.robots.core.Bytes;
import com.uphyca.robots.core.LongPacket;
import com.uphyca.robots.core.ShortPacket;

public class LongPacketTest {

    /**
     * パケットが正しく送信されること.
     * 
     * RS303MR/RS304MD 取扱説明書 Ver1.13 P.23 [Sum]の例より.
     */
    @Test
    public void testThatLongPacketSent() throws IOException {

        // Given
        LongPacket underTest = LongPacket.address(0x1E)
                                         .length(0x03)
                                         .data(ShortPacket.id(0x01)
                                                          .data(0x64, 0x00)
                                                          .build())
                                         .data(ShortPacket.id(0x02)
                                                          .data(0x64, 0x00)
                                                          .build())
                                         .data(ShortPacket.id(0x05)
                                                          .data(0xF4, 0x01)
                                                          .build())
                                         .build();

        ByteArrayOutputStream out = Bytes.stream();

        // When
        underTest.send(out);

        // Then
        assertThat(out.toByteArray(), is(Bytes.of(0xFA, 0xAF, 0x00, 0x00, 0x1E, 0x03, 0x03, 0x01, 0x64, 0x00, 0x02, 0x64, 0x00, 0x05, 0xF4, 0x01, 0xED)));
    }

}

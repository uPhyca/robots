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

import org.junit.Test;

import com.uphyca.robots.core.Bytes;

public class BytesTest {

    @Test
    public void testBytesOfBooleans() {
        assertThat(Bytes.of(true), is(Bytes.of(0x01)));
        assertThat(Bytes.of(true, true), is(Bytes.of(0x03)));
        assertThat(Bytes.of(true, true, true), is(Bytes.of(0x07)));
        assertThat(Bytes.of(true, true, true, true), is(Bytes.of(0x0F)));
        assertThat(Bytes.of(true, true, true, true, true), is(Bytes.of(0x1F)));
        assertThat(Bytes.of(true, true, true, true, true, true), is(Bytes.of(0x3F)));
        assertThat(Bytes.of(true, true, true, true, true, true, true), is(Bytes.of(0x7F)));
        assertThat(Bytes.of(true, true, true, true, true, true, true, true), is(Bytes.of(0xFF)));
    }
}

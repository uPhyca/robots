/*
 * Copyright (C) 2012 uPhyca Inc.
 * 
 * Base on previous work by
 * Copyright (C) 2011 BRILLIANTSERVICE Co., Ltd. & RT Corporation
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
 */
public abstract class PacketUtils {

    public static void updateChecksum(byte[] buffer,
                                      int start,
                                      int end) {
        byte sum = calcChecksum(buffer, start, end);
        buffer[end - 1] = sum;
    }

    public static byte calcChecksum(byte[] buffer,
                                    int start,
                                    int end) {
        byte c = 0;
        for (int i = start + 2, num = end - 1; i < num; ++i)
            c ^= buffer[i];
        return c;
    }

    public static void a(byte[] buffer,
                         int offset,
                         int... values) {
        if (buffer == null)
            buffer = new byte[values.length];
        for (int i = 0, len = values.length; i < len; ++i)
            buffer[offset + i] = (byte) (0xff & values[i]);
    }

    public static void a(byte[] buffer,
                         int offset,
                         byte... values) {
        if (values == null)
            return;
        for (int i = 0, len = values.length; i < len; ++i)
            buffer[offset + i] = values[i];
    }

    public static void a(byte[] buffer,
                         int offset,
                         byte value) {
        if (buffer == null)
            return;
        buffer[offset] = value;
    }

    public static void a(byte[] buffer,
                         int offset,
                         int value) {
        if (buffer == null)
            return;
        buffer[offset] = (byte) (0xff & value);
    }

    public static String dump(byte[] buffer) {
        StringBuilder b = new StringBuilder(buffer.length * 3);
        for (int i = 0, num = buffer.length; i < num; ++i) {
            if (i > 0)
                b.append(" ");
            b.append(String.format("%02X", buffer[i]));
        }
        return b.toString();
    }

}

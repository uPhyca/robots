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
package com.uphyca.robots.example;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.uphyca.robots.core.Bytes;
import com.uphyca.robots.core.PacketUtils;
import com.uphyca.robots.core.RS485;
import com.uphyca.robots.core.ReturnPacket;
import com.uphyca.robots.core.ShortPacket;

public class RS485Example {

    private static final String DEFALULT_PORT_NAME = "/dev/tty.usbserial-000013FA";
    private static final int TIMEOUT = 0;
    private static final long WAIT_MILLIS = 500;

    public static void main(String[] args) throws Exception {

        //Prepare COM port.
        String portName = (args.length == 0) ? DEFALULT_PORT_NAME : args[0];
        SerialPort port = prepareComPort(portName);

        InputStream in = port.getInputStream();
        OutputStream out = port.getOutputStream();

        try {
            ReturnPacket presentPosition = null;

            // Set torque on.
            torque(true, out);

            // Move to 90 degrees
            moveTo(Bytes.of(0x84, 0x03), out);

            // Obtain present position
            requestPresentPosition(out);
            presentPosition = obtainPresentPosition(in);
            System.out.println(PacketUtils.dump(Bytes.of(presentPosition.data())));

            // Move to 0 degrees
            moveTo(Bytes.of(0x00, 0x00), out);

            // Obtain present position
            requestPresentPosition(out);
            presentPosition = obtainPresentPosition(in);
            System.out.println(PacketUtils.dump(Bytes.of(presentPosition.data())));

            // Set torque off.
            torque(false, out);

        } finally {
            out.close();
            in.close();
            port.close();
        }
    }

    private static SerialPort prepareComPort(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException {
        CommPortIdentifier comID = CommPortIdentifier.getPortIdentifier(portName);
        CommPort commPort = comID.open("robots-example", TIMEOUT);

        SerialPort port = (SerialPort) commPort;
        port.setSerialPortParams(115200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

        return port;
    }

    private static ReturnPacket obtainPresentPosition(InputStream in) throws IOException {
        ReturnPacket packet = ReturnPacket.header(in)
                                          .id(in)
                                          .flag(in)
                                          .address(in)
                                          .length(in)
                                          .count(in)
                                          .data(in)
                                          .sum(in)
                                          .build();
        return packet;
    }

    private static void requestPresentPosition(OutputStream out) throws IOException, InterruptedException {
        RS485 packet = RS485.data(ShortPacket.id(0x01)
                                             .flag(0x0F)
                                             .address(0x2A)
                                             .length(0x02)
                                             .count(0x00)
                                             .build())
                            .footer(0x0A)
                            .build();
        packet.send(out);
        Thread.sleep(WAIT_MILLIS);
    }

    private static void torque(boolean on,
                               OutputStream out) throws IOException, InterruptedException {
        RS485 packet = RS485.data(ShortPacket.id(0x01)
                                             .flag(0x00)
                                             .address(0x24)
                                             .count(0x01)
                                             .data(on ? 0x01 : 0x00)
                                             .build())
                            .build();
        packet.send(out);
        Thread.sleep(WAIT_MILLIS);
    }

    private static void moveTo(byte[] degrees,
                               OutputStream out) throws IOException, InterruptedException {
        RS485 packet = RS485.data(ShortPacket.id(0x01)
                                             .flag(0x00)
                                             .address(0x1E)
                                             .count(0x01)
                                             .data(degrees)
                                             .data(0xF4, 0x01)
                                             .build())
                            .build();
        packet.send(out);
        Thread.sleep(5000 + WAIT_MILLIS);
    }
}

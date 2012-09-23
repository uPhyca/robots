robots
======

Fluent style APIs for Controlling servo motors


Tested servo motors are:
=====
-FUTABA RS30MD http://www.futaba.co.jp/robot/command_type_servos/rs304md.html


Example:
=====

Torque On/Off
===

    private static void torque(boolean on,
                               OutputStream out) throws IOException {
        RS485 packet = RS485.data(ShortPacket.id(0x01)
                                             .flag(0x00)
                                             .address(0x24)
                                             .count(0x01)
                                             .data(on ? 0x01 : 0x00)
                                             .build())
                            .build();
        packet.send(out);
    }

Send Long packet
===
        LongPacket packet = LongPacket.address(0x1E)
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

        packet.send(out);

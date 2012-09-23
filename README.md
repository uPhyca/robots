robots
======

Fluent style APIs for Controlling servo motors


Tested servo motors are:
=====
-FUTABA RS30MD http://www.futaba.co.jp/robot/command_type_servos/rs304md.html


Example:
=====
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

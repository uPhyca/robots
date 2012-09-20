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

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * RS303MR/RS304MD 取扱説明書 Ver1.13 より.
 * 
 * サーボにコマンドを送ったり、サーボからデータを受信したりする際のデータのかたまりを『パケット』と呼びます。
 * パケットは次の三種類に分類され、それぞれに異なる書式になっています。
 * <dl>
 * 
 * <dt>ショートパケット</dt>
 * <dd>一つのサーボに対して、メモリーマップのデータを送信するときに使用するパケットです。</dd>
 * 
 * <dt>ロングパケット</dt>
 * <dd>複数のサーボに対して、メモリーマップのデータを一度に送信できるパケットです。</dd>
 * 
 * <dt>リターンパケット</dt>
 * <dd>サーボにリターンパケットの要求をした時にサーボから送られてくるパケットです。</dd>
 * </dl>
 */
public interface Packet {

    void send(OutputStream out) throws IOException;
}

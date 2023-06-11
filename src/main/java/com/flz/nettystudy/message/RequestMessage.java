package com.flz.nettystudy.message;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestMessage extends BaseMessage {

    public void decode(ByteBuf byteBuf) {
        int version = byteBuf.readInt();
        int opCode = byteBuf.readInt();
        String streamId = (String) byteBuf.readCharSequence(Integer.MAX_VALUE, StandardCharsets.UTF_8);
//        byteBuf.readBytes()
    }
}

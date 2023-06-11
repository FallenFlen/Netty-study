package com.flz.nettystudy.message;

import com.flz.nettystudy.utils.JsonUtils;
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
public class ResponseMessage extends BaseMessage {

    public void encode(ByteBuf byteBuf) {
        byteBuf.writeInt(this.getHeader().getVersion());
        byteBuf.writeInt(this.getHeader().getOpCode());
        byteBuf.writeCharSequence(this.getHeader().getStreamId(), StandardCharsets.UTF_8);
        byteBuf.writeBytes(JsonUtils.silentMarshal(this.getBody()).getBytes());
    }
}

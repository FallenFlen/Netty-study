package com.flz.nettystudy.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseMessage {
    private Header header;
    private Object body;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Header {
        private Integer version;
        private Integer opCode;
        private String streamId;
    }
}

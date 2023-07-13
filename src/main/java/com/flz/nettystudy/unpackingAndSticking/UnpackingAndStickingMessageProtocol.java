package com.flz.nettystudy.unpackingAndSticking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
public class UnpackingAndStickingMessageProtocol {
    private int length;
    private byte[] content;
}

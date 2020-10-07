package com.bootstrap.startup.components.encryption;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncryptObject {
    public String iv;
    public String value;
    public String mac;
}

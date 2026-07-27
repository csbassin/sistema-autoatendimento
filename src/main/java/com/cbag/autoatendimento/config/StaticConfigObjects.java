package com.cbag.autoatendimento.config;

import java.util.HashMap;

public class StaticConfigObjects {
    public static final String userHomeDir = System.getProperty("user.home");
    public static final HashMap<String, String> printerNames;
    static {
        printerNames = new HashMap<>();
        printerNames.put("TOTEM", "THERMAL");
    }

}

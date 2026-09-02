package com.cbag.autoatendimento.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class StaticConfigObjects {
    public static final String userHomeDir = System.getProperty("user.home");
    public static String os;
    public static String computerName; // vou usar para caso precise imprimir coisas no Windows
    public static boolean atendimentoLiberado = true;
    public static final HashMap<String, String> printerNames;
    static {
        os = System.getProperty("os.name");

        try {
            computerName = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Hostname cannot be resolved");
        }

        printerNames = new HashMap<>();
        printerNames.put("TOTEM", "THERMAL");
    }

}

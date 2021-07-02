package me.doublenico.playerleveling.utils;

import me.doublenico.hypeapi.util.VersionUtils;

public class LegacyVersion {

    public static boolean isLegacyVersion() {

        switch (VersionUtils.getNMSVersion()){
            case "1_17_R1":
            case "1_16_R3":
            case "1_16_R2":
            case "1_16_R1":
            case "1_15_R1":
            case "1_14_R1":
            case "1_13_R2":
            case "1_13_R1":
            case "1_12_R1":
            case "1_11_R1":
            case "1_10_R1":
            case "1_9_R2":
            case "1_9_R1":
                return false;
            case "1_8_R3":
            case "1_8_R2":
            case "1_8_R1":
                return true;
        }

        return true;
    }



}

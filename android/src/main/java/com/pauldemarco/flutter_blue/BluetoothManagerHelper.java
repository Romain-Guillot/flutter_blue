package com.pauldemarco.flutter_blue;

import java.nio.ByteBuffer;
import java.util.UUID;

import android.bluetooth.le.ScanFilter;

public class BluetoothManagerHelper {
    public static byte[] getIdAsByte(UUID uuid) {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }

    public static ScanFilter getFilterFromUUID(String scanUUID){
        byte[] uuid = getIdAsByte(UUID.fromString(scanUUID));
        byte[] data = new byte[20];
        byte[] mask = new byte[20];
        data[0] = (byte) 0x02;
        data[1] = (byte) 0x15;
        mask[0] = (byte) 0xff;
        mask[1] = (byte) 0xff;
        for (int i = 2; i <= 17; i++){
            data[i] = uuid[i-2];
            mask[i] = (byte) 0xff;
        }
        ScanFilter filter = new ScanFilter.Builder()
                .setManufacturerData(0x004c, data, mask)
                .build();
        return filter;
    }
}
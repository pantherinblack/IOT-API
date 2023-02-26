package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Device

class DeviceHandler {
    private var deviceList : List<Device> =  emptyList()

    fun getDeviceList() : List<Device> {
        return deviceList;
    }

    fun getDeviceByUUID(uuid:String) : Device {
        return Device()
    }
}
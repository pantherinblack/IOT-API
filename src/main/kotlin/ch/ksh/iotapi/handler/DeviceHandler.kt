package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Device
import java.sql.ResultSet

class DeviceHandler private constructor() {
    private var deviceList: ArrayList<Device> = ArrayList()

    companion object {
        private var instance: DeviceHandler? = null

        fun getInstance(): DeviceHandler {
            if (instance == null)
                instance = DeviceHandler()
            return instance!!
        }
    }

    init {
        loadDeviceList()
    }

    fun loadDeviceList() {
        val rs: ResultSet = SQLHandler.getResultSet("SELECT * FROM Device")
        deviceList = SQLHandler.resultSetToArrayList(rs, Device::class.java)
    }

    fun getDeviceList(): ArrayList<Device> {
        return deviceList
    }

    fun getDeviceByUUID(uuid: String?): Device? {
        if (uuid == null)
            return null
        deviceList.forEach {
            if (it.deviceUUID == uuid)
                return it
        }
        return null
    }

    fun insertDevice(device: Device) {
        val list = mapOf<Int, Any?>(
            1 to device.deviceUUID,
            2 to device.deviceName,
            3 to device.latitude,
            4 to device.longitude
        )
        SQLHandler.executeStatement(
            "INSERT INTO Device (deviceUUID, deviceName, latitude, longitude) VALUES (?, ?, ?, ?)",
            list
        )
        deviceList.add(device)
    }

    fun updateDevice(
        uuid: String,
        deviceUUID: String? = null,
        deviceName: String? = null,
        latitude: Float? = null,
        longitude: Float? = null
    ) {
        val device = getDeviceByUUID(uuid)!!
        if (deviceUUID != null)
            device.deviceUUID = deviceUUID
        if (deviceName != null)
            device.deviceName = deviceName
        if (latitude != null)
            device.latitude = latitude
        if (longitude != null)
            device.longitude = longitude
        val list = mapOf<Int, Any?>(1 to device.deviceName, 2 to device.latitude, 3 to device.longitude, 4 to uuid)
        SQLHandler.executeStatement(
            "UPDATE Device SET deviceName = ?, latitude = ?, longitude = ? WHERE deviceUUID LIKE ?",
            list
        )
    }

    fun deleteDevice(uuid: String) {
        val list = mapOf<Int, Any>(1 to uuid)
        SQLHandler.executeStatement("DELETE from Device where deviceUUID = ?", list)
        deviceList.remove(getDeviceByUUID(uuid))
    }
}
package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Device
import java.sql.ResultSet

/**
 * Business logic for all devices
 * @author Kevin Stupar
 * @since 07.05.2023
 */
class DeviceHandler private constructor() {
    private var deviceList: ArrayList<Device> = ArrayList()

    companion object {
        private var instance: DeviceHandler? = null

        /**
         * gives the instance object
         * @return instance
         */
        fun getInstance(): DeviceHandler {
            if (instance == null)
                instance = DeviceHandler()
            return instance!!
        }
    }

    /**
     * loads data
     */
    init {
        loadDeviceList()
    }

    /**
     * loads all devices
     */
    fun loadDeviceList() {
        val rs: ResultSet = SQLHandler.getResultSet("SELECT * FROM Device")
        deviceList = SQLHandler.resultSetToArrayList(rs, Device::class.java)
    }

    /**
     * gives all stored devices back
     * @return deviceList
     */
    fun getDeviceList(): ArrayList<Device> {
        return deviceList
    }

    /**
     * gives back a device if it exists
     *
     * @param uuid of the device
     * @return device
     */
    fun getDeviceByUUID(uuid: String?): Device? {
        if (uuid == null)
            return null
        deviceList.forEach {
            if (it.deviceUUID == uuid)
                return it
        }
        return null
    }

    /**
     * adds a device
     *
     * @param device to add
     */
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

    /**
     * updates a device
     *
     * @param uuid of teh device to update
     * @param deviceUUID to be updated
     * @param deviceName to be updated
     * @param latitude to be updated
     * @param longitude to be updated
     */
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

    /**
     * deletes a device
     *
     * @param uuid of hte device that is to be deleted
     */
    fun deleteDevice(uuid: String): Boolean {
        val list = mapOf<Int, Any>(1 to uuid)
        SQLHandler.executeStatement("DELETE from Device where deviceUUID = ?", list)
        return deviceList.remove(getDeviceByUUID(uuid))
    }
}
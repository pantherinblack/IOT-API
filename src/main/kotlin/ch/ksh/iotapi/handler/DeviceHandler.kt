package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Device
import java.sql.ResultSet

class DeviceHandler private constructor() {
    private var deviceList : ArrayList<Device> = ArrayList()

    companion object {
        private var instance : DeviceHandler? = null

        fun getInstance() : DeviceHandler {
            if (instance == null)
                instance = DeviceHandler()
            return instance!!
        }
    }
    init {
        loadDeviceList()
    }

    fun loadDeviceList() {
        val rs : ResultSet? = SQLHandler.getResultSet("SELECT * FROM Device")
        while (rs!!.next()) {
            deviceList = SQLHandler.resultSetToArrayList(rs,Device::class.java)
        }
    }

    fun getDeviceList() : ArrayList<Device> {
        return deviceList
    }

    fun getDeviceByUUID(uuid:String) : Device? {
        deviceList.forEach {
            if (it.deviceUUID == uuid)
                return it
        }
        return null
    }

    fun insertDevice(device: Device) {
        //TODO Insert into DB
        deviceList.add(device)
    }

    fun updateDevice(device: Device) {
        //TODO Alter DB
        val oldDevice = getDeviceByUUID(device.deviceUUID)
        oldDevice?.deviceName = device.deviceName!!
        oldDevice?.latitude = device.latitude!!
        oldDevice?.longitude = device.longitude!!
    }
    fun updateDevice(uuid: String, device: Device) {
        device.deviceUUID = uuid
        updateDevice(device)
    }

    fun deleteDevice(uuid:String) {
        //TODO Delete in DB
        deviceList.remove(getDeviceByUUID(uuid))
    }
}
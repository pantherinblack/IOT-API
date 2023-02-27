package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.model.Device
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/device")
class DeviceService {
    @ResponseBody
    @GetMapping("/list")
    fun listDevices(): ResponseEntity<ArrayList<Device>> {
        return ResponseEntity.status(200).body(DeviceHandler.getInstance().getDeviceList())
    }

    @ResponseBody
    @GetMapping("/get")
    fun getDeviceByUUID(
        @RequestParam("deviceUUID") uuid : String
    ): ResponseEntity<Device?> {
        return ResponseEntity.status(200).body(DeviceHandler.getInstance().getDeviceByUUID(uuid))
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestParam device: Device
    ): ResponseEntity<String?> {
        DeviceHandler.getInstance().insertDevice(device)
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @PutMapping("/update")
    fun updateDevice(
        @RequestParam("deviceUUID") deviceUUID: String,
        @RequestParam("deviceName") deviceName: String? = null,
        @RequestParam("latitude") latitude: Float? = null,
        @RequestParam("longitude") longitude: Float? = null
    ): ResponseEntity<String?> {
        DeviceHandler.getInstance().updateDevice(uuid = deviceUUID, deviceName = deviceName, latitude = latitude, longitude = longitude)
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @DeleteMapping("/delete")
    fun deleteDevice(
        @RequestParam("deviceUUID") deviceUUID: String
    ): ResponseEntity<String?> {
        DeviceHandler.getInstance().deleteDevice(uuid = deviceUUID)
        return ResponseEntity.status(200).body(null)
    }
}
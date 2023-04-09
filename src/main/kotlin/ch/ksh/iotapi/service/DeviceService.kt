package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.model.Device
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        @RequestParam("deviceUUID") uuid: String
    ): ResponseEntity<Device?> {
        return ResponseEntity.status(200).body(DeviceHandler.getInstance().getDeviceByUUID(uuid))
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestBody device: Device
    ): ResponseEntity<String?> {
        DeviceHandler.getInstance().insertDevice(device)
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @PutMapping("/update")
    fun updateDevice(
        @RequestBody device: Device
    ): ResponseEntity<String?> {
        DeviceHandler.getInstance()
            .updateDevice(
                uuid = device.deviceUUID,
                deviceName = device.deviceName,
                latitude = device.latitude,
                longitude = device.longitude
            )
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @DeleteMapping("/delete/{uuid}")
    fun deleteDevice(
        @PathVariable uuid: String
    ): ResponseEntity<String?> {
        DeviceHandler.getInstance().deleteDevice(uuid = uuid)
        return ResponseEntity.status(200).body(null)
    }
}
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
    @GetMapping("/get/{uuid}")
    fun getDeviceByUUID(
        @PathVariable uuid: String
    ): ResponseEntity<Device?> {
        return ResponseEntity.status(200).body(DeviceHandler.getInstance().getDeviceByUUID(uuid))
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestBody device: Device
    ): ResponseEntity<String?> {
        if (DeviceHandler.getInstance().getDeviceByUUID(device.deviceUUID) == null) {
            DeviceHandler.getInstance().insertDevice(device)
            return ResponseEntity.status(200).body(null)
        } else {
            return ResponseEntity.status(400).body("UUID already exists.")
        }
    }

    @ResponseBody
    @PutMapping("/update")
    fun updateDevice(
        @RequestBody device: Device
    ): ResponseEntity<String?> {
        try {
            DeviceHandler.getInstance()
                .updateDevice(
                    uuid = device.deviceUUID,
                    deviceName = device.deviceName,
                    latitude = device.latitude,
                    longitude = device.longitude
                )
        } catch (n: NullPointerException) {
            return ResponseEntity.status(400).body("Missing or wrong input.")
        }

        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @DeleteMapping("/delete/{uuid}")
    fun deleteDevice(
        @PathVariable uuid: String
    ): ResponseEntity<String?> {
        try {
            DeviceHandler.getInstance().deleteDevice(uuid = uuid)
        } catch (n: NullPointerException) {
            return ResponseEntity.status(400).body("Missing or wrong input.")
        }
        return ResponseEntity.status(200).body(null)
    }
}
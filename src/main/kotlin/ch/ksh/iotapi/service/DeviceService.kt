package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.AuthenticationHandler
import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.model.Device
import jakarta.validation.constraints.Size
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
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
        @Size(min = 5, max = 36, message = "UUID must be >= 5 >= 36 characters in length.")
        @PathVariable uuid: String,
        @CookieValue("userName") userName: String,
        @CookieValue("password") password: String
    ): ResponseEntity<Device?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            return ResponseEntity.status(200).body(DeviceHandler.getInstance().getDeviceByUUID(uuid))
        }
        return ResponseEntity.status(401).body(null)
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestBody device: Device,
        @CookieValue("userName") userName: String,
        @CookieValue("password") password: String
    ): ResponseEntity<String?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            if (DeviceHandler.getInstance().getDeviceByUUID(device.deviceUUID) == null) {
                if (device.valid() == null) {
                    DeviceHandler.getInstance().insertDevice(device)
                    return ResponseEntity.status(200).body(null)
                } else {
                    return ResponseEntity.status(400).body(device.valid())
                }
            } else {
                return ResponseEntity.status(400).body("UUID already exists.")
            }
        }
        return ResponseEntity.status(401).body(null)
    }

    @ResponseBody
    @PutMapping("/update")
    fun updateDevice(
        @RequestBody device: Device,
        @CookieValue("userName") userName: String,
        @CookieValue("password") password: String
    ): ResponseEntity<String?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            if (device.valid() == null) {
                if (DeviceHandler.getInstance().getDeviceByUUID(device.deviceUUID) != null) {
                    DeviceHandler.getInstance()
                        .updateDevice(
                            uuid = device.deviceUUID,
                            deviceName = device.deviceName,
                            latitude = device.latitude,
                            longitude = device.longitude
                        )
                    return ResponseEntity.status(200).body(null)
                } else {
                    return ResponseEntity.status(400).body("deviceUUID does not exist or is empty")
                }
            } else {
                return ResponseEntity.status(400).body(device.valid())
            }
        }
        return ResponseEntity.status(401).body(null)
    }

    @ResponseBody
    @DeleteMapping("/delete/{uuid}")
    fun deleteDevice(
        @Size(min = 5, max = 36, message = "UUID must be >= 5 >= 36 characters in length.")
        @PathVariable uuid: String,
        @CookieValue("userName") userName: String,
        @CookieValue("password") password: String
    ): ResponseEntity<String?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            if (DeviceHandler.getInstance().deleteDevice(uuid = uuid)) {
                return ResponseEntity.status(200).body(null)
            } else {
                return ResponseEntity.status(400).body("This UUID does not exist")
            }
        }
        return ResponseEntity.status(401).body(null)
    }
}
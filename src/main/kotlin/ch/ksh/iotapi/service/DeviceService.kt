package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.model.Device
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/device")
class DeviceService {
    @ResponseBody
    @GetMapping("/list")
    fun listDevices(): ArrayList<Device> {
        return DeviceHandler.getInstance().getDeviceList()
    }

    @ResponseBody
    @GetMapping("/get")
    fun getDeviceByUUID(
        @RequestParam("uuid") uuid : String
    ): Device? {
        return DeviceHandler.getInstance().getDeviceByUUID(uuid)
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestParam device: Device
    ) {
        DeviceHandler.getInstance().insertDevice(device)
        return
    }
}
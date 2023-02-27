package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.handler.RecordHandler
import ch.ksh.iotapi.model.Device
import ch.ksh.iotapi.model.Record
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/record")
class RecordService {
    @ResponseBody
    @GetMapping("/list")
    fun listRecords(
        @RequestParam("time") time: Int?
    ): ArrayList<Record> {
        if (time != null && time>0) {
            RecordHandler.getInstance().loadRecordList(time)
        } else {
            RecordHandler.getInstance().loadRecordList()
        }
        return RecordHandler.getInstance().getRecordList()
    }


    @ResponseBody
    @RequestMapping("/get")
    fun getRecordByUUID(
        @RequestParam("uuid") uuid : String
    ): Record {
        return RecordHandler.getInstance().getRecordByUUID(uuid)!!
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestParam record: Record,
        @RequestParam("latitude") latitude : Float,
        @RequestParam("longitude") longitude : Float
    ) {

        RecordHandler.getInstance().insertRecord(record)
        DeviceHandler.getInstance().updateDevice(uuid = record.deviceUUID, latitude = latitude, longitude = longitude)
        return
    }
}


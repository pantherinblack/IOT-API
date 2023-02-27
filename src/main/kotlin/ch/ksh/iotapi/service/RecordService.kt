package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.handler.RecordHandler
import ch.ksh.iotapi.model.Record
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/record")
class RecordService {
    @ResponseBody
    @GetMapping("/list")
    fun listRecords(
        @RequestParam("time") time: Int?
    ): ResponseEntity<ArrayList<Record>> {
        if (time != null && time>0) {
            RecordHandler.getInstance().loadRecordList(time)
        } else {
            RecordHandler.getInstance().loadRecordList()
        }
        return ResponseEntity.status(200).body(RecordHandler.getInstance().getRecordList())
    }


    @ResponseBody
    @RequestMapping("/get")
    fun getRecordByUUID(
        @RequestParam("uuid") uuid : String
    ): ResponseEntity<Record?>  {
        return ResponseEntity.status(200).body(RecordHandler.getInstance().getRecordByUUID(uuid))
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertDevice(
        @RequestParam record: Record,
        @RequestParam("latitude") latitude : Float? = null,
        @RequestParam("longitude") longitude : Float? = null
    ): ResponseEntity<String?> {

        RecordHandler.getInstance().insertRecord(record)
        DeviceHandler.getInstance().updateDevice(uuid = record.deviceUUID, latitude = latitude, longitude = longitude)
        return ResponseEntity.status(200).body(null)
    }
}


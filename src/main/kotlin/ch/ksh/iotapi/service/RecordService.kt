package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.handler.RecordHandler
import ch.ksh.iotapi.model.Record
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

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
    fun insertRecord(
        @RequestParam record: Record,
        @RequestParam("latitude") latitude : Float? = null,
        @RequestParam("longitude") longitude : Float? = null
    ): ResponseEntity<String?> {

        RecordHandler.getInstance().insertRecord(record)
        DeviceHandler.getInstance().updateDevice(uuid = record.deviceUUID, latitude = latitude, longitude = longitude)
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @PutMapping("/update")
    fun updateRecord(
        @RequestParam("recordUUID") recordUUID : String,
        @RequestParam("deviceUUID") deviceUUID : String? = null,
        @RequestParam("timestamp") timestamp : LocalDateTime? = null,
        @RequestParam("temperature") temperature : Float? = null,
        @RequestParam("humidity") humidity : Float? = null,
        @RequestParam("batteryv") batteryv : Float? = null,
    ): ResponseEntity<String?> {
        RecordHandler.getInstance().updateRecord(recordUUID, deviceUUID, timestamp, temperature, humidity, batteryv)
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @DeleteMapping("/delete")
    fun deleteRecord(
        @RequestParam("recordUUID") recordUUID: String
    ): ResponseEntity<String?> {
        RecordHandler.getInstance().deleteRecord(recordUUID)
        return ResponseEntity.status(200).body(null)
    }
}


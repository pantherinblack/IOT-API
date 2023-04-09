package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.handler.RecordHandler
import ch.ksh.iotapi.model.Record
import ch.ksh.iotapi.model.RecordInsertDTO
import ch.ksh.iotapi.util.ConfigReader
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
        if (time != null && time > 0) {
            RecordHandler.getInstance().loadRecordList(time)
        } else {
            RecordHandler.getInstance().loadRecordList()
        }
        return ResponseEntity.status(200).body(RecordHandler.getInstance().getRecordList())
    }


    @ResponseBody
    @RequestMapping("/get/{uuid}")
    fun getRecordByUUID(
        @PathVariable uuid: String
    ): ResponseEntity<Record?> {
        return ResponseEntity.status(200).body(RecordHandler.getInstance().getRecordByUUID(uuid))
    }

    @ResponseBody
    @PostMapping("/insert")
    fun insertRecord(
        @RequestBody riDTO: RecordInsertDTO

    ): ResponseEntity<String?> {

        if (riDTO.key == ConfigReader.readConfig("key")) {
            val record = Record(
                deviceUUID = riDTO.deviceUUID,
                temperature = riDTO.temperature,
                humidity = riDTO.humidity,
                batteryv = riDTO.batteryv,
                timestamp = riDTO.timestamp
            )
            RecordHandler.getInstance().insertRecord(record)

            DeviceHandler.getInstance().updateDevice(
                uuid = record.deviceUUID!!,
                latitude = riDTO.latitude,
                longitude = riDTO.longitude
            )
            return ResponseEntity.status(200).body(null)
        } else {
            return ResponseEntity.status(403).body(null)
        }
    }

    @ResponseBody
    @PutMapping("/update")
    fun updateRecord(
        @RequestBody record: Record,
    ): ResponseEntity<String?> {
        RecordHandler.getInstance().updateRecord(
            record.recordUUID,
            record.deviceUUID,
            record.timestamp,
            record.temperature,
            record.humidity,
            record.batteryv)
        return ResponseEntity.status(200).body(null)
    }

    @ResponseBody
    @DeleteMapping("/delete/{uuid}")
    fun deleteRecord(
        @PathVariable uuid: String
    ): ResponseEntity<String?> {
        RecordHandler.getInstance().deleteRecord(uuid)
        return ResponseEntity.status(200).body(null)
    }
}


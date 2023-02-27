package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.RecordHandler
import ch.ksh.iotapi.model.Record
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/record")
class RecordService {
    @ResponseBody
    @GetMapping("/list")
    fun listRecords(): ArrayList<Record> {
        return RecordHandler.getInstance().getRecordList();
    }


    @ResponseBody
    @RequestMapping("/get")
    fun getRecordByUUID(
        @RequestParam("uuid") uuid : String
    ): Record {
        return RecordHandler.getInstance().getRecordByUUID(uuid)!!
    }
}


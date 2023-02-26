package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Device
import ch.ksh.iotapi.model.Record
import java.sql.ResultSet

class RecordHandler {
    private var recordList : ArrayList<Record> = ArrayList()

    companion object {
        private var instance : RecordHandler? = null

        fun getInstance() : RecordHandler {
            DeviceHandler.getInstance()
            if (instance == null)
                instance = RecordHandler()
            return instance!!
        }
    }
    init {
        loadRecordList()
    }

    private fun loadRecordList() {
        val rs : ResultSet? = SQLHandler.getResultSet("SELECT * FROM Record")
        while (rs!!.next()) {
            recordList = SQLHandler.resultSetToArrayList(rs, Record::class.java)
        }
    }

    fun getRecordByUUID(uuid:String) : Record? {
        recordList.forEach {
            if (it.recordUUID == uuid)
                return it
        }
        return null
    }

    fun insertRecord(record: Record) {
        //TODO Insert into DB
        recordList.add(record)
    }

    fun updateRecord(record: Record) {
        //TODO Alter DB
        val oldRecord = getRecordByUUID(record.recordUUID)
        oldRecord?.deviceUUID = record.deviceUUID!!
        oldRecord?.timestamp = record.timestamp!!
        oldRecord?.temperature = record.temperature!!
        oldRecord?.humidity = record.humidity!!
        oldRecord?.batteryv = record.batteryv!!
    }

    fun updateRecord(uuid: String, record: Record) {
        record.recordUUID = uuid
        updateRecord(record)
    }

    fun deleteRecord(uuid: String) {
        //TODO Delete in DB
        recordList.remove(getRecordByUUID(uuid))
    }
}
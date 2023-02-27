package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Record
import java.sql.ResultSet
import java.time.LocalDateTime

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

    fun loadRecordList() {
        val rs : ResultSet? = SQLHandler.getResultSet("SELECT * FROM Record WHERE date(timestamp) >= date_add(current_timestamp,INTERVAL 1 DAY) ORDER BY timestamp DESC")
        rs!!.next();
        if (rs.getInt(1) != recordList.size) {
            loadRecordList(1)
        }

    }

    fun loadRecordList(hours: Int) {
        val rs : ResultSet? = SQLHandler.getResultSet("SELECT * FROM Record WHERE date(timestamp) >= date_add(current_timestamp,INTERVAL "+hours+" DAY) ORDER BY timestamp DESC")
        while (rs!!.next()) {
            recordList = SQLHandler.resultSetToArrayList(rs, Record::class.java)
        }
        SQLHandler.sqlClose()
    }

    fun getRecordList(): ArrayList<Record> {
        return recordList
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
        recordList.forEach {
                record -> if (record.timestamp >= LocalDateTime.now().minusDays(1)) {
                    softDeleteRecord(record)
                }
        }
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

    fun softDeleteRecord(record: Record) {
        recordList.remove(record)
    }

    fun deleteRecord(uuid: String) {
        //TODO Delete in DB
        recordList.remove(getRecordByUUID(uuid))
    }
}
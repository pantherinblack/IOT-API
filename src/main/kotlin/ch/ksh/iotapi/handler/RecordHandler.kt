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
        rs!!.next()
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
        val list = mapOf<Int, Any?>(1 to record.recordUUID, 2 to record.deviceUUID, 3 to record.timestamp, 4 to record.temperature, 5 to record.humidity, 6 to record.batteryv)
        SQLHandler.getResultSet("INSERT INTO Device (recordUUID, deviceUUID, timestamp, temperature, humidity, batteryv) VALUES (?, ?, ?, ?, ?, ?)", list)
        recordList.add(record)
        recordList.forEach {
                record -> if (record.timestamp!! >= LocalDateTime.now().minusDays(1)) {
            softDeleteRecord(record)
        }
        }
    }

    fun updateRecord(uuid: String, deviceUUID: String? = null, timestamp: LocalDateTime? = null, temperature: Float? = null, humidity: Float? = null, batteryv: Float? = null) {
        val record = getRecordByUUID(uuid)!!
        if (deviceUUID != null)
            record.deviceUUID = deviceUUID
        if (timestamp != null)
            record.timestamp = timestamp
        if (temperature != null)
            record.temperature = temperature
        if (humidity != null)
            record.humidity = humidity
        if (batteryv != null)
            record.batteryv = batteryv
        val list = mapOf<Int, Any?>(1 to record.deviceUUID, 2 to record.timestamp, 3 to record.temperature, 4 to record.humidity, 5 to record.batteryv, 6 to uuid)
        SQLHandler.getResultSet("UPDATE Record SET deviceUUID = ?, timestamp = ?, temperature = ?, humidity = ?, batteryv = ? WHERE recordUUID LIKE ?", list)


    }

    fun softDeleteRecord(record: Record) {
        recordList.remove(record)
    }

    fun deleteRecord(uuid: String) {
        val list = mapOf<Int, Any>(1 to uuid)
        SQLHandler.getResultSet("DELETE from Record where recordUUID like ?",list)
        recordList.remove(getRecordByUUID(uuid))
    }
}
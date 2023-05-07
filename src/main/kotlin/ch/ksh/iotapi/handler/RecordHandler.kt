package ch.ksh.iotapi.handler

import ch.ksh.iotapi.model.Record
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDateTime
import kotlin.math.abs

/**
 * Business logic for all records
 * @author Kevin Stupar
 * @since 07.05.2023
 */
class RecordHandler {
    private var recordList: ArrayList<Record> = ArrayList()

    companion object {
        private var instance: RecordHandler? = null

        /**
         * gives the instance object
         * @return instance
         */
        fun getInstance(): RecordHandler {
            DeviceHandler.getInstance()
            if (instance == null)
                instance = RecordHandler()
            return instance!!
        }
    }

    /**
     * loads data
     */
    init {
        loadRecordList()
    }

    /**
     * loads all records that are a day (or less) old
     */
    fun loadRecordList() {
        val rs: ResultSet =
            SQLHandler.getResultSet("SELECT COUNT(*) FROM Record WHERE date(timestamp) >= date_add(current_timestamp,INTERVAL -1 DAY) ORDER BY timestamp DESC")
        rs.next()
        if (rs.getInt(1) != recordList.size) {
            loadRecordList(1)
        }

    }

    /**
     * loads all devices that are x days (or less) old
     * @param days how old the records may be
     */
    fun loadRecordList(days: Int) {
        val rs: ResultSet = SQLHandler.getResultSet(
            "SELECT * FROM Record WHERE date(timestamp) >= date_add(current_timestamp,INTERVAL " +
                    (0 - abs(days)) + " DAY) ORDER BY timestamp DESC"
        )
        recordList = SQLHandler.resultSetToArrayList(rs, Record::class.java)
        SQLHandler.sqlClose()
    }

    /**
     * gives all stored records back
     * @return recordList
     */
    fun getRecordList(): ArrayList<Record> {
        return recordList
    }

    /**
     * gives back a record if it exists
     *
     * @param uuid of the record
     * @return record
     */
    fun getRecordByUUID(uuid: String): Record? {
        //In Local storage
        recordList.forEach {
            if (it.recordUUID == uuid)
                return it
        }

        //Not in storage, request to DB
        val list = mapOf<Int, Any?>(
            1 to uuid
        )
        val rs: ResultSet = SQLHandler.getResultSet(
            "SELECT * FROM Record WHERE recordUUID = ? LIMIT 1",
            list
        )
        //Convert to record-list
        val tempList = SQLHandler.resultSetToArrayList(rs, Record::class.java)

        //Return DB output, if not empty
        if (tempList.size == 1)
            return tempList[0]
        else
            return null
    }

    /**
     * adds a record
     *
     * @param record to be added
     */
    fun insertRecord(record: Record) {
        val list = mapOf<Int, Any?>(
            1 to record.recordUUID,
            2 to record.deviceUUID,
            3 to record.timestamp,
            4 to record.temperature,
            5 to record.humidity,
            6 to record.batteryv
        )
        SQLHandler.executeStatement(
            "INSERT INTO Record (recordUUID, deviceUUID, timestamp, temperature, humidity, batteryv) VALUES ( ?, ?, ?, ?, ?, ? )",
            list
        )
        recordList.add(record)
        recordList.removeIf { record1 -> (record1.timestamp!!.toLocalDateTime() >= LocalDateTime.now().minusDays(1)) }
    }

    /**
     * updates a record
     *
     * @param uuid of the record that is to get updated
     * @param deviceUUID to be updated
     * @param timestamp to be updated
     * @param temperature to be updated
     * @param humidity to be updated
     * @param batteryv to be updated
     */
    fun updateRecord(
        uuid: String,
        deviceUUID: String? = null,
        timestamp: Timestamp? = null,
        temperature: Float? = null,
        humidity: Float? = null,
        batteryv: Float? = null
    ) {
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
        val list = mapOf<Int, Any?>(
            1 to record.deviceUUID,
            2 to record.timestamp,
            3 to record.temperature,
            4 to record.humidity,
            5 to record.batteryv,
            6 to uuid
        )
        SQLHandler.executeStatement(
            "UPDATE Record SET deviceUUID = ?, timestamp = ?, temperature = ?, humidity = ?, batteryv = ? WHERE recordUUID LIKE ?",
            list
        )
    }

    /**
     * deletes a device
     *
     * @param uuid of the record that is to be deleted
     */
    fun deleteRecord(uuid: String): Boolean {
        val list = mapOf<Int, Any>(1 to uuid)
        SQLHandler.executeStatement("DELETE from Record where recordUUID = ?", list)
        return recordList.remove(getRecordByUUID(uuid))
    }
}
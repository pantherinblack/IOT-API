package ch.ksh.iotapi.service

import ch.ksh.iotapi.handler.AuthenticationHandler
import ch.ksh.iotapi.handler.DeviceHandler
import ch.ksh.iotapi.handler.RecordHandler
import ch.ksh.iotapi.model.Record
import ch.ksh.iotapi.model.RecordInsertDTO
import ch.ksh.iotapi.util.ConfigReader
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Pattern
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 * Record Service
 * @author Kevin Stupar
 * @since 07.05.2023
 */
@Validated
@RestController
@RequestMapping("/record")
class RecordService {

    /**
     * returns a list of all records with a specific age
     *
     * @param time in days (max)
     * @return DeviceList
     */
    @ResponseBody
    @GetMapping("/list")
    fun listRecords(
        @DecimalMin(value = "0", message = "Value must be >= 1")
        @DecimalMax(value = "10000", message = "Value must be >= 10000")
        @RequestParam("time") time: Int?,
        @CookieValue("userName") userName: String?,
        @CookieValue("password") password: String?
    ): ResponseEntity<ArrayList<Record>> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            if (time != null) {
                RecordHandler.getInstance().loadRecordList(time)
            } else {
                RecordHandler.getInstance().loadRecordList()
            }
            return ResponseEntity.status(200).body(RecordHandler.getInstance().getRecordList())
        }
        return ResponseEntity.status(401).body(null)
    }

    /**
     * gets a specific record
     *
     * @param uuid of the record
     * @param userName as authentication
     * @param password as authentication
     * @return record
     */
    @ResponseBody
    @RequestMapping("/get/{uuid}")
    fun getRecordByUUID(
        @Pattern(
            regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$",
            message = "UUID must follow this pattern: ^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$."
        )
        @PathVariable uuid: String,
        @CookieValue("userName") userName: String?,
        @CookieValue("password") password: String?
    ): ResponseEntity<Record?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            return ResponseEntity.status(200).body(RecordHandler.getInstance().getRecordByUUID(uuid))
        }
        return ResponseEntity.status(401).body(null)
    }

    /**
     * Inserts a new record
     *
     * @param riDTO to be inserted
     */
    @ResponseBody
    @PostMapping("/insert")
    fun insertRecord(
        @RequestBody riDTO: RecordInsertDTO
    ): ResponseEntity<String?> {
        if (riDTO.valid() == null) {

            if (riDTO.key == ConfigReader.readConfig("key")) {
                if (DeviceHandler.getInstance().getDeviceByUUID(riDTO.deviceUUID) != null) {
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
                    return ResponseEntity.status(400).body("deviceUUID does not exist")
                }
            } else {
                return ResponseEntity.status(403).body(null)
            }
        } else {
            return ResponseEntity.status(400).body(riDTO.valid())
        }
    }

    /**
     * updates a record with new data
     *
     * @param record with updated data
     * @param userName as authentication
     * @param password as authentication
     */
    @ResponseBody
    @PutMapping("/update")
    fun updateRecord(
        @RequestBody record: Record,
        @CookieValue("userName") userName: String?,
        @CookieValue("password") password: String?
    ): ResponseEntity<String?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            if (record.valid() == null) {
                if (RecordHandler.getInstance().getRecordByUUID(record.recordUUID) != null) {
                    if (DeviceHandler.getInstance().getDeviceByUUID(record.deviceUUID) != null) {
                        RecordHandler.getInstance().updateRecord(
                            record.recordUUID,
                            record.deviceUUID,
                            record.timestamp,
                            record.temperature,
                            record.humidity,
                            record.batteryv
                        )
                        return ResponseEntity.status(200).body(null)
                    } else {
                        return ResponseEntity.status(400).body("deviceUUID does not exist")
                    }
                } else {
                    return ResponseEntity.status(400).body("recordUUID does not exist or is empty")
                }
            } else {

                return ResponseEntity.status(400).body(record.valid())
            }
        }
        return ResponseEntity.status(401).body(null)
    }

    /**
     * deletes a record
     *
     * @param uuid of the record to delete
     * @param userName as authentication
     * @param password as authentication
     */
    @ResponseBody
    @DeleteMapping("/delete/{uuid}")
    fun deleteRecord(
        @Pattern(
            regexp = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$",
            message = "UUID must follow this pattern: ^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$."
        )
        @PathVariable uuid: String,
        @CookieValue("userName") userName: String?,
        @CookieValue("password") password: String?
    ): ResponseEntity<String?> {
        if (AuthenticationHandler.getInstance().isValidUser(userName, password)) {
            if (RecordHandler.getInstance().deleteRecord(uuid)) {
                return ResponseEntity.status(200).body(null)
            } else {
                return ResponseEntity.status(400).body("This UUID does not exist")
            }
        }
        return ResponseEntity.status(401).body(null)
    }
}


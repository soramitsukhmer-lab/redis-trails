package com.soramitsukhmer.redistime.portal

import com.soramitsukhmer.redistime.models.RecordEvent
import com.soramitsukhmer.redistime.repository.RecordRepository
import org.springframework.data.domain.Range
import org.springframework.data.redis.connection.stream.ObjectRecord
import org.springframework.web.bind.annotation.*

/** NOTE:
 *  This portal is only for testing purpose.
 *  We will remove it after testing pub/sub is done.
 */

@RestController
@RequestMapping("/api/v1/records")
class RecordPortal(
    private val recordRepo: RecordRepository
){

    @GetMapping("/{subject}")
    fun findAll(@PathVariable subject: String) : List<ObjectRecord<String, RecordEvent>>? {
        return recordRepo.findAll(subject)
    }

    @GetMapping("/{subject}/{id}")
    fun findById(@PathVariable subject: String , @PathVariable id: Int) : List<ObjectRecord<String, RecordEvent>>? {
        return recordRepo.getAllBySubjectAndId(subject, id)
    }

    @GetMapping("/range/{subject}/{id}")
    fun findRangeRecord(
        @PathVariable subject: String,
        @PathVariable id: Int,
        @RequestParam("from") from: String?,
        @RequestParam("to") to: String?,
    ) : List<ObjectRecord<String, RecordEvent>>? {
        return recordRepo.getRangeRecords(
            subject + "_" + id.toString(),
            Range.closed(from ?: "-", to ?: "+")
        )
    }
}
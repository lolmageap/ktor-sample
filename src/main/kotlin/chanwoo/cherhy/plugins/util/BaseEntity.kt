package chanwoo.cherhy.plugins.util

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

//@MappedSuperclass
//@EntityListeners(AuditingEntityListener)
//abstract class BaseEntity {
//
//    @CreatedDate
//    @Column(nullable = false, updatable = false)
//    protected var createdAt: LocalDateTime = LocalDateTime.MIN
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    protected var modifiedAt: LocalDateTime = LocalDateTime.MIN
//
//}
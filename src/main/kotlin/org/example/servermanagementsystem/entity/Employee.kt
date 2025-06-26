package org.example.servermanagementsystem.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "employees")
class Employee(

    @Id
    val id: UUID = UUID.randomUUID(),

    var name: String = "",

    var email: String = "",

    var position: String = "",

    val createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    var department: Department? = null
) {
    constructor() : this(UUID.randomUUID(), "", "", "", Instant.now(), null)
}
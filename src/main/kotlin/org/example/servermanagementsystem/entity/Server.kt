package org.example.servermanagementsystem.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "servers")
class Server(

    @Id
    val id: UUID = UUID.randomUUID(),

    var name: String = "",

    var manufacturer: String = "",

    var ip: String = "",

    var ramGb: Int = 0,

    var diskGb: Int = 0,

    val createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id", nullable = false)
    var responsible: Employee? = null
) {
    constructor() : this(UUID.randomUUID(), "", "", "", 0, 0, Instant.now(), null)
}
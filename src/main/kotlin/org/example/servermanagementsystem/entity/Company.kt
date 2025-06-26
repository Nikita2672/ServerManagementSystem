package org.example.servermanagementsystem.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "companies")
class Company(

    @Id
    val id: UUID = UUID.randomUUID(),

    var name: String = "",

    val createdAt: Instant = Instant.now(),

    @OneToMany(mappedBy = "company", cascade = [CascadeType.ALL], orphanRemoval = true)
    val departments: MutableList<Department> = mutableListOf()
) {
    constructor() : this(UUID.randomUUID(), "", Instant.now(), mutableListOf())
}
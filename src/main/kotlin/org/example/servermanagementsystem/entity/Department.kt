package org.example.servermanagementsystem.entity

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "departments")
class Department(

    @Id
    val id: UUID = UUID.randomUUID(),

    var name: String = "",

    val createdAt: Instant = Instant.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    var company: Company? = null,

    @OneToMany(mappedBy = "department", cascade = [CascadeType.ALL], orphanRemoval = true)
    val employees: MutableList<Employee> = mutableListOf()
) {
    constructor() : this(UUID.randomUUID(), "", Instant.now(), null, mutableListOf())
}
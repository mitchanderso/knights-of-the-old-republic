package com.ld.learning.domain

import com.ld.learning.domain.enum.JediRank

data class Jedi (
    val name: String,
    val master: String,
    val padawan: String,
    val rank: JediRank
)
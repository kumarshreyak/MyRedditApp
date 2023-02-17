package com.example.myredditapp.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(@PrimaryKey val name: String)
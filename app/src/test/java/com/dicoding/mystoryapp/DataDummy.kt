package com.dicoding.mystoryapp

import com.dicoding.mystoryapp.api.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/new-ui-logo.png",
                "2023-11-04T18:55:07.275Z",
                "name $i",
                "lorem ipsum",
                0.0,
                "$i",
                0.0
            )
            items.add(story)
        }
        return items
    }
}
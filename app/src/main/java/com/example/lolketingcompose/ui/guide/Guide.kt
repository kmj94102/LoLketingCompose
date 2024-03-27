package com.example.lolketingcompose.ui.guide

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.lolketingcompose.R

enum class Guide(
    @StringRes
    val titleRes: Int,
    @DrawableRes
    val imageRes: Int,
    @StringRes
    val content: Int
) {
    AOS(R.string.aos, R.drawable.img_lol_guide_aos, R.string.guide_aos),
    Rule(R.string.rule, R.drawable.img_lol_guide_rule, R.string.guide_rule),
    Position(R.string.position, R.drawable.img_lol_guide_position, R.string.guide_position),
    Nature(R.string.nature, R.drawable.img_lol_guide_nature, R.string.guide_nature),
    Score(R.string.score, R.drawable.img_lol_guide_score, R.string.guide_score),
    Terms(R.string.game_terms, R.drawable.img_lol_guide_term, R.string.guide_score);

    fun getTitle(context: Context) = context.getString(titleRes)

    companion object {
        fun getGuide(@StringRes titleRes: Int) =
            Guide.values().find { it.titleRes == titleRes } ?: AOS
    }
}
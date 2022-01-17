package br.android.cericatto.infrontxtask.adapter

import br.android.cericatto.infrontxtask.data.common.AwayTeam
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue
import br.android.cericatto.infrontxtask.data.result.Score

sealed class ResultsRecyclerViewItem {

    class Title(
        val title: String
    ) : ResultsRecyclerViewItem()

    class Result(
        val awayTeam: AwayTeam,
        val competitionStage: CompetitionStage,
        val date: String,
        val homeTeam: HomeTeam,
        val id: Int,
        val score: Score,
        val venue: Venue
    ) : ResultsRecyclerViewItem()
}
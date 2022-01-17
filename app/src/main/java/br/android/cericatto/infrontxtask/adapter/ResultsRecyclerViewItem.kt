package br.android.cericatto.infrontxtask.adapter

import br.android.cericatto.infrontxtask.data.common.AwayTeam
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue
import br.android.cericatto.infrontxtask.data.result.AggregateScore
import br.android.cericatto.infrontxtask.data.result.PenaltyScore
import br.android.cericatto.infrontxtask.data.result.Score

sealed class ResultsRecyclerViewItem {

    class Title(
        val title: String
    ) : ResultsRecyclerViewItem()

    class Result(
        val aggregateScore: AggregateScore,
        val awayTeam: AwayTeam,
        val competitionStage: CompetitionStage,
        val date: String,
        val homeTeam: HomeTeam,
        val id: Int,
        val penaltyScore: PenaltyScore,
        val score: Score,
        val state: String,
        val type: String,
        val venue: Venue
    ) : ResultsRecyclerViewItem()
}
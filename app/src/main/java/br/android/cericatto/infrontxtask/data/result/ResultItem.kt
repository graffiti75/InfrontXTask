package br.android.cericatto.infrontxtask.data.result

import br.android.cericatto.infrontxtask.data.common.AwayTeam
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue

data class ResultItem(
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
)